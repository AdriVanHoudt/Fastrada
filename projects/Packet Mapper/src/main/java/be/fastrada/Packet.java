package be.fastrada;

public class Packet {

    private String content;
    private int position;

    public Packet(String packetString) {
        this.content = packetString.replace(" ", "");
        this.position = 0;
    }



    public long readUint8() {
        int byteHexLength = 2;
        return readLong(byteHexLength);
    }

    public long readUint16() {
        int byteHexLength = 4;
        return readLong(byteHexLength);
    }

    public long readUint32() {
        int byteHexLength = 8;
        return readLong(byteHexLength);
    }

    public void resetPosition() {
        this.position = 0;
    }

    private long readLong(int byteHexLength) {
        long result = Long.parseLong(content.substring(position, position + byteHexLength), 16);
        position += byteHexLength;
        return result;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }

    public String getContent() {
        return content;
    }

    public int getPosition() {
        return this.position;
    }
}
