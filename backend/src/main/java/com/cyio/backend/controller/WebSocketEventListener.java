package com.cyio.backend.controller;

import com.cyio.backend.model.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messageTemplate;

    @EventListener
    public void  handleWebSocketConnectListener(SessionConnectedEvent event){
        logger.info("New web socket connection");
    }

    @EventListener
    public void handleWebsocketDisconnectListener(SessionDisconnectEvent event){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String userId = (String) headerAccessor.getSessionAttributes().get("userId");

        if (userId != null){
            logger.info("User Disconnected: " + userId);

            Notification notification = new Notification();

            notification.setType(Notification.NotificationType.LEAVE);
            notification.setReferencedUser(userId);

            messageTemplate.convertAndSend("/topic/public", notification);
        }
    }
}
