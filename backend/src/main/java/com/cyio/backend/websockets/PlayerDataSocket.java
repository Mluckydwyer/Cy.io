package com.cyio.backend.websockets;

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
    private final int UPDATE_INTERVAL = 5; // milliseconds

    @Autowired
    public SimpMessagingTemplate template;

    @Autowired
    public PlayerDataObjects pdos;

    public final String endPoint = "/playerdata";
    public final String listenPoint = "/topic" + endPoint;

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
            Player p;

            switch (msg.getType()) {
                case "JOIN":
                    System.out.println(payload);
                    String username = payload.getString("username");
                    addPlayer(playerId, new Player(username, playerId));
                    HashMap<String, String> values = new HashMap<>();
                    sendToAll(new PlayerData("JOIN", playerId, new HashMap<String, String>()));
                    break;
                case "LEAVE":
                    removePlayer((Player) pdos.getPlayers().get(playerId));
                    break;
                case "PLAYER_MOVEMENT":
                    if (payload == null) return;
                    p = (Player) pdos.getPlayers().get(playerId);
                    p.updatePlayerData(payload);
                    break;
                case "ENTITIES":
                    p = (Player) pdos.getPlayers().get(playerId);

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
        // System.out.println("Culling pdos.getPlayers()");
        for (Object key : pdos.getPlayers().keySet()) {
            LocalDateTime playerDataRecency = ((Player) pdos.getPlayers().get(key)).getPayloadRecency();
            if (playerDataRecency.isBefore(LocalDateTime.now().minusSeconds(3))) {
                pdos.getPlayers().remove(key);
            }
        }
    }

    public ArrayList<PlayerData> getAllPlayerData() {
        ArrayList<PlayerData> data = new ArrayList<PlayerData>();

        for (Object key : pdos.getPlayers().keySet()) {
            PlayerData pd = ((Player) pdos.getPlayers().get(key)).getPlayerData();
            if (pd != null) {
                data.add(pd);
            }
        }

        return data;
    }

    public void addPlayer(Player player) {
        pdos.getPlayers().put(player.getUserId(), player);
        pdos.getJustJoined().put(player.getUserId(), player);
        notifyPlayerListObservers();
    }

    public void addPlayer(String playerId, Player player) {
        pdos.getPlayers().put(playerId, player);
        pdos.getJustLeft().put(playerId, player);
        notifyPlayerListObservers();
    }

    public void removePlayer(Player player) {
        pdos.getPlayers().remove(player.getUserId());
        notifyPlayerListObservers();
    }

    public ArrayList<Entity> getAllEntities() {
        ArrayList<Entity> data = new ArrayList<Entity>();

        for (Object key : pdos.getEntities().keySet()) {
            data.add((Entity) pdos.getEntities().get(key));
        }

        return data;
    }

    @Override
    public void registerObserver(PlayerListObserver playerListObserver) {
        pdos.getPlayerListObservers().add(playerListObserver);
    }

    @Override
    public void removeObserver(PlayerListObserver playerListObserver) {
        pdos.getPlayerListObservers().remove(playerListObserver);
    }

    @Override
    public void registerObserver(EntityListObserver observer) {
        pdos.getEntityListObservers().add(observer);
    }

    @Override
    public void removeObserver(EntityListObserver observer) {
        pdos.getEntityListObservers().remove(observer);
    }

    @Override
    public void notifyEntityListObservers() {
        for (EntityListObserver entityListObserver : pdos.getEntityListObservers()) {
            entityListObserver.updateEntityList(pdos.getEntities());
        }
    }

    @Override
    public void notifyPlayerListObservers() {
        for (PlayerListObserver playerListObserver : pdos.getPlayerListObservers()) {
            playerListObserver.updatePlayerList(pdos.getPlayers());
        }
    }

    public void fillEntities() {
        int numToAdd = 100 - pdos.getEntities().size();
        for (int i = 0; i < numToAdd; i++) {
            Entity e = new Entity();
            pdos.getEntities().put(e.getId(), e);
        }
    }
}
