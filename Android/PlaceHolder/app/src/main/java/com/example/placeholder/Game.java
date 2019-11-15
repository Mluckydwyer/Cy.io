package com.example.placeholder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Game
{
    private String title;
    private  String blurb;
    private String about;
    private String gameID;
    private String creatorID;
    //add thumbnail

    public Game()
    {

    }
    public Game(String titleName, String blurbInfo, String aboutInfo, String gameIDInfo, String creatorIDInfo)
    {
        title = titleName;
        blurb = blurbInfo;
        about = aboutInfo;
        gameID = gameIDInfo;
        creatorID = creatorIDInfo;
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

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }

    public String getTitle()
    {
        return title;
    }

    public String getBlurb()
    {
        return blurb;
    }

    public String getAbout()
    {
        return about;
    }

    public String getGameID()
    {
        return gameID;
    }

    public String getCreatorID()
    {
        return creatorID;
    }
}
