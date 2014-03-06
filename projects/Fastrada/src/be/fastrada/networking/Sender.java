package be.fastrada.networking;

import java.util.List;

/**
 * Created by bavo on 5/03/14.
 */
public interface Sender extends Runnable {
    public void send(List<Packet> packets);
    public int getAmountSent();

}
