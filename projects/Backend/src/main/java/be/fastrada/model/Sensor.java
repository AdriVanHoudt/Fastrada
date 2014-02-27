package be.fastrada.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Sensor {

    @Id
    private String id;

    private Packet[] packets;

    public Sensor(Packet[] packets) {
        this.packets = packets;
    }

    public Packet[] getPackets() {
        return packets;
    }

    public void setPackets(Packet[] packets) {
        this.packets = packets;
    }
}
