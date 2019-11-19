package com.cyio.backend.model;

import com.cyio.backend.payload.PlayerData;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.socket.sockjs.client.SockJsClient;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

public class Player {
    private String userName;
    private String userId;
    private int score;
    private SockJsClient socket;
    private PlayerData playerData;

    public void setUserId(String userId) {
        setUserId(userId);
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public void setPlayerData(PlayerData playerData) {
        setPlayerData(playerData);
    }


    public Player(String userName, String playerId){
        this(playerId);
        setUserName(userName);
        setScore(0);
    }

    public Player(String userName, int score){
        this(UUID.randomUUID().toString());
        setUserName(userName);
        setScore(score);
    }

    public Player(SockJsClient socket) {
        this(UUID.randomUUID().toString());
        this.socket = socket;
    }

    public Player(String id) {
        this.userId = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId(){
        return userId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public SockJsClient getSocket() {
        return socket;
    }

    public void updatePlayerData(HashMap<String, String> payloadMap) {
        playerData = new PlayerData("PLAYER_MOVEMENT", getUserId(), payloadMap);
    }

    public void updatePlayerData(JSONObject data) throws JSONException {
        HashMap<String, String> payloadMap = new HashMap<>();
        Iterator it = data.keys();
        while (it.hasNext()) {
            String key = (String) it.next();
            payloadMap.put(key, data.getString(key));
        }
        updatePlayerData(payloadMap);
    }

    static class PlayerComparater implements Comparator<Player> {
        public int compare(Player p1, Player p2){
            int score1 = p1.getScore();
            int score2 = p2.getScore();
            return score2 - score1;
        }
    }
}
