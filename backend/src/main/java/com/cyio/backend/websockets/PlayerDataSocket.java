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
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@EnableScheduling
@Controller
public class PlayerDataSocket implements PlayerListSubject, EntityListSubject {
    private final Logger LOGGER = LoggerFactory.getLogger(PlayerDataSocket.class);
    private final int UPDATE_INTERVAL = 15;

    @Autowired
    public SimpMessagingTemplate template;

    private HashMap players = new HashMap<String, Player>();
    private HashMap entities = new HashMap<String, Entity>();

    private ArrayList<PlayerListObserver> playerListObservers = new ArrayList<>();
    private ArrayList<EntityListObserver> entityListObservers = new ArrayList<>();

    public final String endPoint = "/playerdata";
    public final String listenPoint = "/topic" + endPoint;

    public PlayerDataSocket() { }

    @MessageMapping(endPoint)
    public void recieveUpdate(@Payload PlayerData data) {
        manageMessages(data);
    }

    private void manageMessages(PlayerData msg) {
        try {
            JSONObject payload = new JSONObject(msg.getPayload());
            String playerId = msg.getPlayerId();
            Player p;

            switch (msg.getType()) {
                case "JOIN":
                    String username = payload.getString("username");
                    players.put(playerId, new Player(username, playerId));
                    notifyPlayerListObservers();
                    HashMap<String, String> values = new HashMap<>();
                    sendToAll(new PlayerData("JOIN", playerId, new HashMap<String, String>()));
                    break;
                case "LEAVE":
                    players.remove(playerId);
                    notifyPlayerListObservers();
                    break;
                case "PLAYER_MOVEMENT":
                    p = (Player) players.get(playerId);
                    p.updatePlayerData(payload);
                    break;
                case "ENTITIES":
                    p = (Player) players.get(playerId);

                    fillEntities();
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

    @Scheduled(fixedRate = UPDATE_INTERVAL)
    public void sendPlayerUpdate() {
        ArrayList<PlayerData> playerData = getAllPlayerData();
        sendToAll(playerData);
    }

    @Scheduled(fixedRate = UPDATE_INTERVAL)
    public void sendEntityUpdate() {
        fillEntities();
        sendToAll(getAllEntities());
    }

    @Scheduled(fixedRate = 1000)
    public void cullDeadConnections() {
        for (Object key : players.keySet()) {
            LocalDateTime playerDataRecency = ((Player) players.get(key)).getPayloadRecency();
            if (playerDataRecency.isBefore(LocalDateTime.now().minusSeconds(3))) {
                players.remove(players.get(key));
            }
        }
    }

    public ArrayList<PlayerData> getAllPlayerData() {
        ArrayList<PlayerData> data = new ArrayList<PlayerData>();

        for (Object key : players.keySet()) {
            PlayerData pd = ((Player) players.get(key)).getPlayerData();
            if (pd != null) {
                data.add(pd);
            }
        }

        return data;
    }

    public void addPlayer(Player player) {
        players.put(player.getUserId(), player);
    }

    public void removePlayer(Player player) {
        players.remove(player.getUserId());
    }

    public ArrayList<Entity> getAllEntities() {
        ArrayList<Entity> data = new ArrayList<Entity>();

        for (Object key : entities.keySet()) {
            data.add((Entity) entities.get(key));
        }

        return data;
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
            entityListObserver.updateEntityList(entities);
        }
    }

    @Override
    public void notifyPlayerListObservers() {
        for (PlayerListObserver playerListObserver : playerListObservers) {
            playerListObserver.updatePlayerList(players);
        }
    }

    public void fillEntities() {
        int numToAdd = 100 - entities.size();
        for (int i = 0; i < numToAdd; i++) {
            Entity e = new Entity();
            entities.put(e.getId(), e);
        }
    }
}
