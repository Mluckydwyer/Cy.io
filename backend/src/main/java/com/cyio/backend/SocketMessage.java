package com.cyio.backend;

public class SocketMessage {

    private String content;

    public SocketMessage(String content) {
        setContent(content);
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
