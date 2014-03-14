package be.fastrada.networking.sending;

import android.util.Log;
import be.fastrada.networking.Packet;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;

public class PacketSender implements Runnable {
    private String raceName;
    private long startTime;
    private List<Packet> packets;
    private ObjectMapper objectMapper;

    public PacketSender(String raceName, long startTime, List<Packet> packets) {
        this.raceName = raceName;
        this.startTime = startTime;
        this.packets = packets;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void run() {
        try {
            final String jsonString = packetsToJson(packets);
            Log.d("myPacketSender", jsonString);
            //versturen
            /*final HttpClient client = new DefaultHttpClient();
            final HttpPost request = new HttpPost("192.168.0.6:8080/fastrada/api/packet");
            final StringEntity params = new StringEntity(jsonString);
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            final HttpResponse response = client.execute(request);*/
        } catch (IOException e) {
            Log.e("Fastrada", "Error met parsen van packet naar json");
        } catch (ParseException e) {
            Log.e("Fastrada", "Error met parsen van packet naar json");
        }
    }

    private String packetsToJson(List<Packet> packets) throws IOException, ParseException {
        final String jsonString = objectMapper.writeValueAsString(packets);
        final JSONObject jsonObject = new JSONObject();
        final JSONArray jsonPacketArray = (JSONArray) new JSONParser().parse(jsonString);

        jsonObject.put("packets", jsonPacketArray);
        jsonObject.put("raceName", raceName);
        jsonObject.put("startTime", startTime);

        return jsonObject.toJSONString();
    }
}
