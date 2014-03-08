package be.fastrada.tryouts;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Primitive class to send the packets to the backend server.
 */
public class PacketSender implements Runnable {
    public static final int SERVER_PORT = 1234;

    private ConcurrentLinkedQueue<byte[]> packetQueue;
    private String serverIp;
    private int sentPackets;
    private DatagramSocket socket;
    private InetAddress inetAddress;

    public PacketSender(String serverIp) throws SocketException, UnknownHostException {
        this.serverIp = serverIp;
        this.packetQueue = new ConcurrentLinkedQueue<byte[]>();
        this.socket = new DatagramSocket();
        this.inetAddress = InetAddress.getByName(serverIp);
    }

    public void addPacket(byte[] packet) {
        this.packetQueue.add(packet);
    }

    @Override
    public void run() {
        while (true) {
            if (packetQueue.peek() == null) continue;

            final byte[] bytes = packetQueue.poll();
            try {
                sendPacket(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getSentPackets() {
        return sentPackets;
    }

    public int getQueueSize() {
        return packetQueue.size();
    }

    //TODO test if packet actually reaches server
    private void sendPacket(byte[] bytes) throws IOException {
        final DatagramPacket packet = new DatagramPacket(bytes, bytes.length, inetAddress, SERVER_PORT);

        socket.send(packet);
        sentPackets++;
    }
}
