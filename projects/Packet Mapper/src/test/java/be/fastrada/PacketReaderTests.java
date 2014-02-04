package java.be.fastrada;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PacketReaderTests {

    /**
     *  1. read file line by line (A line is 1 packet)
     *  2. get id from line
     *  3. get config matching id
     *  4. decode packet by config
     *  5. check output
     */

    private PacketReader reader = new PacketReader();

    @BeforeClass
    public void before() {
        reader.openFile("test/res/data.txt");
    }

    @Test
    public void readPacket1FromFile() {
        Packet packet = reader.readNextPacket();
        assertEquals(packet.toString(), "00 FF FF 0A 17 00 00 00 00");
    }

    public void readPacket2FromFile() {
        Packet packet = reader.readNextPacket();
        assertEquals(packet.toString(), "00 FF FF 9A 19 00 00 00 00");
    }
}
