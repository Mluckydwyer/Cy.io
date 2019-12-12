package com.example.placeholder;

import com.example.placeholder.Activities.GameListActivity;
import com.example.placeholder.Models.Game;
import com.example.placeholder.SupportingClasses.GameListSupport;
import com.example.placeholder.SupportingClasses.LinkedGameList;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class GameListTest
{
    @Mock
    GameListActivity g;

    @Mock
    GameListSupport gameListSupport;

    @Before
    public void init()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void Null_CreateLinkedList()
    {
        LinkedGameList lgl = new LinkedGameList();
        when(gameListSupport.createLinkedList(null)).thenReturn(lgl);
        assertEquals(lgl.getGame(lgl.head), null);
    }

    @Test
    public void OneGame_CreateLinkedList()
    {
        LinkedGameList lgl = new LinkedGameList();
        Game g1 = new Game("Cookie Clicker", "S", "t", "u", "v");
        lgl.AddToList(g1);
        ArrayList<Game> list = new ArrayList<>();
        list.add(g1);
        when(gameListSupport.createLinkedList(list)).thenReturn(lgl);
        assertEquals(lgl.getGame(lgl.head), g1);
    }

    @Test
    public void ManyGame_CreateLinkedList()
    {
        LinkedGameList lgl = new LinkedGameList();
        Game g1 = new Game("Cookie Clicker", "S", "t", "u", "v");
        Game g2 = new Game("agar.io", "S", "t", "u", "v");
        lgl.AddToList(g1);
        lgl.AddToList(g2);
        ArrayList<Game> list = new ArrayList<>();
        list.add(g1);
        list.add(g2);
        when(gameListSupport.createLinkedList(list)).thenReturn(lgl);
        assertEquals(lgl.getGame(lgl.head), g1);
        assertEquals(lgl.getGame(lgl.head.next), g1);
        assertEquals(lgl.getGame(lgl.head.next.next), g2);
        assertEquals(lgl.getGame(lgl.head.next.next.next), null);
    }


    @Test
    public  void getList_test()
    {
        LinkedGameList lgl = new LinkedGameList();
        g.linkedGameList = lgl;
        when(gameListSupport.getList()). thenReturn(lgl);
        assertEquals(g.linkedGameList, lgl);
    }
}
