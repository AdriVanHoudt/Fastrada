package be.fastrada.networking;


import android.os.*;
import android.os.Process;
import android.util.Log;
import be.fastrada.activities.Main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import static android.os.Process.setThreadPriority;

/**
 * The server that receives the packets from the car.
 * When it receives a packet it sends it to the handler for further processing.
 */
public class Server extends Thread {
    public static final int PORT_NUMBER = 6666;
    public static final int BUFFER_SIZE = 20;
    public static final String BUNDLE_BYTES_KEY = "Server.Bytes";

    private DatagramSocket socket;
    private byte[] buffer;

    public Server() throws SocketException {
        this.socket = new DatagramSocket(PORT_NUMBER);
        this.buffer = new byte[BUFFER_SIZE];

        start();
    }

    @Override
    public void run() {
        while (true) {
            final byte[] bytes = receiveBytes();

            try {
                handleMessage(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
            new WebRequest(5).start();
        }
    }

    private byte[] receiveBytes() {
        DatagramPacket packet = null;

        try {
            packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return packet.getData();
    }

    private void handleMessage(byte[] bytes) throws IOException {
        final Bundle bundle = new Bundle();
        final Message msg = Message.obtain();

        bundle.putByteArray(BUNDLE_BYTES_KEY, bytes);
        msg.setData(bundle);
        Main.mHandler.sendMessage(msg);
    }
}
