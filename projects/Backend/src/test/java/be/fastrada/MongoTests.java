package be.fastrada;


import be.fastrada.model.Packet;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import be.fastrada.config.SrpingMongoConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

public class MongoTests {

    @Test
    public void addRace() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SrpingMongoConfig.class);
        MongoOperations mongoOperations = (MongoOperations) ctx.getBean("mongoTemplate");

        Packet p = new Packet("00011011010001101101000110110100011011010001101101000110110100011011010001101101", new DateTime());

    }
}
