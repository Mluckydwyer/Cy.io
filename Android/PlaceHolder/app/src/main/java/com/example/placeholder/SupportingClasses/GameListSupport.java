package com.example.placeholder.SupportingClasses;

import com.example.placeholder.Models.Game;
import com.example.placeholder.SupportingClasses.LinkedGameList;

import java.util.ArrayList;

public class GameListSupport
{
    public LinkedGameList createLinkedList(ArrayList<Game> gameArrayList)
    {
        LinkedGameList lgl = new LinkedGameList();
        for (int i = 0; i < gameArrayList.size(); i++)
        {
            lgl.AddToList(gameArrayList.get(i));
        }
        return lgl;
    }



}
