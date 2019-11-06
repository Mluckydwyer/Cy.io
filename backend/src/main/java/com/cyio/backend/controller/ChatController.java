package com.cyio.backend.controller;

import com.cyio.backend.model.ChatMessage;
import com.cyio.backend.model.OutputChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static com.cyio.backend.controller.Constants.*;


@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    @MessageMapping("/SendMessage")
    @SendTo("/room/public")
    public ChatMessage sendAll(@Payload ChatMessage msg) {
        //OutputChatMessage out = new OutputChatMessage(msg.getFrom(), msg.getText(), new SimpleDateFormat("HH:mm").format(new Date()));
        System.out.println(msg);
        return msg;
    }

//    @MessageMapping(SECURED_CHAT_ROOM)
//    @SendTo(SECURED_CHAT_HISTORY)
//    public void sendSpecific(@Payload ChatMessage msg, Principal user, @Header("simpSessionId") String sessionId) throws Exception {
//        OutputChatMessage out = new OutputChatMessage(msg.getFrom(), msg.getText(), new SimpleDateFormat("HH:mm").format(new Date()));
//        simpMessagingTemplate.convertAndSendToUser(msg.getTo(), SECURED_CHAT_SPECIFIC_USER, out);
//    }



//    @MessageMapping("/message")
//    @SendToUser("/chat")
//    public ChatMessage processMessageFromClient(ChatMessage message) {
//        return message;
//    }

//    private Chatroom room;
//
//    public ChatController() {
//        room = new Chatroom();
//    }
//
//    @MessageMapping("/chatroom")
//    @SendTo("/chat/user-123456789")
//    public ChatMessage Chatroom(ChatMessage message) {
//        ChatMessage room = new ChatMessage();
//        room.generateDummyData();
//        return room;
//    }
}
