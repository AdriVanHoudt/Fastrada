package be.fastrada.pojo;

import be.fastrada.pojo.Packet;

import java.util.List;

public class PostPacketList {
    private String raceName;
    private List<Packet> packets;

    public PostPacketList() {

    }

    public String getRaceName() {
        return raceName;
    }

    public void setRaceName(String raceName) {
        this.raceName = raceName;
    }

    public List<Packet> getPackets() {
        return packets;
    }

    public void setPackets(List<Packet> packets) {
        this.packets = packets;
    }
}
