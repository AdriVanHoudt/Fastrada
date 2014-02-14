package be.fastrada.packetmapper;


import java.io.EOFException;

public class PacketReader {

    private String content;
    private int position;

    public PacketReader(String packetString) {
        this.content = packetString.replace(" ", "");
        this.position = 0;
    }

    public byte readUint8() throws EOFException {
        int byteHexLength = 2;

        return (byte)readHexPart(byteHexLength);
    }

    public short readUint16() throws EOFException {
        int byteHexLength = 4;
        return (short)readHexPart(byteHexLength);
    }

    public int readUint32() throws EOFException {
        int byteHexLength = 8;
        return (int)readHexPart(byteHexLength);
    }

    private long readHexPart(int byteHexLength) throws EOFException{
        if ((position + byteHexLength) <= content.length()){
            long result = Long.parseLong(content.substring(position, position + byteHexLength), 16);
            position += byteHexLength;
            return result;
        }
            throw new EOFException();
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