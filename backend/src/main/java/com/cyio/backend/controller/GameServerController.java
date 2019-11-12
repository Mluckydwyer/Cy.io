package com.cyio.backend.controller;

import com.cyio.backend.exception.ResourceNotFoundException;
import com.cyio.backend.model.Game;
import com.cyio.backend.model.GameServer;
import com.cyio.backend.repository.GameRepository;
import com.cyio.backend.repository.ServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Controller
@Component
public class GameServerController {

    @Autowired
    ServerRepository serverRepository; //using autowire to create an instance of blog Repository

    public GameServerController() {
        generateDummyServer();
    }

    private void generateDummyServer() {
        GameServer dummyGame = new GameServer(firstGame);



        serverRepository.save(dummyGame);
    }

    @RequestMapping("/join")
    public GameServer join(@RequestParam(value="server-id", defaultValue="") String serverId) {
        return serverRepository.findGameServerByServerId(serverId);//.orElseThrow(() -> new ResourceNotFoundException("GameServer", "id", serverId));
    }





    @MessageMapping("/game")
    @SendTo("/game/receive")
    public GameServer GameServer() throws Exception{
//        Thread.sleep(1000);
        Game testGame = new Game("Baisc Game", "123", "456");
        GameServer gs = new GameServer(testGame);
        gs.init();
        return gs;
    }
}
