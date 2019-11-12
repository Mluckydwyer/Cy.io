package com.cyio.backend.model;

import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import java.util.HashMap;
import java.util.UUID;

public class GameServer {

    @Id
    private String serverID;

    @Column(name = "gameId")
    private String gameId;

    private HashMap players;
    private Game game;


    public GameServer(Game game) {
        this.serverID = UUID.randomUUID().toString();
        this.game = game;
        this.gameId = this.game.getGameID();
        players = new HashMap();
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
