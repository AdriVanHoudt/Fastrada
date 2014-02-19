package packetSender;

import java.io.IOException;
import java.net.*;

public class PacketSender {

    private InetAddress adres;
    private DatagramSocket socket;
    private int port;
    private int sendPackets;

    public PacketSender(String host, int port) {
        this.port = port;
        try {
            adres = InetAddress.getByName(host);
            socket = new DatagramSocket();
        } catch (UnknownHostException e) {
            throw new Error("unknowhost");
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void sendByte(byte[] bytes) {
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, adres, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            throw new Error("IOException");
            //e.printStackTrace();
        }
        sendPackets++;
    }


    public int getSendPackets() {
        return sendPackets;
    }
}
