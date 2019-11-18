package com.cyio.backend.websockets;

import com.cyio.backend.model.GameServer;
import com.cyio.backend.model.Player;
import com.cyio.backend.model.PlayerData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableScheduling
@Component
public class PlayerDataSocket {
    private final Logger logger = LoggerFactory.getLogger(PlayerDataSocket.class);

    @Autowired
    public SimpMessagingTemplate template;

    private HashMap players;
    public final String endPoint = "/playerdata";
    public final String listenPoint = "/topic" + endPoint;

    public PlayerDataSocket() {
        players = new HashMap<String, Player>();
    }

    @MessageMapping(endPoint)
    public void recieveUpdate(@Payload PlayerData data) {
        // TODO
    }

    public void sendToAll(Object message) {
        template.convertAndSend(listenPoint, message);
    }

    @Scheduled(fixedRate = 200)
    public void sendUpdate() {
        ArrayList<PlayerData> playerData = getAllPlayerData();
        sendToAll(playerData);
    }

    public void updatePlayerData(String playerId, Map<String, String> data) {
        ((Player) players.get(playerId)).updatePlayerData(data);
    }

    public ArrayList<PlayerData> getAllPlayerData() {
        ArrayList<PlayerData> data = new ArrayList<PlayerData>();

        for (Object key : players.keySet()) {
            data.add(((Player) players.get(key)).getPlayerData());
        }

        return data;
    }

    public void addPlayer(Player player) {
        players.put(player.getUserId(), player);
    }

    public void removePlayer(Player player) {
        players.remove(player.getUserId());
    }
}
