package be.fastrada.packetmapper;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
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

    public boolean invokeMethod(String methodToInvoke, long value) {
        if (methodToInvoke == null || methodToInvoke.isEmpty()) {
            return false;
        }

        try {
            Class<?> c = Class.forName("be.fastrada.Dashboard");
            Class[] argTypes = new Class[] { Long.class };
            Method m = c.getDeclaredMethod(methodToInvoke, argTypes);
            m.invoke(null, (Object)value);

            return true;
        } catch (ClassNotFoundException e) {
            return false;
        } catch (InvocationTargetException e) {
            return false;
        } catch (NoSuchMethodException e) {
            return false;
        } catch (IllegalAccessException e) {
            return false;
        }
    }
}