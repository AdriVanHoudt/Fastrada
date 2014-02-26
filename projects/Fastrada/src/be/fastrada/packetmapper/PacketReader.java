package be.fastrada.packetmapper;

import java.io.EOFException;
import java.util.Arrays;

/**
 * Class that reads the bytes of a packet.
 */
public class PacketReader {
    private String content;
    private int position;

    public PacketReader(byte[] bytes) {
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

    private long readHexPart(int byteHexLength) throws EOFException {
        if ((position + byteHexLength) <= content.length()) {
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
        String id = content.substring(0, 4);
        return Integer.parseInt(id, 16);
    }

    public void resetPosition() {
        this.position = 0;
    }

    /**
     * This methods performs an AND operation on every byte in the bytearray,
     * except the first 2 (The ID).
     */
    public byte[] andAllExceptId(byte[] bytes) {
        final byte[] copyOfBytes = Arrays.copyOf(bytes, bytes.length);

        for (int i = 2; i < bytes.length; i++) {
            copyOfBytes[i] = bytes[i] & 0xFF;
        }
    }
}