package be.fastrada.service;

import be.fastrada.pojo.Packet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PacketService {

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String COLLECTION_NAME = "packets";

    public void addPacket(Packet packet) {
        // If the collection does not exist, create it
        if (!mongoTemplate.collectionExists(Packet.class)) {
            mongoTemplate.createCollection(Packet.class);
        }

        // Create a new packet
        mongoTemplate.insert(packet, COLLECTION_NAME);
    }

}
