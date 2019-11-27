package com.cyio.backend.payload;

import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.util.Map;

public class PlayerData {

    private String type;
    private String playerId;
    private Map<String, String> payload;

    public PlayerData() { }

    public PlayerData(String type, String playerId, Map<String, String> payload) {
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

    public Map<String, String> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, String> payload) {
        this.payload = payload;
    }
}
