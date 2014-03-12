package be.fastrada.pojo;

import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document()
public class Packet {

    @Id
    private String id;

    private double value;
    private DateTime timestamp;
    private String raceId;
    private String type;

    public Packet(double value, DateTime timestamp, String raceId, String type) {
        this.value = value;
        this.timestamp = timestamp;
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

    public DateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
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
                ", time=" + timestamp +
                ", raceId='" + raceId + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
