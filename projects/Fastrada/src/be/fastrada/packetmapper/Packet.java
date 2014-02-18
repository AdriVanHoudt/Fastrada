package be.fastrada.packetmapper;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Packet {
    private String content;
    private PacketReader reader;
    private JSONObject configFile;
    private JSONArray methods;

    public Packet(String content, String packetMappingPath) {
        this.content = content.replace(" ", "");
        this.reader = new PacketReader(this.content);

        try {
            FileReader fr = new FileReader(packetMappingPath);
            this.configFile = (JSONObject) new JSONParser().parse(fr);
        } catch (ParseException e) {
            throw new Error("parse error");
        } catch (FileNotFoundException e) {
            throw new Error("file not found");
        } catch (IOException e) {
            throw new Error("IO exception");
        }
    }

    public String getContent() {
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


            Object obj = cls.newInstance();

            //error met types van parameters van functies van dashboard

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
                            m.invoke(obj, (long) reader.readUint32());
                            break;
                    }

                    return true;
                }
            }

            return true;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return false;
        } catch (EOFException e) {
            e.printStackTrace();
            return false;
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
}