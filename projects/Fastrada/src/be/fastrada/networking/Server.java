package be.fastrada.networking;


import android.os.*;
import android.os.Process;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import static android.os.Process.setThreadPriority;

public class Server extends AsyncTask<Void, Void, Void> {
    public static final int PORT_NUMBER = 6666;
    public static final int BUFFER_SIZE = 10;
    public static final String BUNDLE_BYTES_KEY = "Server.Bytes";

    private DatagramSocket socket;
    private byte[] buffer;
    private Handler handler;

    public Server(Handler handler) throws SocketException {
        this.socket = new DatagramSocket(PORT_NUMBER);
        this.buffer = new byte[BUFFER_SIZE];
        this.handler = handler;

        execute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

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
        handler.sendMessage(msg);
    }
}
