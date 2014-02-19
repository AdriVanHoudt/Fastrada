package packetSender;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PacketSenderTest {
    private static int portCounter = 1234;
    private String url = "127.0.0.1";

    @Test
    public void sendPacket() throws InterruptedException {
        String message = "test message";
        int port = getPort();

        PacketSender sender = new PacketSender(url, port);

        //server maken voor te ontvangen
        UdpServer server = getServer(message, port);

        sender.sendByte(message.getBytes());

        Thread.sleep(1000);

        assertEquals(1, server.getPacketsReceived());
        assertEquals(1, sender.getSendPackets());
    }

    @Test
    public void sendMultiplePackets() throws InterruptedException {
        String message = "test message";
        int port = getPort();

        PacketSender sender = new PacketSender(url, port);

        //server maken voor te ontvangen
        UdpServer server = getServer(message, port);

        int aantal = 5;

        for (int i = 0; i < aantal; ++i)
        {
            sender.sendByte(message.getBytes());
        }

        Thread.sleep(5000);

        assertEquals(aantal, server.getPacketsReceived());
        assertEquals(aantal, sender.getSendPackets());
    }

    @Test(expected=Error.class)
    public void testHostException(){
        new PacketSender("blabla", getPort());
    }

    @Test
    public void testPacketContent() throws IOException, InterruptedException {
        int port = getPort();
        // read from file


        BufferedReader br = new BufferedReader(new FileReader("src/resources/CANData"));
        String lines[] = new String[2];

        for(int i = 0; i <= 1; i++){
            lines[i] = br.readLine();
        }

        // make server
        UdpServer server = getServer(lines[0], port);
        // send lines
        PacketSender sender = new PacketSender(url, port);

        for (int i = 0; i <= 1; ++i)
        {
            sender.sendByte(lines[i].getBytes());
        }

        Thread.sleep(5000);

        // assert lines
        assertTrue(lines[1].equals(new String(server.getBuffer())));
    }

    @Test
    public void runSimulator() throws IOException {
        int port = getPort();
        int linesRead = 0;

        BufferedReader br = new BufferedReader(new FileReader("src/resources/CANData"));

        String line = br.readLine();


        UdpServer server = getServer(line, port);
        PacketSender sender = new PacketSender(url, port);

        while(!line.equals("") || line != null) {
            linesRead++;
            sender.sendByte(line.getBytes());
            line = br.readLine();
            if(line == null){break;}
        }

        System.out.println("Lines read: " + linesRead);

        assertEquals(linesRead, server.getPacketsReceived());
    }

    private UdpServer getServer(String message, int port) {
        //System.out.println(port);
        UdpServer server = new UdpServer(message.getBytes().length, port);
        Thread serverThread = new Thread(server);
        serverThread.start();

        return server;
    }

    private int getPort(){
        return ++portCounter;
    }
}
