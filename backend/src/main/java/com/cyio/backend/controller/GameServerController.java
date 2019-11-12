package com.cyio.backend.controller;

import com.cyio.backend.model.Game;
import com.cyio.backend.model.GameServer;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GameServerController {
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
