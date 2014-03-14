package be.fastrada.tryouts;

import android.util.Log;
import be.fastrada.networking.Packet;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RestSender implements Sender {
    public static final int TIMEOUT = 100;
    private ConcurrentLinkedQueue<Packet> packetBuffer;
    private ObjectMapper objectMapper;
    private String raceName;
    private int packetsSent;

    public RestSender(String raceName) {
        this.raceName = raceName;
        this.packetBuffer = new ConcurrentLinkedQueue<Packet>();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void send(List<Packet> packets) {
        packetBuffer.addAll(packets);
        Log.d("RestSender", "send methode");
    }

    @Override
    public int getAmountSent() {
        return packetsSent;
    }

    @Override
    public void run() {
        Log.d("RestSender", "run methode");
        try {
            while (true) {
                Log.d("RestSender", "eerste while loop");
                final List<Packet> sendBuffer = new ArrayList<Packet>();
                long startTime = System.currentTimeMillis();

                while (packetBuffer.size() > 0 && System.currentTimeMillis() - startTime < TIMEOUT) {
                    final Iterator<Packet> it = packetBuffer.iterator();
                    Log.d("RestSender", "tweede while loop");
                    while (it.hasNext()) {
                        sendBuffer.add(it.next());
                        it.remove();
                    }
                    startTime = System.currentTimeMillis();
                }

                /*final List<Packet> packetCopy = new ArrayList<Packet>();
                final Iterator it = packetBuffer.iterator();
                if(packetBuffer.size() >= 50) {
                   while(true && )
                } else if(packetBuffer.size() > 0 && packetBuffer.size() < 50) {


                }
                packetBuffer.clear();
                packetsSent += packetCopy.size();*/


                Log.d("JSONSTRING", packetsToJson(sendBuffer));
            }
        } catch (ParseException e) {
            Log.e("Fastrada", "Error met parsen van packet naar json");
        } catch (IOException e) {
            Log.e("Fastrada", "Error met parsen van packet naar json");
        }
    }

    private String packetsToJson(List<Packet> packets) throws IOException, ParseException {
        final String jsonString = objectMapper.writeValueAsString(packets);
        final JSONObject jsonObject = new JSONObject();
        final JSONArray jsonPacketArray = (JSONArray) new JSONParser().parse(jsonString);

        jsonObject.put("packets", jsonPacketArray);
        jsonObject.put("raceName", raceName);

        return jsonObject.toJSONString();
    }
}
