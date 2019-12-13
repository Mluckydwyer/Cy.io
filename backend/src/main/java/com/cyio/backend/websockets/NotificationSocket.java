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

    public void sendTestNotification() {
        sendToAll("This is a test 123...");
    }

    public void sendToAll(Object message) {
        template.convertAndSend(listenPoint, message);
    }

    public void playerJoined(Player player) {
        sendToAll("<b>" + player.getUserName() + "</b> joined the game!");
    }

    public void playerLeft(Player player) {
        sendToAll("<b>" + player.getUserName() + "</b> disconnected");
    }

    public void newLeader(Player player) {
        sendToAll("<b>" + player.getUserName() + "</b> has taken the lead!");
    }

    public void newGameAdded(Game game) {
        sendToAll("<b>" + game.getTitle() + "</b> is now playable");
    }

}
