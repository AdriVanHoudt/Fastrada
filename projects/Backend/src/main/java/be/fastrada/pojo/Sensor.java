package be.fastrada.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;

@Document
public class Sensor {

    @Id
    private String id;

    private String name;
    private Packet[] packets;

    public Sensor(String name, Packet[] packets) {
        this.packets = packets;
        this.name = name;
    }

    public Packet[] getPackets() {
        return packets;
    }

    public void setPackets(Packet[] packets) {
        this.packets = packets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", packets=" + Arrays.toString(packets) +
                '}';
    }
}
