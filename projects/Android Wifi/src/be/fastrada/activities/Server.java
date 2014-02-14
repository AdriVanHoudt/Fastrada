package be.fastrada.activities;


import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;

public class Server extends AsyncTask<Void, Void, Void> {
    public static final int PORT_NUMBER = 6666;

    private DatagramSocket socket;
    private byte[] buffer;

    public Server() throws SocketException {
        this.socket = new DatagramSocket(PORT_NUMBER);
        this.buffer = new byte[10];
    }

    @Override
    protected Void doInBackground(Void... voids) {
        while (true) {
            try {
                Log.d("POGING2", Arrays.toString(receiveByte()));
                new WebRequest().start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] receiveByte() throws IOException {
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
}
