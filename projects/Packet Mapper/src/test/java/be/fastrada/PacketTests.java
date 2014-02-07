package be.fastrada;

import org.junit.Test;

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
    public void readByteAndReset() {
        // Test reading a byte as test
        checkPosition(0);
        assertEquals(1, packet.readUint8());
        checkPosition(2);

        // Reset to 0 position
        packet.resetPosition();
        checkPosition(0);
    }

    @Test
    public void readByte() {
        checkPosition(0);
        assertEquals(1, packet.readUint8());
        checkPosition(2);
    }

    @Test
    public void readMaxByte() {
        packet.setContent("FF");
        checkPosition(0);
        assertEquals(255, packet.readUint8());
        checkPosition(2);
    }

    @Test
    public void readShort() {
        checkPosition(0);
        assertEquals(511, packet.readUint16()); // 01FF
        checkPosition(4);
    }

    @Test
    public void readMaxShort() {
        packet.setContent("FFFF");
        checkPosition(0);
        assertEquals(65535, packet.readUint16());
        checkPosition(4);
    }

    @Test
    public void readInt() {
        checkPosition(0);
        assertEquals(33554186, packet.readUint32());
        checkPosition(8);
    }

    @Test
    public void readMaxInt() {
        packet.setContent("FFFFFFFF");
        checkPosition(0);
        assertEquals(4294967295L, packet.readUint32());
        checkPosition(8);
    }

    @Test
    public void testByteAndShortAndInt() {
        // Read Byte
        readByte();
        packet.resetPosition();

        // Read Short
        readShort();
        packet.resetPosition();

        // Read Int
        readInt();
        packet.resetPosition();
    }

    // Test if we reached end of the hex!

    /**
     * private methods
     */

    private void checkPosition(int expected){
        assertEquals(expected, packet.getPosition());
    }

}
