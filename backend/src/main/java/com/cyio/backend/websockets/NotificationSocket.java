package com.cyio.backend.websockets;


import com.cyio.backend.model.Game;
import com.cyio.backend.model.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.cyio.backend.model.Notification.NotificationType.*;

@ServerEndpoint("/notificationws/{username}")
@Component
public class NotificationSocket {
    private static Map<Session, String> sessionUsernameMap = new HashMap<>();
    private static Map<String, Session> usernameSessionMap = new HashMap<>();

    private final Logger logger = LoggerFactory.getLogger(NotificationSocket.class);

    /**
     *  When a new user joins the game, a new notification object is created and broadcasted to everyone with the referenced username added to it
     * @param session
     * @param username
     * @throws IOException
     */
    @OnOpen
    public void onOpen(
            Session session,
            @PathParam("username") String username) throws IOException
    {
        logger.info("Entered into Open");

        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);
        Notification n = new Notification();
        n.setType(JOIN);
        n.setNotificationMessage("A new user has joined the game");
        n.setReferencedUser(username);
        String message=n.toString();
        broadcast(message);
    }

    public void newGame(Game game) throws IOException {
        logger.info ("Entered into new Game");
        Notification n = new Notification();
        n.setType(NEWGAME);
        n.setNotificationMessage("New game added!");
        n.setReferencedUser(game.getCreatorID());
        n.setReferencedGame(game.getGameID());
        broadcast(n.toString());
    }

                        /**
     * When a user leaves the game, a new notification object is created and broadcasted with the referenced user attached to it
     *
     * @param session
     * @throws IOException
     */
                        @OnClose
    public void onClose(Session session) throws IOException
    {
        logger.info("Entered into Close");
        String username = sessionUsernameMap.get(session);
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);

        Notification n = new Notification();
        n.setReferencedUser(username);
        n.setNotificationMessage("A user has left the game");
        n.setType(LEAVE);
        String message= n.toString();
        broadcast(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable)
    {
        // Do error handling here
        logger.info("Entered into Error");
    }

    private static void broadcast(String message)
            throws IOException
    {
        sessionUsernameMap.forEach((session, username) -> {
            synchronized (session) {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
