package be.fastrada.packetmapper;


import android.util.Log;
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
    private String content;
    private PacketReader reader;
    private JSONArray methods;
    private PacketConfiguration packetConfiguration;

    public Packet(String content, PacketConfiguration packetConfiguration) {
        this.packetConfiguration = packetConfiguration;
        this.content = content.replace(" ", "");
        this.reader = new PacketReader(this.content);
    }

    public String getContent() {
        return this.content;
    }

    public PacketReader getReader() {
        return reader;
    }


    public JSONArray getStructure() {
        JSONObject packets = (JSONObject) packetConfiguration.getConfigFile().get("packets");
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
            Class cls = Class.forName(packetConfiguration.getClassObject().getClass().toString()); // Probeer da eens :D
            Object obj = packetConfiguration.getClassObject(); // Run eens in debug

            for (Method m : cls.getMethods()) {
                if (m.getName().equals(methodToInvoke)) {
                    switch (byteSize) {
                        case 8:
                            m.invoke(obj, (short) reader.readUint8());
                            break;
                        case 16:
                            m.invoke(obj, (int) reader.readUint16());    // hier crasht em
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
        this.content = content.replaceAll(" ", "");
        this.content = content.replaceAll("\t", "");
        this.reader.resetPosition();
        this.reader.setContent(content);
    }
}