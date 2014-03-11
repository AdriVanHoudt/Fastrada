package be.fastrada.controller;

import be.fastrada.pojo.PostPacketList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@Controller
@RequestMapping("/packet")
public class PacketController {
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<PostPacketList> addPacket(@RequestBody PostPacketList packetList) throws IOException {
        System.out.println(packetList);
        return new ResponseEntity<PostPacketList>(packetList, HttpStatus.CREATED);
    }
}
