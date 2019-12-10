package com.cyio.backend.model;

import com.cyio.backend.observerpatterns.EntityListObserver;
import com.cyio.backend.observerpatterns.PlayerListObserver;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class PlayerDataObjects {
    private HashMap<String, Player> players = new HashMap<String, Player>();
    private HashMap<String, Player> justJoined = new HashMap<String, Player>();
    private HashMap<String, Player> justLeft = new HashMap<String, Player>();
    private HashMap<String, Entity> entities = new HashMap<String, Entity>();

    public HashMap getJustJoined() {
        return justJoined;
    }

    public void setJustJoined(HashMap justJoined) {
        this.justJoined = justJoined;
    }

    public HashMap getJustLeft() {
        return justLeft;
    }

    public void setJustLeft(HashMap justLeft) {
        this.justLeft = justLeft;
    }

    public HashMap getPlayers() {
        return players;
    }

    public void setPlayers(HashMap players) {
        this.players = players;
    }

    public HashMap getEntities() {
        return entities;
    }

    public void setEntities(HashMap entities) {
        this.entities = entities;
    }

    public ArrayList<PlayerListObserver> getPlayerListObservers() {
        return playerListObservers;
    }

    public void setPlayerListObservers(ArrayList<PlayerListObserver> playerListObservers) {
        this.playerListObservers = playerListObservers;
    }

    public ArrayList<EntityListObserver> getEntityListObservers() {
        return entityListObservers;
    }

    public void setEntityListObservers(ArrayList<EntityListObserver> entityListObservers) {
        this.entityListObservers = entityListObservers;
    }

    private ArrayList<PlayerListObserver> playerListObservers = new ArrayList<>();
    private ArrayList<EntityListObserver> entityListObservers = new ArrayList<>();

}
