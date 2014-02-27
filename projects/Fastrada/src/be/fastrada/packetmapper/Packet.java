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
import java.nio.ByteBuffer;

/**
 * Class that represents a packet.
 */
public class Packet {
    private ByteBuffer byteBuffer;
    private PacketConfiguration packetConfiguration;

    public Packet(byte[] content, PacketConfiguration packetConfiguration) {
        this.packetConfiguration = packetConfiguration;
        this.byteBuffer = ByteBuffer.wrap(content); // Wrap the byte array in the buffer, BIG ENDIAN!
    }

    public JSONArray getStructure() {
        int id = this.getId();
        JSONObject packets = (JSONObject) packetConfiguration.getConfigFile().get("packets");
        JSONObject packet = (JSONObject) packets.get("" + id);
        return (JSONArray) packet.get("struct");
    }

    public int getId() {
        return byteBuffer.getShort();
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
            Class cls = Class.forName(packetConfiguration.getClassPath());
            Object obj = packetConfiguration.getClassObject();

            for (Method m : cls.getMethods()) {
                if (m.getName().equals(methodToInvoke)) {
                    switch (byteSize) {
                        case 8:
                            m.invoke(obj, (short) byteBuffer.get());
                            break;
                        case 16:
                            m.invoke(obj, (int) byteBuffer.getShort());
                            break;
                        case 32:
                            m.invoke(obj, (long) byteBuffer.getInt()); //kan dit niet coveren omdat dashboard geen parameter voor double heeft
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

    public ByteBuffer getBuffer() {
        return byteBuffer;
    }
}