package be.fastrada.tryouts;

import be.fastrada.networking.Packet;

import java.util.List;

public interface Sender extends Runnable {
    public void send(List<Packet> packets);

    public int getAmountSent();
}
