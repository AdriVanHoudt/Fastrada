package be.fastrada;

import org.junit.Test;

import java.io.EOFException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

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
    public void readByteAndReset() {
        // Test reading a byte as test
        try {
            checkPosition(0);
            assertEquals(1, packetReader.readUint8());
            checkPosition(2);

            // Reset to 0 position
            packetReader.resetPosition();
            checkPosition(0);
        } catch (EOFException e) {
            fail("Went passed end of hex when it shouldn't have");
        }
    }

    @Test
    public void readByte() {
        try {
            checkPosition(0);
            assertEquals(1, packetReader.readUint8()); // 01
            checkPosition(2);
        } catch (EOFException e) {
            fail("Went passed end of hex when it shouldn't have");
        }
    }

    @Test
    public void readMaxByte() {
        try{
            packetReader.setContent("FF");
            checkPosition(0);
            assertEquals(255, packetReader.readUint8());
            checkPosition(2);
        } catch (EOFException e) {
            fail("Went passed end of hex when it shouldn't have");
        }
    }

    @Test
    public void readShort() {
        try{
            checkPosition(0);
            assertEquals(511, packetReader.readUint16()); // 01FF
            checkPosition(4);
        } catch (EOFException e) {
            fail("Went passed end of hex when it shouldn't have");
        }
    }

    @Test
    public void readMaxShort() {
        try{
            packetReader.setContent("FFFF");
            checkPosition(0);
            assertEquals(65535, packetReader.readUint16());
            checkPosition(4);
        } catch (EOFException e) {
            fail("Went passed end of hex when it shouldn't have");
        }
    }

    @Test
    public void readInt() {
        try{
            checkPosition(0);
            assertEquals(33554186, packetReader.readUint32()); // 01FFFF0A
            checkPosition(8);
        } catch (EOFException e) {
            fail("Went passed end of hex when it shouldn't have");
        }
    }

    @Test
    public void readMaxInt() {
        try{
            packetReader.setContent("FFFFFFFF");
            checkPosition(0);
            assertEquals(4294967295L, packetReader.readUint32());
            checkPosition(8);
        } catch (EOFException e) {
            fail("Went passed end of hex when it shouldn't have");
        }
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
    @Test
    public void testEndOfHex() {
        try{
            packetReader.readUint32();
            packetReader.readUint32();
            packetReader.readUint16();
            fail("Pasted end of hex without error");
        } catch (EOFException ex) {
            // test passes
        }
    }

    /**
     * private methods
     */
    private void checkPosition(int expected){
        assertEquals(expected, packetReader.getPosition());
    }
}
