package be.fastrada.pojo;

import org.joda.time.DateTime;

public class Packet {
    private String content;
    private DateTime timestamp;
    private String type;

    public Packet() {

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}