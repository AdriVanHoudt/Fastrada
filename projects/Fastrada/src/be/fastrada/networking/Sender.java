package be.fastrada.networking;

import org.joda.time.Instant;

import java.util.ArrayList;

/**
 * Created by bavo on 5/03/14.
 */
public interface Sender
{
    public void send(ArrayList<Packet> packets);
    public int getAmountSent();
}
