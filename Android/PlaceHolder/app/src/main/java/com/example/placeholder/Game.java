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
    public String title;
    public String blurb;
    public String about;
    public String gameID;
    public String creatorID;
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
