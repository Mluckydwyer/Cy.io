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

    public Game()
    {
        title = null;
        blurb = null;
        about = null;
        gameID = null;
        creatorID = null;
    }
    public Game(String titleName, String blurbInfo, String aboutInfo, String gameIDInfo, String creatorIDInfo)
    {
        title = titleName;
        blurb = blurbInfo;
        about = aboutInfo;
        gameID = gameIDInfo;
        creatorID = creatorIDInfo;
    }

    public void setTitle(String titleName)
    {
        title = titleName;
    }

    public void setBlurb(String blurbInfo)
    {
        blurb = blurbInfo;
    }

    public void setAbout(String aboutInfo)
    {
        about = aboutInfo;
    }

    public void setGameID(String gameIDInfo)
    {
        gameID = gameIDInfo;
    }

    public void setCreatorID(String creatorIDInfo)
    {
        creatorID = creatorIDInfo;
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
