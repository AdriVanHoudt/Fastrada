package be.fastrada.activities;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;

public class Server extends AsyncTask<Void, Void, Void> {
    public static final int PORT_NUMBER = 6666;

    private DatagramSocket socket;
    private byte[] buffer;

    public Server(Context context) throws SocketException {
        this.socket = new DatagramSocket(PORT_NUMBER);
        this.buffer = new byte[10];
    }

    @Override
    protected Void doInBackground(Void... voids) {
        while (true) {
            final byte[] bytes = receiveByte();

            new WebRequest().start();
        }
    }

    private byte[] receiveByte() {
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
