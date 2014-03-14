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
            //configuration = new PacketConfiguration(ClassLoader.getSystemResourceAsStream("structure.json"), "be.fastrada.packetmapper.PacketProcessor", new PacketProcessor());
            configuration = new PacketConfiguration(this.getClass().getResourceAsStream("/structure.json"), "be.fastrada.packetmapper.PacketProcessor", new PacketProcessor());

        } catch (FastradaException e) {
            e.printStackTrace();
        }

        PacketMapper packetMapper = new PacketMapper(configuration);
        for (Packet p : packetList.getPackets()){

            packetMapper.clearArrays();
            packetMapper.setContent(hexStringToByteArray(p.getContent()));
            packetMapper.process();

            ArrayList<Double> values = packetMapper.getValues();
            ArrayList<String> types = packetMapper.getTypes();

            for (int i = 0; i < values.size(); i++) {
                be.fastrada.model.Packet packet = new be.fastrada.model.Packet(values.get(i), p.getTimestamp(), packetList.getRaceName(), types.get(i));
                packetService.addPacket(packet);
            }

        }

        return "Created " + packetList.getPackets().size() + " packets.";
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
}
