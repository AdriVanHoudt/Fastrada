package be.fastrada.packetmapper;


import be.fastrada.Dashboard;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Class that represents a packet.
 */
public class Packet {
    private byte[] content;
    private PacketReader reader;
    private JSONObject configFile;
    private JSONArray methods;
    private Dashboard dashboard;

    public Packet(byte[] bytes, InputStream packetMappingPath, Dashboard dashboard) {
        this.reader = new PacketReader(this.content);
        this.dashboard = dashboard;

        try {
            this.configFile = (JSONObject) new JSONParser().parse(new InputStreamReader(packetMappingPath));
        } catch (ParseException e) {
            throw new Error("parse error");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] getContent() {
        return this.content;
    }

    public PacketReader getReader() {
        return reader;
    }

    public JSONObject getConfigFile() {
        return configFile;
    }

    public JSONArray getStructure() {
        JSONObject packets = (JSONObject) configFile.get("packets");
        JSONObject packet = (JSONObject) packets.get("" + this.getId());
        return (JSONArray) packet.get("struct");
    }

    public int getId() {
        return reader.getId();
    }

    public int getSize(String name) {
        for (Object o : this.getStructure()) {
            JSONObject obj = (JSONObject) o;
            if (obj.get("name").equals(name)) {
                return ((Long) obj.get("size")).intValue();
            }
        }
        return -1;
    }

    public boolean invokeMethod(JSONObject jo) {
        if (jo == null) {
            return true;
        }

        String methodToInvoke = (String) jo.get("name");
        int byteSize = Integer.parseInt(jo.get("size").toString());

        try {
            Class cls = Class.forName("be.fastrada.Dashboard");
            Object obj = dashboard;

            for (Method m : cls.getMethods()) {
                if (m.getName().equals(methodToInvoke)) {
                    switch (byteSize) {
                        case 8:
                            m.invoke(obj, (short) reader.readUint8());
                            break;
                        case 16:
                            m.invoke(obj, (int) reader.readUint16());
                            break;
                        case 32:
                            m.invoke(obj, (long) reader.readUint32()); //kan dit niet coveren omdat dashboard geen parameter voor double heeft
                            break;
                    }
                    return true;
                }
            }

            return true;
        } catch (InvocationTargetException e) {
            //kan niet gecoverd word
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            //does not happen
            return false;
        } catch (ClassNotFoundException e) {
            //does not happen
            e.printStackTrace();
            return false;
        } /*catch (InstantiationException e) {
            e.printStackTrace();
            return false;
        }*/ catch (EOFException e) {
            throw new Error();
        }
    }

    public boolean process() {
        JSONArray structure = getStructure();

        for (int i = 0; i < structure.size(); i++) {
            JSONObject jo = (JSONObject) structure.get(i);

            if (!invokeMethod(jo)) {
                return false;
            }
        }

        return true;
    }

    public void setContent(String content) {
        this.reader.resetPosition();
        this.reader.setContent(content);
    }
}