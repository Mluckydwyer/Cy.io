package com.cyio.backend.model;

import java.util.HashMap;
import java.util.UUID;

public class GameServer {

    private String serverID;

    private HashMap players;
    private Game game;


    public GameServer(Game game) {
        this.serverID = UUID.randomUUID().toString();
        this.game = game;
        players = new HashMap();
    }

    public void addPlayer(Player player) {
        players.put(player.getId(), player);
    }



    public void removePlayer(Player player) {
        players.remove(player.getId());
    }

    public String getServerID() {
        return serverID;
    }

    public void setServerID(String serverID) {
        this.serverID = serverID;
    }

    public void init() {
    }
}
