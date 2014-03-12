package be.fastrada;

import be.fastrada.config.SpringMongoConfig;
import be.fastrada.pojo.Packet;
import be.fastrada.pojo.Race;
import be.fastrada.pojo.Sensor;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

public class MongoTests {

    @Test
    public void addRace() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

        // Gen race
        Race race = new Race("TestRace", new DateTime());

        mongoOperation.save(race);

        // Gen sensors
        Sensor engine = new Sensor("Engine", new String[]{"rpm", "temp"});
        Sensor carData = new Sensor("CarData", new String[]{"speed", "gear"});

        mongoOperation.save(engine);
        mongoOperation.save(carData);

        // Gen packets
        Packet packet1 = new Packet(5000, new DateTime(), race.getId(), "rpm");
        Packet packet2 = new Packet(80, new DateTime(), race.getId(), "temp");
        Packet packet3 = new Packet(100, new DateTime(), race.getId(), "speed");
        Packet packet4 = new Packet(5, new DateTime(), race.getId(), "gear");

        mongoOperation.save(packet1);
        mongoOperation.save(packet2);
        mongoOperation.save(packet3);
        mongoOperation.save(packet4);


        /* mongo operations examples
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
        */
    }
}
