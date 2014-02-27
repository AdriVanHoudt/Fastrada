package unit.packetmapper;

import be.fastrada.Dashboard;
import be.fastrada.packetmapper.Packet;
import be.fastrada.packetmapper.PacketConfiguration;
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
    private byte[] byteArray1 = { 0x00, 0x02, 0x00, 0x28 };
    private Packet packet;
    private Packet packet2;
    private PacketConfiguration configuration;

    public PacketTest() throws FileNotFoundException {
        configuration = new PacketConfiguration(new FileInputStream(new File("res/raw/structure.json")), "be.fastrada.Dashboard", new Dashboard());
        packet = new Packet(byteArray1, configuration);
        packet2 = new Packet(byteArray1, configuration);
    }


    @Test
    public void createPacket() {
        assertEquals(byteArray1, packet.getBuffer());
    }


    @Test
    public void getId() {
        assertEquals(1, packet.getId());
    }


    @Test
    public void getConfiguration() {
        assertFalse(configuration.getConfigFile() == null);
        assertTrue(configuration.getConfigFile() != null);
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

    @Test
    public void invokeNullMethod() throws FileNotFoundException {
        Packet packet = new Packet(byteArray1, configuration);
        assertTrue(packet.invokeMethod(null));
    }

    @Test
    public void invokeTestUint8() throws FileNotFoundException {
        Packet packet = new Packet(byteArray1, configuration);

        JSONObject put = new JSONObject();
        put.put("name", "setCurrentSpeed");
        put.put("size", 8);

        assertTrue(packet.invokeMethod(put));
    }

    @Test(expected = Error.class)
    public void EOFExceptionTest() throws FileNotFoundException {
        Packet packet = new Packet(byteArray1, configuration);
        packet.process();
    }

}