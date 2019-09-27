package com.cyio.backend;

public class Game{
	
	private final long id;
	private final String title;

    public Game(long id, String content) {
        this.id = id;
        this.title = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return title;
    }
}
