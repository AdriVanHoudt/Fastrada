package be.fastrada.networking;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

public class BluetoothServer implements Runnable
{
    private final UUID MY_UUID;
    private BluetoothAdapter adapter;
    private BluetoothServerSocket serverSocket;
    private BluetoothSocket socket;
    private BufferedReader in;

    private Handler handler;

    public BluetoothServer(UUID MY_UUID, Handler handler) {
        this.MY_UUID = MY_UUID;
        adapter = BluetoothAdapter.getDefaultAdapter();

        this.handler = handler;
        startConnection();
    }

    private void startConnection() {
        try {
            serverSocket = adapter.listenUsingRfcommWithServiceRecord("server", MY_UUID);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    public void run() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

        Bundle b = new Bundle();
        b.putString("msg", "trol");
        Message msg = Message.obtain();
        msg.setData(b);
        handler.sendMessage(msg);

        while ( true)
        {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            if (serverSocket != null)
            {
                setupInput();

                handleMessages();
            }
        }
    }

    private void setupInput() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void handleMessages() {
        String input = "";
        try {
            input = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bundle b = new Bundle();
        b.putString("msg", input);
        Message msg = Message.obtain();
        msg.setData(b);
        handler.sendMessage(msg);
        //todo mesages afhandelen


    }
}
