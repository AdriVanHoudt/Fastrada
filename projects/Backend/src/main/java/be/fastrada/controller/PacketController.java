package be.fastrada.controller;

import be.fastrada.Exception.FastradaException;
import be.fastrada.packetmapper.PacketConfiguration;
import be.fastrada.packetmapper.PacketMapper;
import be.fastrada.packetmapper.PacketProcessor;
import be.fastrada.pojo.Packet;
import be.fastrada.pojo.PostPacketList;
import be.fastrada.service.PacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;

@Controller
@RequestMapping("api")
public class PacketController {

    @Autowired
    private PacketService packetService;

    @RequestMapping(value="packet", method=RequestMethod.POST)
    @ResponseBody
    public String addPacket(@RequestBody PostPacketList packetList) {
        // TODO insert packetmapper
        PacketConfiguration configuration = null;
        try {
            //servletcontext
            //web-inf/classes/structure.json
            //servletcontext.getRealPath(url)
            System.out.println(new File("/resources/structure.json"));
            System.out.println(this.getClass().getResourceAsStream("structure.json"));
            configuration = new PacketConfiguration(this.getClass().getResourceAsStream("structure.json"), "be.fastrada.packetmapper.PacketProcessor", new PacketProcessor());
        } catch (FastradaException e) {
            e.printStackTrace();
        }


        for (Packet p : packetList.getPackets()){
            PacketMapper packetMapper = new PacketMapper(configuration);
            packetMapper.setContent(p.getContent().getBytes(Charset.forName("UTF-8")));
            packetMapper.process();

            ArrayList<Double> values = packetMapper.getValues();
            ArrayList<String> types = packetMapper.getTypes();

            for (int i = 0; i < values.size(); i++) {
                be.fastrada.model.Packet packet = new be.fastrada.model.Packet(values.get(i), p.getTimestamp(), packetList.getRaceName(), "temp");
                packetService.addPacket(packet);
            }

        }

        return "Created " + packetList.getPackets().size() + " packets.";
    }
}
