package communication;

import be.fastrada.networking.PacketSender;
import junit.framework.Assert;
import org.junit.Test;

import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by Thomas on 7/02/14.
 */
public class PacketSenderTest {
    @Test
    public void testIncomingPacket() throws InterruptedException, SocketException, UnknownHostException {
        final PacketSender packetSender = new PacketSender("192.168.0.6");
        final Thread packetThread = new Thread(packetSender);

        packetSender.addPacket("Een test".getBytes());
        packetThread.start();

        Thread.sleep(1000); //My pc is too fast
        final int sentPackets = packetSender.getSentPackets();

        Assert.assertEquals(1, sentPackets);
        Assert.assertEquals(0, packetSender.getQueueSize());
    }

    @Test
    public void testMultipleIncoming() throws InterruptedException, SocketException, UnknownHostException {
        final PacketSender packetSender = new PacketSender("192.168.0.6");
        final Thread packetThread = new Thread(packetSender);

        for (int i = 0; i < 5; i++) {
            packetSender.addPacket(String.format("Packet %d", i).getBytes());
        }

        packetThread.start();
        Thread.sleep(1000); //My pc is too fast
        final int sentPackets = packetSender.getSentPackets();
        Assert.assertEquals(5, sentPackets);
    }
}
