package be.fastrada.networking;

import org.joda.time.Instant;

public class Packet {
    private byte[] content;
    private Instant timestamp;

    public Packet(byte[] content, Instant timestamp) {
        this.content = content;
        this.timestamp = timestamp;
    }

    public byte[] getContent() {
        return content;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
