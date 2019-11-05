package com.cyio.backend.model;

import java.util.ArrayList;

public class LeaderBoard {
    private ArrayList<Player> leaderList;

    public LeaderBoard(){
        leaderList = new ArrayList<>();
    }

    public ArrayList<Player> getLeaderList() {
        return leaderList;
    }

    public boolean sortBoard(){
        leaderList.sort(new Player.PlayerComparater());
        return true;
    }

    public void updatePlayerScore(String playerName, int newScore){
        Player p = searchByUsername(playerName);
        if(p != null){
            p.setScore(newScore);
        }
        else
            throw new IllegalArgumentException("No such player found");
        sortBoard();
    }

    private Player searchByUsername(String playerName){
        for(Player p : leaderList){
            if (p.getUserName().equalsIgnoreCase(playerName)) {
                return p;
            }
        }
        return null;
    }

    public void generateDummyData(){
        leaderList.add(new Player("Toby", 50));
        leaderList.add(new Player("Calvin", 50));
        leaderList.add(new Player("John", 50));
        leaderList.add(new Player("Brodi", 50));
        leaderList.add(new Player("Jose", 50));
        leaderList.add(new Player("Wang", 50));
        leaderList.add(new Player("Fernando", 50));
        leaderList.add(new Player("Coudio", 50));
        leaderList.add(new Player("Melissa", 50));
        leaderList.add(new Player("Lauren", 50));
        leaderList.add(new Player("Riesha", 50));
    }
}
