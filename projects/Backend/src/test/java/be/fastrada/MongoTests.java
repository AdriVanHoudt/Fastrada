package be.fastrada;

import be.fastrada.config.SpringMongoConfig;
import be.fastrada.model.Packet;
import be.fastrada.model.Race;
import be.fastrada.model.Sensor;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import java.util.List;

public class MongoTests {

    @Test
    public void addRace() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

        // Make race with testdata
        Packet packet = new Packet("00011011010001101101000110110100011011010001101101000110110100011011010001101101", new DateTime());
        Packet packet2 = new Packet("00011000010001101101000110110100011011010000001101000110110100011011010001101101", new DateTime());
        Packet packet3 = new Packet("00011000010001101101000110110111111011010000001101000110110100011011010001101101", new DateTime());
        Packet packet4 = new Packet("00011000010000001101000110110100011011010000001101000110110100011011010001101101", new DateTime());
        Packet[] packets1 = new Packet[2];
        Packet[] packets2 = new Packet[2];
        packets1[0] = packet;
        packets1[1] = packet2;
        packets2[0] = packet3;
        packets2[1] = packet4;

        Sensor sensor1 = new Sensor("rpm", packets1);
        Sensor sensor2 = new Sensor("speed", packets2);

        Sensor[] sensors = new Sensor[2];
        sensors[0] = sensor1;
        sensors[1] = sensor2;

        Race race = new Race("TestRace", new DateTime(), sensors);

        // save to mongo
        mongoOperation.save(race);

        System.out.println("Race: " + race);

        // query db for the race
        Query query = new Query(Criteria.where("name").is("TestRace"));

        // find the race with the query
        Race foundRace = mongoOperation.findOne(query, Race.class);
        System.out.println("Found race: " + foundRace);

        // delete race
        // mongoOperation.remove(query, Race.class);

        // list races, should be 0
        List<Race> raceList = mongoOperation.findAll(Race.class);
        System.out.println("number of races: " + raceList.size());
    }
}
