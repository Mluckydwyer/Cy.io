package com.cyio.backend.websockets;

import com.cyio.backend.model.Player;
import com.cyio.backend.model.PlayerData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@EnableScheduling
@Component
public class PlayerDataSocket {
    private final Logger logger = LoggerFactory.getLogger(PlayerDataSocket.class);

    public final String endPoint = "/playerdata";
    public final String listenPoint = "/topic" + endPoint;

    @MessageMapping(endPoint)
    public void recieveUpdate(@Payload PlayerData data) {
        // TODO
    }

    @SendTo(listenPoint)
    public List<Player> sendAll(List<Player> leaders) {
        System.out.println(leaders);
        return leaders;
    }

    @Scheduled(fixedRate = 5000)
    public void sendUpdate() {
        // TODO
        //sendAll(leaderBoard.getLeaderList());
    }

}
