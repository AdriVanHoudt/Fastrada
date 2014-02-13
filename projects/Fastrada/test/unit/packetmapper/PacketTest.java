package unit.packetmapper;

import be.fastrada.packetmapper.Packet;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class PacketTest {
    private Packet packet = new Packet("01 FF FF 0A 17 00 00 00 00");

    @Test
    public void createPacket() {
        assertEquals("01FFFF0A1700000000", packet.getContent());
        assertEquals("01FFFF0A1700000000", packet.getReader().getContent());
    }

    @Test
    public void getId() {
        assertEquals(1, packet.getId());
    }

    @Test
    public void getConfiguration() {
        assertFalse(packet.getConfigFile() == null);
        assertTrue(packet.getConfigFile() != null);
    }

    @Test
    public void getStructure()  {
        assertEquals(2, packet.getStructure().size());
    }

    @Test
    public void getSizeByMethod() {
        assertEquals(8, packet.getSize("setGear"));
    }

    @Test
    public void invokeMethod1() {
       //Dashboard mockedDb = mock(Dashboard.class);
    }
}