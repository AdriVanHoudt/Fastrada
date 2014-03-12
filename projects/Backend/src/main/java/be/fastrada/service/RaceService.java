package be.fastrada.service;

import be.fastrada.model.Packet;
import be.fastrada.model.Race;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RaceService {

    @Autowired
    private MongoTemplate mongoTemplate;
    private static final String COLLECTION_NAME_RACES = "races";
    private static final String COLLECTION_NAME_PACKETS = "packets";

    public List getAllRaces() {
        return mongoTemplate.findAll(Race.class);
    }

    public Race getRaceById(String raceId) {
        Query query = new Query(Criteria.where("_id").is(new ObjectId(raceId)));
        return mongoTemplate.findOne(query, Race.class, COLLECTION_NAME_RACES);
    }

    public List getRaceDataById(String raceId) {
        Query queryData = new Query(Criteria.where("raceId").is(raceId));
        return mongoTemplate.find(queryData, Packet.class, COLLECTION_NAME_PACKETS);
    }
}
