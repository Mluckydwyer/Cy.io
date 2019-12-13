package com.cyio.backend.model;

import com.cyio.backend.observerpatterns.EntityListObserver;
import com.cyio.backend.observerpatterns.LeaderboardObserver;
import com.cyio.backend.observerpatterns.PlayerListObserver;
import com.cyio.backend.websockets.ChatSocket;
import com.cyio.backend.websockets.LeaderboardSocket;
import com.cyio.backend.websockets.NotificationSocket;
import com.cyio.backend.websockets.PlayerDataSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

//@Entity

//@Table(name = "Servers")
//@Controller
@Component
public class GameServer implements EntityListObserver, PlayerListObserver, LeaderboardObserver {

    private String serverId;
    private String gameId;

    public HashMap<String, Player> players;
    public HashMap<String, Entity> entities;
    public LeaderBoard leaderBoard;
    private Game game;

    // Game Leaderboard
    @Autowired
    private LeaderboardSocket leaderboardSocket;

    // Game notifications
    @Autowired
    private NotificationSocket notificationSocket;

    // Game Chat
    @Autowired
    private ChatSocket chatSocket;

    // Player Data Socket
    @Autowired
    private PlayerDataSocket playerDataSocket;

    @Autowired
    public GameServer(Game game, LeaderboardSocket leaderboardSocket, NotificationSocket notificationSocket, PlayerDataSocket playerDataSocket, ChatSocket chatSocket) {
        this.serverId = UUID.randomUUID().toString();
        changeGame(game);

        this.leaderboardSocket = leaderboardSocket;
        this.notificationSocket = notificationSocket;
        this.playerDataSocket = playerDataSocket;
        this.chatSocket = chatSocket;

        this.players = new HashMap<>();
        this.entities = new HashMap<>();
        this.leaderBoard = new LeaderBoard();
    }

    @PostConstruct
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
//        pds.registerObserver((PlayerListObserver) this);
//        pds.registerObserver((EntityListObserver) this);
//        ls.registerObserver((LeaderboardObserver) this);
//        this.leaderboardSocket = new LeaderboardSocket();
//        this.notificationSocket = new NotificationSocket();
//        this.playerDataSocket = new PlayerDataSocket();
//        this.chatSocket = new ChatSocket();

        this.players = new HashMap<>();
        this.entities = new HashMap<>();
        this.leaderBoard = new LeaderBoard();
        this.playerDataSocket.registerObserver((PlayerListObserver) this);
        this.playerDataSocket.registerObserver((EntityListObserver) this);
        this.leaderboardSocket.registerObserver((LeaderboardObserver) this);
    }

    public Map<String, String> getJoinData() {
        Map<String, String> joinData = new HashMap<>();
        joinData.put("gameTitle", game.getTitle());
        joinData.put("gameId", gameId);
        joinData.put("serverId", serverId);
        joinData.put("url", "");
        joinData.put("chatWs", chatSocket.endPoint);
        joinData.put("chatSub", chatSocket.listenPoint);
        joinData.put("notificationWs", notificationSocket.endPoint);
        joinData.put("notificationSub", notificationSocket.listenPoint);
        joinData.put("leaderboardWs", leaderboardSocket.endPoint);
        joinData.put("leaderboardSub", leaderboardSocket.listenPoint);
        joinData.put("playerDataWs", playerDataSocket.getEndPoint());
        joinData.put("playerDataSub", playerDataSocket.getListenPoint());

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
        this.players = players; // update local player list

        for (String playerId : (Set<String>) playerDataSocket.getPlayerDataObjects().getJustJoined().keySet()) {
            notificationSocket.playerJoined(players.get(playerId));
            leaderboardSocket.leaderBoard.addPlayer(players.get(playerId));
        }
        playerDataSocket.getPlayerDataObjects().getJustJoined().clear();

        for (String playerId : (Set<String>) playerDataSocket.getPlayerDataObjects().getJustLeft().keySet()) {
            notificationSocket.playerLeft(players.get(playerId));
            leaderboardSocket.leaderBoard.removePlayer(players.get(playerId));
        }
        playerDataSocket.getPlayerDataObjects().getJustLeft().clear();
    }

    @Override
    public void updateLeaderBoard(LeaderBoard leaderBoard) {
//        if (this.leaderBoard.getLeader().getPlayerId() != leaderBoard.getLeader().getPlayerId()) {
//            ns.newLeader(players.get(leaderBoard.getLeader().getPlayerId()));
//        }
        this.leaderBoard = leaderBoard; // update local leader list
//        ls.sendLeaderboard(leaderBoard);
    }

    @Override
    public void updateEntityList(HashMap<String, Entity> entities) {
//        if (!this.entities.equals(entities)) {
//            pds.fillEntities();
//        }
        this.entities = entities;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public LeaderboardSocket getLeaderboardSocket() {
        return leaderboardSocket;
    }

    public void setLeaderboardSocket(LeaderboardSocket leaderboardSocket) {
        this.leaderboardSocket = leaderboardSocket;
    }

    public NotificationSocket getNotificationSocket() {
        return notificationSocket;
    }

    public void setNotificationSocket(NotificationSocket notificationSocket) {
        this.notificationSocket = notificationSocket;
    }

    public ChatSocket getChatSocket() {
        return chatSocket;
    }

    public void setChatSocket(ChatSocket chatSocket) {
        this.chatSocket = chatSocket;
    }

    public PlayerDataSocket getPlayerDataSocket() {
        return playerDataSocket;
    }

    public void setPlayerDataSocket(PlayerDataSocket playerDataSocket) {
        this.playerDataSocket = playerDataSocket;
    }

    public void changeGame(Game game) {
        this.game = game;
        this.gameId = this.game.getGameID();
    }
}
