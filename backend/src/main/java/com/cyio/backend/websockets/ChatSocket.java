package com.cyio.backend.websockets;

import com.cyio.backend.model.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatSocket {

    private static final Logger log = LoggerFactory.getLogger(ChatSocket.class);
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
