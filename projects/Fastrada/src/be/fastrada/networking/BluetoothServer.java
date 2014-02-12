package be.fastrada.networking;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

/**
 * Created by bavo on 7-2-14.
 */
public class BluetoothServer implements Runnable
{
    private final UUID MY_UUID;
    private BluetoothAdapter adapter;
    private BluetoothServerSocket serverSocket;
    private BluetoothSocket socket;
    private BufferedReader in;

    public BluetoothServer(UUID MY_UUID) {
        this.MY_UUID = MY_UUID;
        adapter = BluetoothAdapter.getDefaultAdapter();

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
        try {
            String input = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //todo mesages afhandelen

    }
}
