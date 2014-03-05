package unit.communication;

import be.fastrada.networking.Packet;
import org.joda.time.Instant;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;

/**
 * Created by bavo on 5/03/14.
 */
public class PacketTest
{
    @Test
    public void testPacket(){
        byte[] bytes = new byte[10];
        Instant instant = new Instant();

        bytes[0] = (byte) 9;
        Packet p = new Packet(bytes, instant);

        assertEquals(bytes, p.getContent());
        assertEquals(instant, p.getTimestamp());
    }
}
