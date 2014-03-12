package be.fastrada.controller;

import be.fastrada.pojo.PostPacketList;
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
    public String addPacket(@RequestBody PostPacketList packetList) {
        return "Saved packetList: " + packetList.getPackets().size();
    }

    @RequestMapping(value="packet", method=RequestMethod.GET)
    @ResponseBody
    public String getPacket() {
        return "Saved person: ";
    }
}
