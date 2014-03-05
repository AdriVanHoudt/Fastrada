package be.fastrada.networking;

import org.joda.time.Instant;

import java.util.ArrayList;

/**
 * Created by bavo on 5/03/14.
 */
public class PacketGrouper {
    private ArrayList<Packet> packets;
    private int amount;
    private int max;
    private Sender sender;

    public PacketGrouper() {
        amount = 0;
        packets = new ArrayList<Packet>();
    }

    public int getAmount() {
        return amount;
    }

    public void add(byte[] bytes) {
        amount++;

        Packet p = new Packet(bytes, new Instant());
        packets.add(p);

        if (amount == max)
        {
            amount = 0;
            sender.send(packets);
        }
    }

    public byte[] getContent(int i) {
        return packets.get(i).getContent();
    }

    public Instant getTimestamp(int i) {
        return packets.get(i).getTimestamp();
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }
}
