package com.cyio.backend.controller;

import com.cyio.backend.model.Notification;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {

    @MessageMapping("notification.sendNotification")
    @SendTo("topic/public")
    public Notification sendNotification(@Payload Notification notification){
        return notification;
    }

    @MessageMapping("notification.newUser")
    @SendTo("topic/public")
    public Notification newGame(@Payload Notification notification, SimpMessageHeaderAccessor headerAccesser){
        //add user name to the socket notification
        headerAccesser.getSessionAttributes().put("userid", notification.getReferencedUser());
        return notification;
    }
}
