package com.cyio.backend.model;

public class Leader {
    private String name;
    private int score;
    private String playerId;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public Leader(String playerId, String name, int score) {
        setPlayerId(playerId);
        setName(name);
        setScore(score);
    }

    public Leader(Player player) {
        setPlayerId(player.getUserId());
        setName(player.getUserName());
        setScore(player.getScore());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
