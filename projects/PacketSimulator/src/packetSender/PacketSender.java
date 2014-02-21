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
            throw new Error("unknown host");
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
            Thread.sleep(50);
        } catch (IOException e) {
            throw new Error("IOException");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sendPackets++;
    }


    public int getSendPackets() {
        return sendPackets;
    }

    public int runSimulator() {

        int linesRead = 0;

        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("src/resources/CANData"));
            String line = br.readLine();
            line = line.replaceAll("\t", "");

            while (!line.equals("") || line != null) {
                linesRead++;
                byte[] bytes = hexStringToByteArray(line);
                this.sendByte(bytes);
                line = br.readLine();
                if (line == null) {
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println(linesRead);

        return linesRead;
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}