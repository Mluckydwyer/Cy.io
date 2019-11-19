package com.cyio.backend.websockets;


import com.cyio.backend.model.Game;
import com.cyio.backend.model.LeaderBoard;
import com.cyio.backend.model.Player;
import com.cyio.backend.observerpatterns.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Observer;

@EnableScheduling
@Controller
public class NotificationSocket {
    private static final Logger log = LoggerFactory.getLogger(NotificationSocket.class);
    private LeaderBoard leaderBoard = new LeaderBoard();

    @Autowired
    public SimpMessagingTemplate template;

    public final String endPoint = "/notifications";
    public final String listenPoint = "/topic" + endPoint;

//    public NotificationSocket() {
//        template = new SimpMessagingTemplate(new M);
//    }

    @Scheduled(fixedRate = 10000)
    public void testNotifications() {
        sendToAll("This is a test 123...");
    }


    public void sendToAll(Object message) {
        template.convertAndSend(listenPoint, message);
    }

    public void playerJoined(Player player) {
        sendToAll(player.getUserName() + " joined the game!");
    }

    public void playerLeft(Player player) {
        sendToAll(player.getUserName() + " disconnected");
    }

    public void newLeader(Player player) {
        sendToAll(player.getUserName() + " has taken the lead!");
    }

    public void newGameAdded(Game game) {
        sendToAll(game.getTitle() + " is now playable");
    }


}

//
//@ServerEndpoint("/notification")
//@Component
//public class NotificationSocket {
//    private static Map<Session, String> sessionUsernameMap = new HashMap<>();
//    private static Map<String, Session> usernameSessionMap = new HashMap<>();
//
//    private final Logger logger = LoggerFactory.getLogger(NotificationSocket.class);
//
//    public final String endPoint = "/notification";
//    public final String listenPoint = "/topic" + endPoint;
//
//    /**
//     *  When a new user joins the game, a new notification object is created and broadcasted to everyone with the referenced username added to it
//     * @param session
//     * @param username
//     * @throws IOException
//     */
//    @OnOpen
//    public void onOpen(
//            Session session,
//            @PathParam("username") String username) throws IOException
//    {
//        logger.info("Entered into Open");
//
//        sessionUsernameMap.put(session, username);
//        usernameSessionMap.put(username, session);
//        Notification n = new Notification();
//        n.setType(JOIN);
//        n.setNotificationMessage("A new user has joined the game");
//        n.setReferencedUser(username);
//        String message=n.toString();
//        broadcast(message);
//    }
//
//    public void newGame(Game game) throws IOException {
//        logger.info ("Entered into new Game");
//        Notification n = new Notification();
//        n.setType(NEWGAME);
//        n.setNotificationMessage("New game added!");
//        n.setReferencedUser(game.getCreatorID());
//        n.setReferencedGame(game.getGameID());
//        broadcast(n.toString());
//    }
//
//                        /**
//     * When a user leaves the game, a new notification object is created and broadcasted with the referenced user attached to it
//     *
//     * @param session
//     * @throws IOException
//     */
//                        @OnClose
//    public void onClose(Session session) throws IOException
//    {
//        logger.info("Entered into Close");
//        String username = sessionUsernameMap.get(session);
//        sessionUsernameMap.remove(session);
//        usernameSessionMap.remove(username);
//
//        Notification n = new Notification();
//        n.setReferencedUser(username);
//        n.setNotificationMessage("A user has left the game");
//        n.setType(LEAVE);
//        String message= n.toString();
//        broadcast(message);
//    }
//
//    @OnError
//    public void onError(Session session, Throwable throwable)
//    {
//        // Do error handling here
//        logger.info("Entered into Error");
//    }
//
//    private static void broadcast(String message)
//            throws IOException
//    {
//        sessionUsernameMap.forEach((session, username) -> {
//            synchronized (session) {
//                try {
//                    session.getBasicRemote().sendText(message);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//}
//
