package com.cyio.backend.model;

import com.cyio.backend.observerpatterns.LeaderboardObserver;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;

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
     * removes a player from the list
     * @param p the old player to be removed
     */
    public void removePlayer(Player p){
        leaderList.remove(p);
        sortBoard();
    }

    /**
     * Sorts the list by score - higher the score -> higher on the list
     * @return
     */
    public boolean sortBoard() {

        try {
            leaderList.sort(new Player.PlayerComparater());
            return true;
        } catch (Exception e) {
           // e.printStackTrace();
        }
        return false;
    }

    /**
     * updates the player's score and sort the leader board
     * @param playerId the id for the player to be updated
     * @param newScore new score
     */
    public void updatePlayerScore(String playerId, int newScore){
        Player p = searchById(playerId);
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
     * private helper method used to retrieve a player by searching its id
     * @param playerId
     * @return
     */
    private Player searchById(String playerId){
        for(Player p : leaderList){
            if (p.getUserId().equals(playerId)) {
                return p;
            }
        }
        return null;
    }

    /**
     * for testing purposes, populate a list with 10 players with randomized scores from 0 - 1000
     */
    public void generateDummyData(){
        leaderList.clear();
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
        for (Player player:leaderList){
            player.setScore((int) Math.round(Math.random() * 1000));
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
            topBoard.addPlayer(new Player(p.getUserName(),p.getScore()));
        }
        return null;
    }

    /**
     * Creates a map of player scores and usernames to be sent to clients
     * @param limit how many players to return
     * @return the complete leaderboard list in a Map
     */
    public Map<String, Integer> getMap(int limit) {
        sortBoard();
        TreeMap<String, Integer> leaderData = new TreeMap<String, Integer>();
        for (int i = 0; i < limit; i++) {
            leaderData.put(getLeaderList().get(i).getUserName(), getLeaderList().get(i).getScore());
        }
        return leaderData;
    }

    /**
     * Returns the player with the top score in the game
     * @return The player in the lead
     */
    public Leader getLeader() {
        if (getLeaderList().size() < 1) return null;
        sortBoard();
        return new Leader(getLeaderList().get(0));
    }

    /**
     * Creates a list of player scores and names to be sent to clients
     * @param limit how many players to return
     * @return the complete leaderboard list in a list
     */
    public List<Leader> getLeaderList(int limit) {
        ArrayList<Leader> leaders = new ArrayList<>();
        sortBoard();
        for (int i = 0; i < limit && i < getLeaderList().size(); i++) {
            Player player = getLeaderList().get(i);
            Leader leader = new Leader(player);
            leaders.add(leader);
        }

        return leaders;
    }

    /**
     * converts the current list to an JSON string, for socket use
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

