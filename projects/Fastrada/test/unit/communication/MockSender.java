package unit.communication;

import be.fastrada.networking.Packet;
import be.fastrada.tryouts.Sender;
import java.util.List;

public class MockSender implements Sender {
    private int amount;

    public MockSender() {
        amount = 0;
    }

    @Override
    public void send(List<Packet> packets) {
        amount += packets.size();
    }

    @Override
    public int getAmountSent() {
        return amount;
    }


    @Override
    public void run() {

    }
}
