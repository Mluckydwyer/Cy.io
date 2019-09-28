package com.cyio.backend;

import java.util.LinkedHashMap;
import java.util.UUID;

public class Game{
	
	private String title;
    private String blurb;
    private String about;
    private final UUID gameId;
    private final UUID creatorId;
    private UUID thumbnailId;

    public Game(String title, UUID gameId, UUID creatorId) {
        this.title = title;
        this.blurb = "This is a test game";
        this.about = "Wow! This is a long description";
        this.gameId = gameId;
        this.creatorId = UUID.randomUUID();
        this.thumbnailId = UUID.randomUUID();
    }

    public UUID getGameId() {
        return gameId;
    }

    /*
    public LinkedHashMap<String, Object> getDataMap() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("title", title);
        map.put("blurb", getBlurb());
        map.put("about", getAbout());
        map.put("gameId", getGameId());
        map.put("creatorId", getCreatorId());
        map.put("thumbnailId", getThumbnailId());
        return map;
    }
    */

    public String getTitle() {
        return title;
    }

    public String getBlurb() {
        return blurb;
    }

    public String getAbout() {
        return about;
    }

    public UUID getCreatorId() {
        return creatorId;
    }

    public UUID getThumbnailId() {
        return thumbnailId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public void setThumbnailId(UUID thumbnailId) {
        this.thumbnailId = thumbnailId;
    }
}
