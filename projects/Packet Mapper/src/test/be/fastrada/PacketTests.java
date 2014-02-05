package be.fastrada;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class PacketTests {



    @Test
    public void createPacket() {
        Packet packet = new Packet("00 FF FF 0A 17 00 00 00 00");
        assertEquals(packet.toString(), "00FFFF0A1700000000");
    }
}
