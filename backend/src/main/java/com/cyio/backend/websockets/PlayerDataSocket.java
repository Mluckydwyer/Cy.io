package com.cyio.backend.websockets;

// How to better handle websockets: https://stackoverflow.com/questions/48319866/websocket-server-based-on-spring-boot-becomes-unresponsive-after-a-malformed-pac

import com.cyio.backend.model.Entity;
import com.cyio.backend.model.Player;
import com.cyio.backend.model.PlayerDataObjects;
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
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

@EnableScheduling
@Controller
public class PlayerDataSocket implements PlayerListSubject, EntityListSubject {
    private final Logger LOGGER = LoggerFactory.getLogger(PlayerDataSocket.class);
    private final int UPDATE_INTERVAL = 12; // milliseconds

    private final int NUM_ENTITES = 300;

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private PlayerDataObjects playerDataObjects;

    private final String endPoint = "/playerdata";
    private final String listenPoint = "/topic" + endPoint;

    public PlayerDataSocket() {

    }

    @MessageMapping(endPoint)
    public void recieveUpdate(@Payload PlayerData data) {
        manageMessages(data);
    }

    private void manageMessages(PlayerData msg) {
        try {
            JSONObject payload = new JSONObject(msg.getPayload());
            String playerId = msg.getPlayerId();
            Player player;
            Entity entity;

            switch (msg.getType()) {
                case "JOIN":
                    System.out.println(payload);
                    String username = payload.getString("username");
                    addPlayer(new Player(username, playerId));
                    sendToAll(new PlayerData("JOIN", playerId, new HashMap<String, String>()));
                    break;
                case "LEAVE":
                    removePlayer((Player) playerDataObjects.getPlayers().get(playerId));
                    break;
                case "PLAYER_MOVEMENT":
                    player = (Player) playerDataObjects.getPlayers().get(playerId);
                    if (payload == null || player == null) return;
                    player.updatePlayerData(payload);
                    break;
                case "ENTITIES":
                    player = (Player) playerDataObjects.getPlayers().get(playerId);
                    entity = (Entity) getPlayerDataObjects().getEntities().get(payload.getString("id"));
                    getPlayerDataObjects().getEntities().remove(entity.getId());
                    player.incrementScore(entity.getScoreValue());
                    getPlayerDataObjects().getScoreChange().put(playerId, player);
                    notifyPlayerListObservers();
                    fillEntities();
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
        for (Object key : playerDataObjects.getPlayers().keySet()) {
            Player player = (Player) playerDataObjects.getPlayers().get(key);
            LocalDateTime playerDataRecency = player.getPayloadRecency();
            if (playerDataRecency.isBefore(LocalDateTime.now().minusSeconds(30))) {
                System.out.println("Culling player: " + player.getUserName() + "\tId: " + player.getUserId());
                removePlayer(player);
            }
        }
    }

    @Scheduled(fixedRate = 4000)
    public void refreshEntities() {
        for (Object key : playerDataObjects.getEntities().keySet()) {
            if (Math.random() > 0.5) {
                playerDataObjects.getEntities().remove(key);
            }
        }
        fillEntities();
    }

    public ArrayList<PlayerData> getAllPlayerData() {
        ArrayList<PlayerData> data = new ArrayList<PlayerData>();

        for (Object key : playerDataObjects.getPlayers().keySet()) {
            PlayerData pd = ((Player) playerDataObjects.getPlayers().get(key)).getPlayerData();
            if (pd != null) {
                data.add(pd);
            }
        }

        return data;
    }

    public void addPlayer(Player player) {
        playerDataObjects.getPlayers().put(player.getUserId(), player);
        playerDataObjects.getJustJoined().put(player.getUserId(), player);
        notifyPlayerListObservers();
    }

    public void removePlayer(Player player) {
        System.out.println("Removing player: " + player.getUserName() + "\tId: " + player.getUserId());
        playerDataObjects.getPlayers().remove(player.getUserId());
        playerDataObjects.getJustLeft().put(player.getUserId(), player);
        notifyPlayerListObservers();
    }

    public ArrayList<Entity> getAllEntities() {
        ArrayList<Entity> data = new ArrayList<Entity>();

        for (Object key : playerDataObjects.getEntities().keySet()) {
            data.add((Entity) playerDataObjects.getEntities().get(key));
        }

        return data;
    }

    @Override
    public void registerObserver(PlayerListObserver playerListObserver) {
        playerDataObjects.getPlayerListObservers().add(playerListObserver);
    }

    @Override
    public void removeObserver(PlayerListObserver playerListObserver) {
        playerDataObjects.getPlayerListObservers().remove(playerListObserver);
    }

    @Override
    public void registerObserver(EntityListObserver observer) {
        playerDataObjects.getEntityListObservers().add(observer);
    }

    @Override
    public void removeObserver(EntityListObserver observer) {
        playerDataObjects.getEntityListObservers().remove(observer);
    }

    @Override
    public void notifyEntityListObservers() {
        for (EntityListObserver entityListObserver : playerDataObjects.getEntityListObservers()) {
            entityListObserver.updateEntityList(playerDataObjects.getEntities());
        }
    }

    @Override
    public void notifyPlayerListObservers() {
        for (PlayerListObserver playerListObserver : playerDataObjects.getPlayerListObservers()) {
            playerListObserver.updatePlayerList(playerDataObjects.getPlayers());
        }
    }

    public void fillEntities() {
        int numToAdd = NUM_ENTITES - playerDataObjects.getEntities().size();
        for (int i = 0; i < numToAdd; i++) {
            Entity e = new Entity();
            playerDataObjects.getEntities().put(e.getId(), e);
        }
    }

    public PlayerDataObjects getPlayerDataObjects() {
        return playerDataObjects;
    }

    public void setPlayerDataObjects(PlayerDataObjects playerDataObjects) {
        this.playerDataObjects = playerDataObjects;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public String getListenPoint() {
        return listenPoint;
    }

    public int getNUM_ENTITES() {
        return NUM_ENTITES;
    }
}
