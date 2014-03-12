package be.fastrada.model;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document()
public class Packet {

    @Id
    private String id;

    private double content;
    private DateTime timestamp;
    private String raceId;
    private String type;

    public Packet(double content, DateTime timestamp, String raceId, String type) {
        this.content = content;
        this.timestamp = timestamp;
        this.raceId = raceId;
        this.type = type;
    }

    public String getId() {
        return id;
    }


    public double getContent() {
        return content;
    }

    public void setContent(double value) {
        this.content = value;
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
                ", content='" + content + '\'' +
                ", time=" + timestamp +
                ", raceId='" + raceId + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}