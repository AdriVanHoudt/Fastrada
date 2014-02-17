package be.fastrada.activities;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class SendPackets {
    public static void main(String[] args) throws IOException, InterruptedException {
        final byte[] bytes = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};


        final InetAddress inetAddress = InetAddress.getByName("192.168.43.1");
        final DatagramSocket socket = new DatagramSocket();
        for (int i = 0; i <5; i++) {
            final DatagramPacket packet = new DatagramPacket(bytes, bytes.length, inetAddress, Server.PORT_NUMBER);
            socket.send(packet);
            Thread.sleep(100);
        }
    }
}
