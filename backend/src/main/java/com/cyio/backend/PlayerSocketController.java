package com.cyio.backend;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class PlayerSocketController {

    @MessageMapping("/player") // When to recieve incoming messages
    @SendTo("/topic/players") // Where to publish the out going message
    public SocketMessage message(PlayerSocketMessage message) {
        return new SocketMessage("Server Response");
    }



}
