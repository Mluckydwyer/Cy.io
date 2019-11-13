package com.example.placeholder;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class GameListTest
{
    @Mock
    GameList g;

    @Before
    public void init()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void Null_parseGameList()
    {
        Game g1 = new Game("Cookie Clicker", "S", "t", "u", "v");
        Game g2 = new Game("Agar.io", "S", "T", "r", "m");
        ArrayList<Game> list = new ArrayList<Game>();
        list.add(g1);
        list.add(g2);
        when(g.parseGameList()).thenReturn(list);
        assertEquals(list.get(0), g1);
        assertEquals(list.get(1), g2);
    }

    @Test
    public void Null_CreateLinkedList()
    {
        LinkedGameList lgl = new LinkedGameList();
        when(g.createLinkedList(null)).thenReturn(lgl);
        assertEquals(lgl.getGame(), null);
    }

    @Test
    public void OneGame_CreateLinkedList()
    {
        LinkedGameList lgl = new LinkedGameList();
        Game g1 = new Game("Cookie Clicker", "S", "t", "u", "v");
        lgl.AddToList(g1);
        ArrayList<Game> list = new ArrayList<>();
        list.add(g1);
        when(g.createLinkedList(list)).thenReturn(lgl);
        assertEquals(lgl.getGame(), g1);
    }

    @Test
    public  void NULL_updateLinkedGameList()
    {
        
    }
}
