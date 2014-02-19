package packetSender;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.*;

import static org.junit.Assert.assertEquals;

public class PacketSender {

    private InetAddress adres;
    private DatagramSocket socket;
    private int port;
    private int sendPackets;

    public PacketSender(String host, int port) {
        this.port = port;
        try {
            adres = InetAddress.getByName(host);
            socket = new DatagramSocket();
        } catch (UnknownHostException e) {
            throw new Error("unknowhost");
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = 6666;
        String url = "192.168.43.1";
        BufferedReader br;

        try {
            br = new BufferedReader(new FileReader("src/resources/CANData"));

            String line = br.readLine();

            PacketSender sender = new PacketSender(url, port);

            while(!line.equals("") || line != null) {
                sender.sendByte(line.getBytes());
                line = br.readLine();
                if(line == null){break;}
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendByte(byte[] bytes) {
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, adres, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            throw new Error("IOException");
            //e.printStackTrace();
        }
        sendPackets++;
    }


    public int getSendPackets() {
        return sendPackets;
    }
}
