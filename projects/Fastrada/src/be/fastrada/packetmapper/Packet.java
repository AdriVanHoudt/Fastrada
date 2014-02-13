package be.fastrada.packetmapper;

import be.fastrada.Dashboard;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.BitSet;
import java.util.Iterator;

public class Packet {
    private String content;
    private PacketReader reader;
    private JSONObject configFile;
    private JSONArray methods;

    public Packet(String content) {
        this.content = content.replace(" ", "");
        this.reader = new PacketReader(this.content);

        try {
            FileReader fr = new FileReader("res/raw/structure.json");
            this.configFile = (JSONObject) new JSONParser().parse(fr);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
            if(obj.get("name").equals(name)) {
                return ((Long) obj.get("size")).intValue();
            }
        }
        return -1;
    }

    public JSONArray getMethods() {
        JSONArray structure = getStructure();
        return methods;
    }

    public boolean invokeMethod(String methodToInvoke, Object value) {
        if (methodToInvoke == null || methodToInvoke.isEmpty()) {
            return true;
        }

        try {
            Class cls = Class.forName("be.fastrada.Dashboard");
            Class[] argTypes = new Class[] { Object.class };

            Object obj = cls.newInstance();

            for (Method m : cls.getMethods()) {
                if (m.getName().equals(methodToInvoke)) {
                    m.invoke(obj, value);
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
        }
    }

    public boolean process() {
        JSONArray structure = getStructure();

        for (int i = 0; i < structure.size(); i++) {
            JSONObject jo = (JSONObject) structure.get(i);

            String methodName = (String) jo.get("name");
            int byteSize = Integer.parseInt((String) jo.get("size"));

            Object valueFound;

            try {
                switch (byteSize) {
                    case 8:
                        valueFound = reader.readUint8();
                        break;
                    case 16:
                        valueFound = reader.readUint16();
                        break;
                    case 32:
                        valueFound = reader.readUint32();
                        break;
                    default:
                        return false;
                }
            }
            catch (EOFException e) {
                return false;
            }

            if (!invokeMethod(methodName, valueFound)) {
                return false;
            }
        }

        return true;
    }
}