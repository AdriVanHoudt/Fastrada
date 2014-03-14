package unit.communication;

import be.fastrada.networking.sending.PacketGrouper;
import be.fastrada.tryouts.Sender;
import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by bavo on 5/03/14.
 */
public class PacketGrouperTest {

    private PacketGrouper packetGrouper;

    public PacketGrouperTest() {
        packetGrouper = getPacketGrouper();
    }

    @Test
    public void addPacket() {
        byte[] bytes = new byte[10];

        packetGrouper.add(bytes);

        assertEquals(1, packetGrouper.getAmount());
    }

    private PacketGrouper getPacketGrouper() {
        return new PacketGrouper();
    }

    @Test
    public void checkContent() {
        byte[] bytes = new byte[10];

        for (int i = 0; i < 10; ++i) {
            byte b = (byte) i;
            bytes[i] = b;
        }

        packetGrouper = getPacketGrouper();
        packetGrouper.add(bytes);

        assertEquals(bytes, packetGrouper.getContent(0));
    }

    @Test
    public void addMultiplePackets() {
        packetGrouper = getPacketGrouper();

        byte[] bytes = new byte[10];

        for (int i = 0; i < 5; i++) {
            packetGrouper.add(bytes);
        }

        assertEquals(5, packetGrouper.getAmount());
    }

    @Test
    public void CheckMultipleContent() {
        packetGrouper = getPacketGrouper();

        byte[] bytes = new byte[10];
        bytes[0] = 1;
        packetGrouper.add(bytes);

        byte[] bytes2 = new byte[10];
        bytes2[0] = 4;
        packetGrouper.add(bytes2);

        assertEquals(bytes, packetGrouper.getContent(0));
        assertEquals(bytes2, packetGrouper.getContent(1));
    }

    @Test
    public void CheckTimestamp() {
        packetGrouper = getPacketGrouper();

        byte[] bytes = new byte[10];

        packetGrouper.add(bytes);
        DateTime instant = new DateTime();

        long millis = instant.getMillis() - packetGrouper.getTimestamp(0).getMillis();

        assertTrue(millis == 0);
    }

    @Test
    public void CheckMultipleTimestamps() throws InterruptedException {
        packetGrouper = getPacketGrouper();

        byte[] bytes = new byte[10];
        bytes[0] = 1;
        packetGrouper.add(bytes);
        Instant instant1 = new Instant();

        Thread.sleep(100);

        byte[] bytes2 = new byte[10];
        bytes2[0] = 4;
        packetGrouper.add(bytes2);
        Instant instant2 = new Instant();

        long millis1 = instant1.getMillis() - packetGrouper.getTimestamp(0).getMillis();
        long millis2 = instant2.getMillis() - packetGrouper.getTimestamp(1).getMillis();

        assertTrue("tijden komen niet overeen", millis1 < 5);
        assertTrue("tijden komen niet overeen", millis2 < 5);
    }

    @Test
    public void MaxPacketsInBundle() {
        byte[] bytes = new byte[10];

        packetGrouper = getPacketGrouper();

        //packetGrouper.setMax(50);
        Sender sender = new MockSender();
        //packetGrouper.setSender(sender);//TODO wegdoen

        for (int i = 0; i < 179; i++) {
            packetGrouper.add(bytes);
        }

        assertEquals(29, packetGrouper.getAmount());
    }

    @Test
    public void testAmountSent() {
        byte[] bytes = new byte[10];

        packetGrouper = getPacketGrouper();
        Sender sender = new MockSender();
        //packetGrouper.setSender(sender);//TODO wegdoen

        for (int i = 0; i < 179; i++) {
            packetGrouper.add(bytes);
        }

        assertEquals(150, sender.getAmountSent());
    }

    @Test
    public void testTimeout() throws InterruptedException {
        byte[] bytes = new byte[10];

        packetGrouper = getPacketGrouper();
        //packetGrouper.setMax(50);//TODO wegdoen
        Sender sender = new MockSender();
        //Sender sender = new RestSender("racename");//Sender sender = new RestSender();//TODO wegdoen
        //packetGrouper.setSender(sender);//TODO wegdoen


        Thread t = new Thread(packetGrouper);
        t.start();

        for (int i = 0; i < 179; i++) {
            packetGrouper.add(bytes);
        }

        Thread.sleep(1000);

        assertEquals(179, sender.getAmountSent());
    }
}
