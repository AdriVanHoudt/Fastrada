package be.fastrada;

import org.junit.Test;

import java.math.BigInteger;

import static junit.framework.Assert.assertEquals;

public class PacketTests {

    private Packet packet = new Packet("01 FF FF 0A 17 00 00 00 00");

    @Test
    public void packetContent() {
        assertEquals(packet.getContent(), "01FFFF0A1700000000");
    }

    @Test
    public void packetToString() {
        assertEquals(packet.toString(), "01FFFF0A1700000000");
    }

    @Test
    public void changePositionBy1ByteAndReset() {
        // Test reading a byte as test
        assertEquals(0, packet.getPosition());
        assertEquals(1, packet.readUint8());
        assertEquals(2, packet.getPosition());

        // Reset to 0 position
        packet.resetPosition();
        assertEquals(0, packet.getPosition());

    }

    @Test
    public void readByte() {
        assertEquals(0, packet.getPosition());
        assertEquals(1, packet.readUint8());
        assertEquals(2, packet.getPosition());
    }

    @Test
    public void readMaxByte() {
        packet.setContent("FF");
        assertEquals(0, packet.getPosition());
        assertEquals(255, packet.readUint8());
        assertEquals(2, packet.getPosition());
    }

    @Test
    public void readShort() {
        assertEquals(0, packet.getPosition());
        assertEquals(511, packet.readUint16()); // 01FF
        assertEquals(4, packet.getPosition());
    }

    @Test
    public void readMaxShort() {
        packet.setContent("FFFF");
        assertEquals(0, packet.getPosition());
        assertEquals(65535, packet.readUint16());
        assertEquals(4, packet.getPosition());
    }

    @Test
    public void readInt() {
        assertEquals(0, packet.getPosition());
        assertEquals(33554186, packet.readUint32());
        assertEquals(8, packet.getPosition());
    }

    @Test
    public void readMaxInt() {
        packet.setContent("FFFFFFFF");
        assertEquals(0, packet.getPosition());
        assertEquals(4294967295L, packet.readUint32());
        assertEquals(8, packet.getPosition());
    }

    @Test
    public void testByteAndShortAndInt() {
        // Read Byte
        assertEquals(0, packet.getPosition());
        assertEquals(1, packet.readUint8());
        assertEquals(2, packet.getPosition());

        // Read Short
        assertEquals(2, packet.getPosition());
        assertEquals(65535, packet.readUint16());
        assertEquals(6, packet.getPosition());

        // Read Int
        assertEquals(6, packet.getPosition());
        assertEquals(169279488, packet.readUint32());
        assertEquals(14, packet.getPosition());
    }

    // Test if we reached end of the hex!
}
