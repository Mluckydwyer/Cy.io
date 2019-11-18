package com.cyio.backend.websockets;

import com.cyio.backend.controller.ChatController;
import com.cyio.backend.model.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.websocket.server.ServerEndpoint;

@Controller
public class ChatSocket {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);
    public final String endPoint = "/chat";
    public final String listenPoint = "/topic" + endPoint;

    @MessageMapping(endPoint)
    @SendTo(listenPoint)
    public ChatMessage sendAll(@Payload ChatMessage msg) {
        //OutputChatMessage out = new OutputChatMessage(msg.getFrom(), msg.getText(), new SimpleDateFormat("HH:mm").format(new Date()));
        System.out.println(msg.getText());
        return msg;
    }

}
