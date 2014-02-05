package be.fastrada;

public class Packet {

    private String content;

    public Packet(String packetString) {
        content = packetString.replace(" ", "");
    }

    @Override
    public String toString() {
        return  content;
    }
}
