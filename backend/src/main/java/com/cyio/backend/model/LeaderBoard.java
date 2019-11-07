package com.cyio.backend.model;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class LeaderBoard {
    private ArrayList<Player> leaderList;

    public LeaderBoard(){
        leaderList = new ArrayList<>();
    }

    /**
     * @return ArrayList<Player> a list of sorted player on the leaderboard
     */
    public ArrayList<Player> getLeaderList() {
        return leaderList;
    }

    /**
     * adds a new player to the list
     * @param p the new player to be added
     */
    public void addPlayer(Player p){
        leaderList.add(p);
        sortBoard();
    }

    /**
     * Sorts the list by score - higher the score -> higher on the list
     * @return
     */
    public boolean sortBoard(){
        leaderList.sort(new Player.PlayerComparater());
        return true;
    }

    /**
     * updates the player's score and sort the leader board
     * @param playerName the username for the player to be updated
     * @param newScore new score
     */
    public void updatePlayerScore(String playerName, int newScore){
        Player p = searchByUsername(playerName);
        if(p != null){
            p.setScore(newScore);
        }
        else
            throw new IllegalArgumentException("No such player found");
        sortBoard();
    }

    /**
     * private helper method used to retrieve a player by searching its username
     * @param playerName
     * @return
     */
    private Player searchByUsername(String playerName){
        for(Player p : leaderList){
            if (p.getUserName().equalsIgnoreCase(playerName)) {
                return p;
            }
        }
        return null;
    }

    /**
     * for testing purposes, populate a list with 10 players with randomized scores from 0 - 1000
     */
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
        Random rnd = new Random();
        for (Player player:leaderList){
            player.setScore(rnd.nextInt(1000));
        }
        sortBoard();
    }

    /**
     * returns the top n players in JSON format
     * @return the top n leaderboard list in JSON format
     */
    public String getTop(int n){
        LeaderBoard topBoard = new LeaderBoard();
        for(int i = 0; i < n;i ++ ){
            Player p = leaderList.get(i);
            topBoard.addPlayer(new Player(p.userName,p.getScore()));
        }
        return null;
    }
    /**
     * convers the current list to an JSON string, for socket use
     * @return the complete leaderboard list in JSON format
     */
    @Override
    public String toString(){
        sortBoard();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonInString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
            return jsonInString;
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}

