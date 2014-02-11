package be.fastrada;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class Packet {
    private String content;
    private PacketReader reader;
    private JSONObject configFile;

    public Packet(String content) {
        this.content = content.replace(" ", "");
        this.reader = new PacketReader(this.content);

        try {
            this.configFile = (JSONObject) new JSONParser().parse(new FileReader("res/packetStructure.json"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
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
}