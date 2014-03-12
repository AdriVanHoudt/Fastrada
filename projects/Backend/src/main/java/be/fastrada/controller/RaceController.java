package be.fastrada.controller;

import be.fastrada.config.SpringMongoConfig;
import be.fastrada.pojo.Packet;
import be.fastrada.pojo.Race;
import org.bson.types.ObjectId;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("api")
public class RaceController {

    private ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
    private MongoOperations mongoOperations = (MongoOperations) ctx.getBean("mongoTemplate");

    @RequestMapping(value = "races", method = RequestMethod.GET)
    @ResponseBody
    public List getRaces() {
        return mongoOperations.findAll(Race.class);
    }

    @RequestMapping(value = "races/{raceId}", method = RequestMethod.GET)
    @ResponseBody
    public Race getRaceById(@PathVariable(value = "raceId") String raceId) {
        Query query = new Query(Criteria.where("_id").is(new ObjectId(raceId)));
        return mongoOperations.findOne(query, Race.class);
    }

    @RequestMapping(value = "races/{raceId}/data", method = RequestMethod.GET)
    @ResponseBody
    public List getRaceDataById(@PathVariable(value = "raceId") String raceId) {
        Query queryRace = new Query(Criteria.where("_id").is(new ObjectId(raceId)));
        Race foundRace = mongoOperations.findOne(queryRace, Race.class);
        Query queryData = new Query(Criteria.where("raceId").is(raceId));
        List data = mongoOperations.find(queryData, Packet.class);
        return data;
    }
}
