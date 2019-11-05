package com.cyio.backend.model;

public class OutputChatMessage extends ChatMessage {

    private String time;

    public OutputChatMessage(final String from, final String text, final String time) {
        setFrom(from);
        setText(text);
        this.time = time;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) { this.time = time; }
}
