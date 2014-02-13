package be.fastrada;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class PacketSender extends Thread {
    private String serverIp;
    private InetAddress inetAddress;

    public PacketSender(String serverIp) throws UnknownHostException {
        this.serverIp = serverIp;
        this.inetAddress = InetAddress.getByName(serverIp);
    }

    @Override
    public void run() {
        super.run();
    }

    public void sendPacket(byte[] bytes) {
        if (!isAlive()) start();

    }
}
