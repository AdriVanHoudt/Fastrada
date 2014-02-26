package unit.packetmapper;

import be.fastrada.Dashboard;
import be.fastrada.packetmapper.Packet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.text.ParseException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;


public class PacketTest {
    private Packet packet;
    private Packet packet2;

    public PacketTest() throws FileNotFoundException {
        packet = new Packet("0001FFFF0A1700000000", new FileInputStream(new File("res/raw/structure.json")), new Dashboard());
        packet2 = new Packet("0000FFFF0A1700000000", new FileInputStream(new File("res/raw/structure.json")), new Dashboard());
    }


    @Test
    public void createPacket() {
        assertEquals("0001FFFF0A1700000000", packet.getContent());
        assertEquals("0001FFFF0A1700000000", packet.getReader().getContent());
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
        assertEquals(1, packet.getStructure().size());
    }


    @Test
    public void getSizeByMethod() {
        assertEquals(16, packet.getSize("setCurrentSpeed"));
        assertEquals(16, packet2.getSize("setCurrentRPM"));
        assertEquals(-1, packet2.getSize("setCurrentTemperature"));

    }


    @Test
    public void invokeMethod1() {
        int vehicleSpeed = 60;
        int maxRPM = 7000;
        double maxSpeed = 150.00;

        JSONArray arr = packet.getStructure();

        JSONObject jsonObject = (JSONObject) arr.get(0);



        assertEquals(true, packet.invokeMethod(jsonObject));

    }


    @Test
    public void processPacket() {
        assertTrue(packet.process());

    }

    @Test(expected=Error.class)
    public void parseExceptionTest() throws FileNotFoundException {
        String error = "parse error";
        assertEquals(error, new Packet("01 FF FF 0A 17 00 00 00 00", new FileInputStream(new File("res/raw/data.txt")), new Dashboard()));

    }

    @Test
    public void invokeNullMethod() throws FileNotFoundException {
        Packet packet = new Packet("01 FF FF 0A 17 00 00 00 00", new FileInputStream(new File("res/raw/structure.json")), new Dashboard());
        assertTrue(packet.invokeMethod(null));
    }


    @Test
    public void invokeTestUint8() throws FileNotFoundException {
        Packet packet = new Packet("00 01 FF AF 0A 17 00 00 00 00", new FileInputStream(new File("res/raw/structure.json")), new Dashboard());

        JSONObject put = new JSONObject();
        put.put("name", "setCurrentSpeed");
        put.put("size", 8);

        assertTrue(packet.invokeMethod(put));
    }

    @Test
    public void setContectTest() throws FileNotFoundException {
        Packet packet = new Packet("0001FFAF0A1700000000", new FileInputStream(new File("res/raw/structure.json")), new Dashboard());
        packet.setContent("0004FFAF0A1700000000");
        assertEquals("0004FFAF0A1700000000", packet.getContent());
    }

    @Test(expected = Error.class)
    public void EOFExceptionTest() throws FileNotFoundException {
        Packet packet = new Packet("FFFFFFAF0A1700000000", new FileInputStream(new File("res/raw/structure.json")), new Dashboard());
        packet.process();
    }

}