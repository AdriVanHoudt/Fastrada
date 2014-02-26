package be.fastrada.networking;

import android.util.Log;
import android.widget.Toast;

/**
 * Created by Thomas on 26/02/14.
 */
public class PacketSender extends Thread {
    public static final int BUFFER_SIZE = 500;
    private byte[] buffer;
    private boolean isRunning;

    public PacketSender() {
        this.buffer = new byte[BUFFER_SIZE];
        isRunning = true;
    }

    @Override
    public void run() {
        while (isRunning) {
            if (buffer.length == BUFFER_SIZE) {
                final byte[] bytesToSend = buffer;
                buffer = new byte[BUFFER_SIZE];
                sendBytes(bytesToSend);
            }


            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Log.d("[PacketSender]",e.getMessage());
            }
        }
    }

    private void sendBytes(byte[] bytesToSend) {

    }

    public void stopSending(){
        isRunning = false;
    }

}
