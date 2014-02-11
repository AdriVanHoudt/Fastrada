package be.fastrada;

import org.json.simple.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PacketTests {
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
        assertFalse(packet.getConfigFile().equals(null));
        assertTrue(packet.getConfigFile() instanceof JSONObject);
    }

    @Test
    public void getStructure()  {
        assertEquals(2, packet.getStructure().size());
    }
}