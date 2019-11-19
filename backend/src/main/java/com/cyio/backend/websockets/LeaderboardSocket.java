package com.cyio.backend.websockets;

import com.cyio.backend.model.LeaderBoard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@EnableScheduling
@Controller
public class LeaderboardSocket {

    private static final Logger log = LoggerFactory.getLogger(LeaderboardSocket.class);
    private LeaderBoard leaderBoard = new LeaderBoard();

    @Autowired
    public SimpMessagingTemplate template;

    public final String endPoint = "/leaderboard";
    public final String listenPoint = "/topic" + endPoint;

    public void sendToAll(Object message) {
        template.convertAndSend(listenPoint, message);
    }

    @Scheduled(fixedRate = 5000)
    public void sendUpdate() {
        leaderBoard.generateDummyData();
        sendToAll(leaderBoard.getLeaderList(5));
    }

}

/*
@ServerEndpoint("/leaderboard")
@Component
public class LeaderboardSocket {
    private final Logger logger = LoggerFactory.getLogger(LeaderboardSocket.class);
    private List<Session> sessionList = new ArrayList<>();

    @OnOpen
    public void onOpen(Session session) throws IOException {
        sessionList.add(session);
        logger.info("New gamews connection");
        LeaderBoard leaderBoard = new LeaderBoard();
        leaderBoard.generateDummyData();
        broadcast("Leaderboard Socket Connected!");
        broadcast(leaderBoard.toString());
    }

    @OnClose
    public void onClose(Session session) throws IOException{
        sessionList.remove(session);
        logger.info("Socket connection closed");
    }

    private  void broadcast(String message) throws IOException {
        for (Session session : sessionList)
        {
            synchronized (session) {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Scheduled(fixedRate = 5000)
    public void sendUpdate() {
        // TODO
    }

}
 */
