package com.example.placeholder;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class LeaderBoardTest
{
    @Mock
    LeaderBoardActivity l;

    @Before
    public void init()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void playerNameTest()
    {
        ArrayList<Player> arr = new ArrayList<>();
        arr.add(new Player("Wang", 420));
        l.players = new ArrayList<>();
        l.players.add(new Player("Wang", 420));
        assertEquals(l.players.get(0).getName(), "Wang");
        when(l.getPlayerName(0)).thenReturn("Wang");
        assertEquals(l.getPlayerName(0), arr.get(0).getName());
    }

    @Test
    public void playerScoreTest()
    {
        ArrayList<Player> arr = new ArrayList<>();
        arr.add(new Player("Wang", 420));
        l.players = new ArrayList<>();
        l.players.add(new Player("Wang", 420));
        assertEquals(l.players.get(0).getScore(),420);
        when(l.getPlayerScore(0)).thenReturn(420);
        assertEquals(l.getPlayerScore(0), arr.get(0).getScore());
    }

    @Test
    public void seePlayersTest()
    {
        l.players = new ArrayList<>();
        l.players.add(new Player("Ben", 300));
        String s = "Ben, 300";
        when(l.seePlayers()).thenReturn(s);
        assertEquals(s, l.players.get(0).getName() + ", " + l.players.get(0).getScoreString());

    }
}
