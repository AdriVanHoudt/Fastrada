package be.fastrada.service;

import be.fastrada.config.SpringMongoConfig;
import be.fastrada.model.Packet;
import be.fastrada.model.Race;
import org.bson.types.ObjectId;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RaceService {

    private ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
    private MongoOperations mongoOperations = (MongoOperations) ctx.getBean("mongoTemplate");

    public List getAllRaces() {
        return mongoOperations.findAll(Race.class);
    }

    public Race getRaceById(String raceId) {
        Query query = new Query(Criteria.where("_id").is(new ObjectId(raceId)));
        return mongoOperations.findOne(query, Race.class);
    }

    public List getRaceDataById(String raceId) {
        Query queryData = new Query(Criteria.where("raceId").is(raceId));
        return mongoOperations.find(queryData, Packet.class);
    }
}
