package be.fastrada.packetmapper;

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
        if ((position + byteHexLength) <= content.length()){
            long result = Long.parseLong(content.substring(position, position + byteHexLength), 16);
            position += byteHexLength;
            return result;
        }
        throw new EOFException();
    }

    public void setContent(String content) {
        this.content = content.replaceAll(" ", "");
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
        this.position = 4;
        String id = content.substring(0,4);
        return Integer.parseInt(id, 16);
    }

    public void resetPosition() {
        this.position = 0;
    }
}