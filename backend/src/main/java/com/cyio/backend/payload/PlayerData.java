package com.cyio.backend.payload;

import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.boot.configurationprocessor.json.JSONObject;

public class PlayerData {

    private String type;
    private String playerId;
    private String payload;

    public PlayerData(String type, String playerId, JSONObject jsonObject) {
        this(type, playerId, jsonObject.toString());
    }

    public PlayerData(String type, String playerId, String payload) {
        setType(type);
        setPlayerId(playerId);
        setPayload(payload);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
