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
        Game g1 = new Game("Cookie Clicker");
        Game g2 = new Game("Agar.io");
        ArrayList<Game> list = new ArrayList<Game>();
        list.add(g1);
        list.add(g2);
        when(g.parseGameList(null)).thenReturn(list);
        assertEquals(list.get(0), g1);
        assertEquals(list.get(1), g2);
    }
}
