package com.cyio.backend.websockets;

import com.cyio.backend.model.LeaderBoard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PlayerDataSocket {
    private final Logger logger = LoggerFactory.getLogger(PlayerDataSocket.class);
    private List<Session> sessionList = new ArrayList<>();

    public PlayerDataSocket() {

    }

    @OnOpen
    public void onOpen(Session session) throws IOException {
        sessionList.add(session);
        logger.info("New Player Data connection");
        broadcast("New Player Data Connected!");
    }

    @OnClose
    public void onClose(Session session) throws IOException{
        sessionList.remove(session);
        logger.info("Socket connection closed");
    }

    private  void broadcast(String message)
            throws IOException
    {
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
}
