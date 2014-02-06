package be.fastrada;

import java.math.BigInteger;

public class Packet {

    private String content;
    private int position;

    public Packet(String packetString) {
        this.content = packetString.replace(" ", "");
        this.position = 0;
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

    public long readUint8() {
        int byteHexLength = 2;
        long result = Long.parseLong(content.substring(position, position + byteHexLength), 16);
        position += byteHexLength;
        return result;
    }

    public long readUint16() {
        int byteHexLength = 4;
        long result = Long.parseLong(content.substring(position, position + byteHexLength), 16);
        position += byteHexLength;
        return result;
    }

    public long readUint32() {
        int byteHexLength = 8;
        long result = Long.parseLong(content.substring(position, position + byteHexLength), 16);
        position += byteHexLength;
        return result;
    }

    public void resetPosition() {
        this.position = 0;
    }

    public void setContent(String content) {
        this.content = content;
    }
}