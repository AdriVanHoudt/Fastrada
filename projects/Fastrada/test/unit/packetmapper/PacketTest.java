package unit.packetmapper;

import be.fastrada.Dashboard;
import be.fastrada.packetmapper.Packet;
import org.json.simple.JSONArray;
import org.junit.Test;
import org.mockito.Mockito;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;


public class PacketTest {
    private Packet packet = new Packet("01 FF FF 0A 17 00 00 00 00");
    private Packet packet2 = new Packet("00 FF FF 0A 17 00 00 00 00");

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
        assertEquals(-1, packet.getSize("setMaxRPM"));
        assertEquals(16, packet.getSize("setCurrentSpeed"));
    }

    @Test
    public void invokeMethod1() {
        int vehicleSpeed = 60;
        int maxRPM = 7000;
        double maxSpeed = 150.00;
        assertEquals(true, packet.invokeMethod("setCurrentSpeed", vehicleSpeed));
        assertEquals(true, packet2.invokeMethod("setMaxRPM", maxRPM));
        assertEquals(true, packet2.invokeMethod("setMaxSpeed", maxSpeed));
    }

    @Test
    public void processPacket() {
        assertTrue(packet.process());

    }
}