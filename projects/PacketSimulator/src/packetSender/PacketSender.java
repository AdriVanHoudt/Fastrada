package packetSender;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.*;


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

        PacketSender sender = new PacketSender(url, port);

        sender.runSimulator();
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

    public int runSimulator() {

        int linesRead = 0;

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("src/resources/CANData"));
            String line = br.readLine();


            while(!line.equals("") || line != null) {
                linesRead++;
                this.sendByte(line.replaceAll(" ","").getBytes());
                line = br.readLine();
                if(line == null){break;}

                //System.out.println(linesRead);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println(linesRead);

        return linesRead;
    }
}
