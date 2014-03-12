package be.fastrada.controller;

import be.fastrada.config.SpringMongoConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("api")
public class RaceController {

    private ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
    private MongoOperations mongoOperations = (MongoOperations) ctx.getBean("mongoTemplate");

    @RequestMapping(value = "races", method= RequestMethod.GET)
    @ResponseBody
    public String getRaces() {
        // get ALL the races
        Query query = new Query();
        // return them
        return "foo";
    }
}
