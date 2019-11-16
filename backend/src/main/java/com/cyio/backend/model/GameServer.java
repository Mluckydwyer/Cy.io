package com.cyio.backend.model;

import com.cyio.backend.websockets.LeaderboardSocket;
import com.cyio.backend.websockets.NotificationSocket;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameServer {

    @Id
    @NotNull
    private String serverID;

    @Column(name = "gameId")
    private String gameId;

    private HashMap players;
    private Game game;

    // Game Leaderboard
    LeaderboardSocket ls;

    // Game notifications
    NotificationSocket ns;

    // Player Data
    PlayerDataSocket pds;

    public GameServer(Game game) {
        this.serverID = UUID.randomUUID().toString();
        this.game = game;
        this.gameId = this.game.getGameID();
        players = new HashMap<String, Player>();
        init();
    }

    public void sendPlayerUpdate(Player player) {
        ArrayList playerData = getAllPlayerGameData();
    }

    private ArrayList<String> getAllPlayerGameData() {
        ArrayList<String> data = new ArrayList<String>();

        for (Object key : players.keySet()) {
            data.add(((Player) players.get(key)).getGameData());
        }

        return data;
    }

    public Map<String, String> getJoinData() {
        Map<String, String> joinData = new HashMap();
        joinData.put("gameId", gameId);
        joinData.put("serverId", serverID);
        joinData.put("url", "");
        joinData.put("chatWS", "");
        joinData.put("notificationWS", "");
        joinData.put("leaderboardWS", "");
        joinData.put("playerDataWS", "");

        return joinData;
    }

    public void addPlayer(Player player) {
        players.put(player.getUserId(), player);
    }

    public void removePlayer(Player player) {
        players.remove(player.getUserId());
    }

    public String getServerID() {
        return serverID;
    }

    public void setServerID(String serverID) {
        this.serverID = serverID;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void init() {
    }
}
