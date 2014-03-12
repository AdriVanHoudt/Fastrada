package be.fastrada.controller;

import be.fastrada.pojo.Packet;
import be.fastrada.pojo.PostPacketList;
import be.fastrada.service.PacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("api")
public class PacketController {

    @Autowired
    private PacketService packetService;

    @RequestMapping(value="packet", method=RequestMethod.POST)
    @ResponseBody
    public String addPacket(@RequestBody PostPacketList packetList) {
        for (Packet p : packetList.getPackets()) {
            packetService.addPacket(p);
        }

        return "Created " + packetList.getPackets().size() + " packets.";
    }
}
