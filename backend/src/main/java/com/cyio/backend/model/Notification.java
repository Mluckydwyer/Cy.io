package com.cyio.backend.model;

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
}
