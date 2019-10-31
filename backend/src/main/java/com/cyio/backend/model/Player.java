package com.cyio.backend.model;

import java.util.Comparator;

public class Player {
    String userName;
    int score;

    public Player(String userName, int score){
        this.userName = userName;
        this.score = score;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    static class PlayerComparater implements Comparator<Player> {
        public int compare(Player p1, Player p2){
            int score1 = p1.getScore();
            int score2 = p2.getScore();
            return score1 - score2;
        }
    }
}
