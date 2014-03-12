package be.fastrada.pojo;

import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document()
public class Packet {

    @Id
    private String id;

    private String content;
    private DateTime timestamp;

    public Packet() {

    }

    public Packet(String value, DateTime time) {
        this.content = value;
        this.timestamp = time;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Packet{" +
                "id='" + id + '\'' +
                ", value='" + content + '\'' +
                ", time=" + timestamp +
                '}';
    }
}
