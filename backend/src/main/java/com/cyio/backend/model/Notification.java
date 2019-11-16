package com.cyio.backend.model;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Notification {
    public enum NotificationType{
        JOIN,
        LEAVE,
        NEWGAME,
        MESSAGE
    }

    private NotificationType type;
    private String notificationMessage;
    private String referencedGame;
    private String referencedUser;

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public String getReferencedGame() {
        return referencedGame;
    }

    public void setReferencedGame(String referencedGame) {
        this.referencedGame = referencedGame;
    }

    public String getReferencedUser() {
        return referencedUser;
    }

    public void setReferencedUser(String referencedUser) {
        this.referencedUser = referencedUser;
    }

    /**
     * convers the current list to an JSON string, for socket use
     * @return the complete leaderboard list in JSON format
     */
    @Override
    public String toString(){
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
