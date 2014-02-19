package unit.packetmapper;

import be.fastrada.Dashboard;
import be.fastrada.packetmapper.Packet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;

import java.text.ParseException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;


public class PacketTest {
   /* private Packet packet = new Packet("01 FF FF 0A 17 00 00 00 00", "res/raw/structure.json");
    private Packet packet2 = new Packet("00 FF FF 0A 17 00 00 00 00", "res/raw/structure.json");

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
        assertEquals(3, packet.getStructure().size());
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

        JSONArray arr = packet.getStructure();

        JSONObject jsonObject = (JSONObject) arr.get(0);
        JSONObject jsonObject2 = (JSONObject) arr.get(1);


        assertEquals(true, packet.invokeMethod(jsonObject));
        assertEquals(true, packet.invokeMethod(jsonObject2));
    }

    @Test
    public void processPacket() {
        assertTrue(packet.process());

    }

    @Test(expected=Error.class)
    public void parseExceptionTest() {
        String error = "parse error";
        assertEquals(error, new Packet("01 FF FF 0A 17 00 00 00 00", "res/raw/data.txt"));

    }

    @Test(expected = Error.class)
    public void fileNotFoundException() {
        String error = "file not found";
        assertEquals(error, new Packet("01 FF FF 0A 17 00 00 00 00", "res/raw/structurcgsgsdge.json"));
    }

    @Test
    public void invokeNullMethod() {
        Packet packet = new Packet("01 FF FF 0A 17 00 00 00 00", "res/raw/structure.json");
        assertTrue(packet.invokeMethod(null));
    }

    @Test
    public void invokeTestUint8() {
        Packet packet = new Packet("01 FF AF 0A 17 00 00 00 00", "res/raw/structure.json");

        JSONObject put = new JSONObject();
        put.put("name", "setCurrentSpeed");
        put.put("size", 8);

        assertTrue(packet.invokeMethod(put));
    }

    @Test
    public void invokeTestUint32() {
        Packet packet = new Packet("01 FF AF 0A 17 00 00 00 00", "res/raw/structure.json");

        JSONObject put = new JSONObject();
        put.put("name", "setMaxSpeed");
        put.put("size", 32);

        assertTrue(packet.invokeMethod(put));
    }
                  */

}