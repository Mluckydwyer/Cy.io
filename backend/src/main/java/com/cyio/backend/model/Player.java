package com.cyio.backend.model;

import org.springframework.web.socket.sockjs.client.SockJsClient;

import java.util.Comparator;
import java.util.UUID;

public class Player {
    private String userName;
    private String userId;
    private int score;
    private SockJsClient socket;


    public Player(String userName, int score){
        this.userName = userName;
        this.score = score;
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


    static class PlayerComparater implements Comparator<Player> {
        public int compare(Player p1, Player p2){
            int score1 = p1.getScore();
            int score2 = p2.getScore();
            return score2 - score1;
        }
    }
}
