package be.fastrada.networking;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.List;

public class RestSender implements Sender {
    private List<Packet> packets;
    JSONObject parent;
    private int amountSent = 0;

    @Override
    public void send(List<Packet> packets) {
        this.packets = packets;
    }

    @Override
    public int getAmountSent() {
        return amountSent;
    }

    @Override
    public void run() {
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        while (true)
        {
            if (packets.size() >= 0)
            {
                List<Packet> local = packets;
                packets = null;

                try {
                    json = mapper.writeValueAsString(local);

                    System.out.println(json);

                    //versturen
                    HttpClient client = new DefaultHttpClient();
                    HttpPost request = new HttpPost("192.168.0.6:8080/fastrada/api/packet");
                    StringEntity params = new StringEntity(json);
                    request.addHeader("content-type", "application/json");
                    request.setEntity(params);
                    HttpResponse response = client.execute(request);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
