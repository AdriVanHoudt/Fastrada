package be.fastrada;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Packet {
    private String content;
    private PacketReader reader;
    private JSONObject configFile;
    private int structureMethods;

    public Packet(String content) {
        this.content = content.replace(" ", "");
        this.reader = new PacketReader(this.content);

        try {
            this.configFile = (JSONObject) new JSONParser().parse(new FileReader("res/packetStructure.json"));
        } catch (IOException e) {
        } catch (ParseException e) {
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
}
