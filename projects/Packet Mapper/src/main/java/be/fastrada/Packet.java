package be.fastrada;

import java.io.EOFException;

public class Packet {

    private String content;
    private int position;

    public Packet(String packetString) {
        this.content = packetString.replace(" ", "");
        this.position = 0;
    }

    public long readUint8() throws EOFException {
            int byteHexLength = 2;
            return readHexPart(byteHexLength);
    }

    public long readUint16() throws EOFException {
            int byteHexLength = 4;
            return readHexPart(byteHexLength);
    }

    public long readUint32() throws EOFException {
            int byteHexLength = 8;
            return readHexPart(byteHexLength);
    }

    public void resetPosition() {
        this.position = 0;
    }

    private long readHexPart(int byteHexLength) throws EOFException{
        if((position + byteHexLength) > content.length()){
           throw new EOFException();
        } else {
            long result = Long.parseLong(content.substring(position, position + byteHexLength), 16);
            position += byteHexLength;
            return result;
        }
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