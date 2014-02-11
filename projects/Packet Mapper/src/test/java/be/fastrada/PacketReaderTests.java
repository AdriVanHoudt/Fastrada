package be.fastrada;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class PacketReaderTests {

    private PacketReader packetReader = new PacketReader("01 FF FF 0A 17 00 00 00 00");

    @Test
    public void packetContent() {
        assertEquals(packetReader.getContent(), "01FFFF0A1700000000");
    }

    @Test
    public void packetToString() {
        assertEquals(packetReader.toString(), "01FFFF0A1700000000");
    }

    @Test
    public void readId() {
        assertEquals(1, packetReader.getId());
    }

    @Test
    public void readByteAndReset() {
        // Test reading a byte as test
        checkPosition(0);
        assertEquals(1, packetReader.readUint8());
        checkPosition(2);

        // Reset to 0 position
        packetReader.resetPosition();
        checkPosition(0);
    }

    @Test
    public void readByte() {
        checkPosition(0);
        assertEquals(1, packetReader.readUint8());
        checkPosition(2);
    }

    @Test
    public void readMaxByte() {
        packetReader.setContent("FF");
        checkPosition(0);
        assertEquals(255, packetReader.readUint8());
        checkPosition(2);
    }

    @Test
    public void readShort() {
        checkPosition(0);
        assertEquals(511, packetReader.readUint16()); // 01FF
        checkPosition(4);
    }

    @Test
    public void readMaxShort() {
        packetReader.setContent("FFFF");
        checkPosition(0);
        assertEquals(65535, packetReader.readUint16());
        checkPosition(4);
    }

    @Test
    public void readInt() {
        checkPosition(0);
        assertEquals(33554186, packetReader.readUint32());
        checkPosition(8);
    }

    @Test
    public void readMaxInt() {
        packetReader.setContent("FFFFFFFF");
        checkPosition(0);
        assertEquals(4294967295L, packetReader.readUint32());
        checkPosition(8);
    }

    @Test
    public void testByteAndShortAndInt() {
        // Read Byte
        readByte();
        packetReader.resetPosition();

        // Read Short
        readShort();
        packetReader.resetPosition();

        // Read Int
        readInt();
        packetReader.resetPosition();
    }

    // Test if we reached end of the hex!

    /**
     * private methods
     */
    private void checkPosition(int expected){
        assertEquals(expected, packetReader.getPosition());
    }
}
