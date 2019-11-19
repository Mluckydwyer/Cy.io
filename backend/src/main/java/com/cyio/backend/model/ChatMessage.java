package com.cyio.backend.model;

public class ChatMessage {
    private String text;

    public ChatMessage() {

    }

    public ChatMessage(String text) {
        setText(text);
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
//    private String chatMessage;
//    private String chatRoomID;
//    private String sendingUser;
//
//    public ChatMessage() {
//        this("", "", "");
//    }
//
//    public ChatMessage(String chatMessage, String chatRoomID, String sendingUser) {
//        this.chatMessage = chatMessage;
//        this.chatRoomID = chatRoomID;
//        this.sendingUser = sendingUser;
//    }
//
//    public String getChatMessage() {
//        return chatMessage;
//    }
//
//    public void setChatMessage(String chatMessage) {
//        this.chatMessage = chatMessage;
//    }
//
//    public String getChatRoomID() {
//        return chatRoomID;
//    }
//
//    public void setChatRoomID(String chatRoomID) {
//        this.chatRoomID = chatRoomID;
//    }
//
//    public String getSendingUser() {
//        return sendingUser;
//    }
//
//    public void setSendingUser(String sendingUser) {
//        this.sendingUser = sendingUser;
//    }

}
