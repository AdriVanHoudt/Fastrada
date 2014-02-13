package be.fastrada.activities;

import android.annotation.TargetApi;
import android.os.Build;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import be.fastrada.PacketSender;
import be.fastrada.Server;
import org.junit.Test;

import java.io.IOException;
import java.net.*;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class be.fastrada.activities.MyActivityTest \
 * be.fastrada.activities.tests/android.test.InstrumentationTestRunner
 */
@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class MyActivityTest extends ActivityInstrumentationTestCase2<MyActivity> {
    private static final byte[] byteArray = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9};

    public MyActivityTest() {
        super("be.fastrada.activities", MyActivity.class);
    }

    @Test
    public void testBytesReceived() {
        Server server = null;
        try {
            server = new Server();
            server.startListening();

            final InetAddress inetAddress = InetAddress.getByName("192.168.43.1");
            final DatagramSocket socket = new DatagramSocket();
            final DatagramPacket packet = new DatagramPacket(byteArray, byteArray.length, inetAddress, Server.PORT_NUMBER);
            Log.d("ADRI", cummulate(packet.getData()));
            socket.send(packet);

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(cummulate(byteArray), cummulate(server.getPacket().getData()));
    }

    private String cummulate(byte[] bytes) {
        String s = "";

        for (byte b : bytes) {
            s += b;
        }

        return s;
    }
}
