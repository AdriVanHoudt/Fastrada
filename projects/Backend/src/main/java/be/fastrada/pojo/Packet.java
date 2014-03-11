package be.fastrada.pojo;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document()
public class Packet {

    @Id
    private String id;

    private String value;
    private DateTime time;

    public Packet() {

    }

    public Packet(String value, DateTime time) {
        this.value = value;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public DateTime getTime() {
        return time;
    }

    public void setTime(DateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Packet{" +
                "id='" + id + '\'' +
                ", value='" + value + '\'' +
                ", time=" + time +
                '}';
    }
}
