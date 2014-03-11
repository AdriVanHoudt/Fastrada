package be.fastrada.networking;

import java.util.List;

public interface Sender extends Runnable {
    public void send(List<Packet> packets);

    public int getAmountSent();
}
