package be.fastrada.networking;

import org.json.simple.JSONObject;

import java.util.List;

public class RestSender implements Sender {
    private List<Packet> packets;
    JSONObject parent;

    @Override
    public void send(List<Packet> packets) {
        for (Packet p : packets) {
            this.packets.add(p);
        }
    }

    @Override
    public int getAmountSent() {
        return 0;
    }

    @Override
    public void run() {
        //packets omzetten naar json tot 50 dan versturen
    }
}
