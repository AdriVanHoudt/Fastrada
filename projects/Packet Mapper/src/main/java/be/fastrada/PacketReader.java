package be.fastrada;


import java.io.EOFException;

public class PacketReader {

    private String content;
    private int position;

    public PacketReader(String packetString) {
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

    public int getId() {
        return Integer.parseInt(content.substring(0, 2), 16);
    }

    public void resetPosition() {
        this.position = 0;
    }
}