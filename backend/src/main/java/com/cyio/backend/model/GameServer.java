package com.cyio.backend.model;

import com.cyio.backend.websockets.ChatSocket;
import com.cyio.backend.websockets.LeaderboardSocket;
import com.cyio.backend.websockets.NotificationSocket;
import com.cyio.backend.websockets.PlayerDataSocket;
import javax.persistence.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//@Entity

//@Table(name = "Servers")
public class GameServer {

//    @Id
    private String serverId;

//    @Column(name = "gameId")
    private String gameId;

    private HashMap players;
    private Game game;

    // Game Leaderboard
    LeaderboardSocket ls;

    // Game notifications
    NotificationSocket ns;

    // Game Chat
    ChatSocket cs;

    // Player Data
    PlayerDataSocket pds;

    public GameServer() {

    }

    public GameServer(Game game) {
        this.serverId = UUID.randomUUID().toString();
        this.game = game;
        this.gameId = this.game.getGameID();
        players = new HashMap<String, Player>();

        ls = new LeaderboardSocket();
        ns = new NotificationSocket();
        pds = new PlayerDataSocket();
        cs = new ChatSocket();
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
        joinData.put("gameTitle", game.getTitle());
        joinData.put("gameId", gameId);
        joinData.put("serverId", serverId);
        joinData.put("url", "");
        joinData.put("chatWs", cs.endPoint);
        joinData.put("chatSub", cs.listenPoint);
        joinData.put("notificationWs", ns.endPoint);
        joinData.put("notificationSub", ns.listenPoint);
        joinData.put("leaderboardWs", ls.endPoint);
        joinData.put("leaderboardSub", ls.listenPoint);
        joinData.put("playerDataWs", pds.endPoint);
        joinData.put("playerDataSub", pds.listenPoint);

        return joinData;
    }

    public void addPlayer(Player player) {
        players.put(player.getUserId(), player);
    }

    public void removePlayer(Player player) {
        players.remove(player.getUserId());
    }

    public String getServerID() {
        return serverId;
    }

    public void setServerID(String serverID) {
        this.serverId = serverID;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
