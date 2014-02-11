package be.fastrada;

import org.junit.Test;

import java.io.EOFException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

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
        try {
            checkPosition(0);
            assertEquals(1, packet.readUint8());
            checkPosition(2);

            // Reset to 0 position
            packet.resetPosition();
            checkPosition(0);
        } catch (EOFException e) {
            fail("Went passes end of hex when it shouldn't have");
        }
    }

    @Test
    public void readByte() {
        try {
            checkPosition(0);
            assertEquals(1, packet.readUint8()); // 01
            checkPosition(2);
        } catch (EOFException e) {
            fail("Went passes end of hex when it shouldn't have");
        }
    }

    @Test
    public void readMaxByte() {
        try{
            packet.setContent("FF");
            checkPosition(0);
            assertEquals(255, packet.readUint8());
            checkPosition(2);
        } catch (EOFException e) {
            fail("Went passes end of hex when it shouldn't have");
        }
    }

    @Test
    public void readShort() {
        try{
            checkPosition(0);
            assertEquals(511, packet.readUint16()); // 01FF
            checkPosition(4);
        } catch (EOFException e) {
            fail("Went passes end of hex when it shouldn't have");
        }
    }

    @Test
    public void readMaxShort() {
        try{
            packet.setContent("FFFF");
            checkPosition(0);
            assertEquals(65535, packet.readUint16());
            checkPosition(4);
        } catch (EOFException e) {
            fail("Went passes end of hex when it shouldn't have");
        }
    }

    @Test
    public void readInt() {
        try{
            checkPosition(0);
            assertEquals(33554186, packet.readUint32()); // 01FFFF0A
            checkPosition(8);
        } catch (EOFException e) {
            fail("Went passes end of hex when it shouldn't have");
        }
    }

    @Test
    public void readMaxInt() {
        try{
            packet.setContent("FFFFFFFF");
            checkPosition(0);
            assertEquals(4294967295L, packet.readUint32());
            checkPosition(8);
        } catch (EOFException e) {
            fail("Went passes end of hex when it shouldn't have");
        }
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
    @Test
    public void testEndOfHex() {
        try{
            packet.readUint32();
            packet.readUint32();
            packet.readUint16();
            fail("Pasted end of hex without error");
        } catch (EOFException ex) {
            // test passes
        }
    }

    /**
     * private methods
     */

    private void checkPosition(int expected){
        assertEquals(expected, packet.getPosition());
    }
}
