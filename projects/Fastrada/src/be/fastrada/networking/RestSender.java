package be.fastrada.networking;

import be.fastrada.tryouts.Sender;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RestSender implements Sender {
    private List<Packet> packets;
    JSONObject parent;
    private int amountSent = 0;
    private String raceName = "test";

    @Override
    public void send(List<Packet> packets) {
        this.packets = packets;
    }

    public String getRaceName() {
        return raceName;
    }

    public void setRaceName(String raceName) {
        this.raceName = raceName;
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
            if (packets.size() > 0)
            {
                List<Packet> local = packets;
                packets = new ArrayList<Packet>();



                try {
                    json = mapper.writeValueAsString(local);

                    JSONObject jo = new JSONObject();
                    JSONArray packets = (JSONArray) new JSONParser().parse(json);

                    jo.put("packets", packets);
                    jo.put("raceName", raceName);

                    System.out.println(jo.toJSONString());

                    //versturen
                    HttpClient client = new DefaultHttpClient();
                    HttpPost request = new HttpPost("teamb.feedient.com:8080/fastrada/api/packet");
                    StringEntity params = new StringEntity(json);
                    request.addHeader("content-type", "application/json");
                    request.setEntity(params);
                    HttpResponse response = client.execute(request);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
