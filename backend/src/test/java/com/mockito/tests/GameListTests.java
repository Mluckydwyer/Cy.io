package com.mockito.tests;

import com.cyio.backend.controller.GameController;
import com.cyio.backend.model.Game;
import com.cyio.backend.repository.GameRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class GameListTests {
    @InjectMocks
    GameController controller;

    @Mock
    GameRepository gameRepo;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getGamebyTitleTest(){
        List<Game> answer = new ArrayList<Game>();
        answer.add(new Game("The Good Game", "gameid", "Jasan Bern"));
        when(gameRepo.findGameByTitleContaining("goodgame")).thenReturn( answer);

        List<Game> retList=  controller.searchBy("title","goodgame");
        assertEquals(retList.size(), 1);

        Game result = retList.get(0);

        assertEquals(result.getTitle(),"The Good Game");
        assertEquals(result.getGameID(), "gameid");
        assertEquals(result.getCreatorID(),"Jasan Bern");

    }

    @Test
    public void getGameByAboutTest(){
        List<Game> answer = new ArrayList<Game>();
        Game g1 = new Game("The Good Game", "gameid", "Jasan Bern");
        g1.setAbout("Key game plays only");
        answer.add(g1);
        when(gameRepo.findGameByAboutContaining("key")).thenReturn( answer);

        List<Game> retList=  controller.searchBy("about","key");
        assertEquals(retList.size(), 1);

        Game result = retList.get(0);

        assertEquals(result.getTitle(),"The Good Game");
        assertEquals(result.getGameID(), "gameid");
        assertEquals(result.getCreatorID(),"Jasan Bern");

    }

    @Test
    public void getGameByBlutbTest(){
        List<Game> answer = new ArrayList<Game>();
        Game g1 = new Game("The Good Game", "gameid", "Jasan Bern");
        g1.setBlurb("Key game plays only");
        answer.add(g1);
        when(gameRepo.findGameByBlurbContaining("key")).thenReturn( answer);

        List<Game> retList=  controller.searchBy("blurb","key");
        assertEquals(retList.size(), 1);

        Game result = retList.get(0);

        assertEquals(result.getTitle(),"The Good Game");
        assertEquals(result.getGameID(), "gameid");
        assertEquals(result.getCreatorID(),"Jasan Bern");

    }
}
