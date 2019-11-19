package com.cyio.backend.websockets;

import com.cyio.backend.model.Player;
import com.cyio.backend.observerpatterns.PlayerListObserver;
import com.cyio.backend.observerpatterns.Subject;
import com.cyio.backend.payload.PlayerData;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Map;

@EnableScheduling
@Component
public class PlayerDataSocket implements Subject {
    private final Logger logger = LoggerFactory.getLogger(PlayerDataSocket.class);

    @Autowired
    public SimpMessagingTemplate template;

    private HashMap players;
    private ArrayList<PlayerListObserver> playerListObservers;
    private PlayerListObserver playerListObserver;

    public final String endPoint = "/playerdata";
    public final String listenPoint = "/topic" + endPoint;

    public PlayerDataSocket() {
        players = new HashMap<String, Player>();
        playerListObservers = new ArrayList<>();
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
                    notifyObservers();
                    break;
                case "LEAVE":
                    players.remove(playerId);
                    notifyObservers();
                    break;
                case "PLAYER_MOVEMENT":
                    p = (Player) players.get(playerId);
                    p.updatePlayerData(payload.getJSONObject("data"));
                    break;
                case "ENTITIES":
                    p = (Player) players.get(playerId);

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

    @Override
    public void registerObserver(PlayerListObserver playerListObserver) {
        playerListObservers.add(playerListObserver);
    }

    @Override
    public void removeObserver(PlayerListObserver playerListObserver) {
        playerListObservers.remove(playerListObserver);
    }

    @Override
    public void notifyObservers() {
        for (PlayerListObserver playerListObserver : playerListObservers) {
            playerListObserver.update(this, players);
        }
    }
}
