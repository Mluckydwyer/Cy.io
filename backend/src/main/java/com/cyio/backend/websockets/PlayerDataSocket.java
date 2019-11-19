package com.cyio.backend.websockets;

import com.cyio.backend.model.Entity;
import com.cyio.backend.model.Player;
import com.cyio.backend.observerpatterns.*;
import com.cyio.backend.payload.PlayerData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@EnableScheduling
@Component
public class PlayerDataSocket implements PlayerListSubject, EntityListSubject {
    private final Logger logger = LoggerFactory.getLogger(PlayerDataSocket.class);

    @Autowired
    public SimpMessagingTemplate template;

    private HashMap players;
    private HashMap entities;

    private ArrayList<PlayerListObserver> playerListObservers;
    private ArrayList<EntityListObserver> entityListObservers;

    public final String endPoint = "/playerdata";
    public final String listenPoint = "/topic" + endPoint;

    public PlayerDataSocket() {
        players = new HashMap<String, Player>();
        entities = new HashMap<String, Entity>();
        playerListObservers = new ArrayList<>();
        entityListObservers = new ArrayList<>();
    }

    @MessageMapping(endPoint)
    public void recieveUpdate(@Payload PlayerData data) {
        manageMessages(data);
    }

    private void manageMessages(PlayerData msg) {
        try {
            JSONObject payload = new JSONObject(msg.getPayload());
            String playerId = payload.getString("playerId");
            Player p;

            switch (msg.getType()) {
                case "JOIN":
                    String username = payload.getString("username");
                    players.put(playerId, new Player(username, playerId));
                    notifyPlayerListObservers();
                    HashMap<String, String> values = new HashMap<>();
                    sendToAll(new PlayerData("JOIN", playerId, new JSONObject()));
                    break;
                case "LEAVE":
                    players.remove(playerId);
                    notifyPlayerListObservers();
                    break;
                case "PLAYER_MOVEMENT":
                    p = (Player) players.get(playerId);
                    p.updatePlayerData(payload.getJSONObject("data"));
                    break;
                case "ENTITIES":
                    p = (Player) players.get(playerId);
                    // TODO ???
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendToAll(Object message) {
        template.convertAndSend(listenPoint, message);
    }

    @Scheduled(fixedRate = 200)
    public void sendUpdate() {
        ArrayList<PlayerData> playerData = getAllPlayerData();
        sendToAll(playerData);
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

    @Override
    public void registerObserver(PlayerListObserver playerListObserver) {
        playerListObservers.add(playerListObserver);
    }

    @Override
    public void removeObserver(PlayerListObserver playerListObserver) {
        playerListObservers.remove(playerListObserver);
    }

    @Override
    public void registerObserver(EntityListObserver observer) {
        entityListObservers.add(observer);
    }

    @Override
    public void removeObserver(EntityListObserver observer) {
        entityListObservers.remove(observer);
    }

    @Override
    public void notifyEntityListObservers() {
        for (EntityListObserver entityListObserver : entityListObservers) {
            entityListObserver.update(entities);
        }
    }

    @Override
    public void notifyPlayerListObservers() {
        for (PlayerListObserver playerListObserver : playerListObservers) {
            playerListObserver.update(players);
        }
    }
}
