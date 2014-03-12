package be.fastrada.controller;

import be.fastrada.pojo.Person;
import be.fastrada.pojo.PostPacketList;
import be.fastrada.pojo.SuccessResponse;
import be.fastrada.pojo.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
