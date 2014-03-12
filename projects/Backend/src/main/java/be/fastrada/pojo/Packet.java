package be.fastrada.pojo;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document()
public class Packet {

    @Id
    private String id;

    private double value;
    private DateTime time;
    private String raceId;
    private String type;

    public Packet(double value, DateTime time, String raceId, String type) {
        this.value = value;
        this.time = time;
        this.raceId = raceId;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public DateTime getTime() {
        return time;
    }

    public void setTime(DateTime time) {
        this.time = time;
    }

    public String getRaceId() {
        return raceId;
    }

    public void setRaceId(String raceId) {
        this.raceId = raceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Packet{" +
                "id='" + id + '\'' +
                ", value='" + value + '\'' +
                ", time=" + time +
                ", raceId='" + raceId + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
