package be.fastrada.networking;

import org.joda.time.Instant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PacketGrouper implements Runnable {
    private List<Packet> packets;
    private int amount;
    private int max;
    private Sender sender;
    private Instant latestReceived;
    public static final int TIMEOUT = 900;

    public PacketGrouper() {
        amount = 0;
        packets = Collections.synchronizedList(new ArrayList<Packet>());
    }

    public int getAmount() {
        return amount;
    }

    public void add(byte[] bytes) {
        amount++;
        latestReceived = new Instant();

        Packet p = new Packet(bytes, new Instant());
        packets.add(p);

        if (amount == max) {
            amount = 0;
            sender.send(packets);
            packets.clear();
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
        new Thread(sender).start();
    }

    @Override
    public void run() {
        while (true) {
            long timeout = new Instant().getMillis() - latestReceived.getMillis();

            if (timeout > TIMEOUT) {
                flush();
            }
        }
    }

    private void flush() {
        amount = 0;
        sender.send(packets);
        packets.clear();
    }
}
