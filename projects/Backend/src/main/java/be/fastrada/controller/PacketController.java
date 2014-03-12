package be.fastrada.controller;

import be.fastrada.pojo.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("api")
public class PacketController {
    @RequestMapping(value="packet", method=RequestMethod.POST)
    @ResponseBody
    public String addPacket(@RequestBody Person person) {
        return "Saved person: " + person.toString();
    }

    @RequestMapping(value="packet", method=RequestMethod.GET)
    @ResponseBody
    public String getPacket() {
        return "Saved person: ";
    }
}
