import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by bavo on 18/02/14.
 */
public class UdpServer implements Runnable {
    private final int PORT;
    private DatagramSocket datagramSocket;
    private int packetsReceived;
    private int byteSize;
    private byte[] buffer;

    public UdpServer(int byteSize, int port) {
        PORT = port;
        try {
            datagramSocket = new DatagramSocket(PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        packetsReceived = 0;
        this.byteSize = byteSize;
        buffer = new byte[byteSize];
    }

    @Override
    public void run() {
        while (true) {
            DatagramPacket packet = null;
            try {
                packet = new DatagramPacket(buffer, byteSize);
                datagramSocket.receive(packet);
                packetsReceived++;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public void closeConnection()
    {
        datagramSocket.close();
    }

    public int getPacketsReceived() {
        return packetsReceived;
    }
}
