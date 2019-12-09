package com.cyio.backend.model;

import com.cyio.backend.observerpatterns.EntityListObserver;
import com.cyio.backend.observerpatterns.LeaderboardObserver;
import com.cyio.backend.observerpatterns.PlayerListObserver;
import com.cyio.backend.websockets.ChatSocket;
import com.cyio.backend.websockets.LeaderboardSocket;
import com.cyio.backend.websockets.NotificationSocket;
import com.cyio.backend.websockets.PlayerDataSocket;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//@Entity

//@Table(name = "Servers")
public class GameServer implements EntityListObserver, PlayerListObserver, LeaderboardObserver {

//    @Id
    private String serverId;

//    @Column(name = "gameId")
    private String gameId;

    private Game game;

    HashMap<String, Player> players;
    HashMap<String, Entity> entities;
    LeaderBoard leaderBoard;

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

        ls = new LeaderboardSocket();
        ns = new NotificationSocket();
        pds = new PlayerDataSocket();
        cs = new ChatSocket();

        pds.fillEntities();
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

    @Override
    public void updatePlayerList(HashMap<String, Player> players) {
        // Find new players that might have joined
        for (String playerId : players.keySet()) {
            if (!this.players.containsKey(playerId)) {
                ns.playerJoined(players.get(playerId));
                ls.leaderBoard.addPlayer(players.get(playerId));
            }
        }

        // Find old players that might have left
        for (String playerId : this.players.keySet()) {
            if (!players.containsKey(playerId)) {
                ns.playerLeft(this.players.get(playerId));
                ls.leaderBoard.removePlayer(this.players.get(playerId));
            }
        }

        this.players = players; // update local player list
    }

    @Override
    public void updateLeaderBoard(LeaderBoard leaderBoard) {
        if (this.leaderBoard.getLeader().getPlayerId() != leaderBoard.getLeader().getPlayerId()) {
            ns.newLeader(players.get(leaderBoard.getLeader().getPlayerId()));
        }
        this.leaderBoard = leaderBoard; // update local leader list
        ls.sendLeaderboard(leaderBoard);
    }

    @Override
    public void updateEntityList(HashMap<String, Entity> entities) {
        if (!this.entities.equals(entities)) {
            pds.fillEntities();
        }
        this.entities = entities;
    }
}
