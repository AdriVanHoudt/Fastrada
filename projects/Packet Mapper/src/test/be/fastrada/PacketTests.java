package be.fastrada;

import org.junit.Test;

import java.math.BigInteger;

import static junit.framework.Assert.assertEquals;

public class PacketTests {

    private Packet packet = new Packet("00 FF FF 0A 17 00 00 00 00");

    @Test
    public void createPacket() {
        assertEquals(packet.toString(), "00FFFF0A1700000000");
    }

    @Test
    public void packetToLong() {
        assertEquals(packet.toBigInt(), new BigInteger("18446473692633366528"));
    }

    @Test
    public void readByte1() {
        assertEquals(packet.applyMask(), 0);
    }
}
