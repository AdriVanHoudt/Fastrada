package be.fastrada;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by Thomas on 13/02/14.
 */
public class Server extends Thread {
    public static final int PORT_NUMBER = 6666;

    private boolean alive;
    private byte[] buffer;
    private DatagramSocket socket;
    private DatagramPacket packet;

    public Server() throws SocketException {
        this.socket = new DatagramSocket(PORT_NUMBER);
        this.buffer = new byte[10];
        this.packet = new DatagramPacket(buffer, buffer.length);
    }

    @Override
    public void run() {
        try {
            while (alive) {
                socket.receive(packet);
                System.out.println(cummulate(packet.getData()));
                Thread.sleep(100);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startListening() {
        if (isAlive()) return;
        start();
    }

    public DatagramPacket getPacket() {
        return packet;
    }

    private String cummulate(byte[] bytes) {
        String s = "";

        for (byte b : bytes) {
            s += b;
        }

        return s;
    }
}
