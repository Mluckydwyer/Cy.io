package com.mockito.tests;

import com.cyio.backend.controller.GameController;
import com.cyio.backend.controller.GameServerController;
import com.cyio.backend.model.*;
import com.cyio.backend.websockets.LeaderboardSocket;
import com.cyio.backend.websockets.PlayerDataSocket;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.UUID;

import static org.mockito.Mockito.when;

public class PlayerDataSocketTests {

    @InjectMocks
    GameServerController gameServerController;

    @Mock
    GameServer gameServer;

    @Mock
    public PlayerDataObjects playerDataObjects;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void fillEntitiesTest() {
        String dummyGameId = "123e4567-e89b-12d3-a456-426655440000";
        String dummyCreatorId = "999e4567-e89b-12d3-a456-426655440000";
        Game dummyGame = new Game("Cygar.io", dummyGameId, dummyCreatorId);
        gameServer = new GameServer(dummyGame, null, null, null, null);
        gameServer.setPlayerDataSocket(new PlayerDataSocket());
        gameServer.setLeaderboardSocket(new LeaderboardSocket());
        gameServer.getPlayerDataSocket().pdos = new PlayerDataObjects();
        gameServer.init();

        assert(gameServer.getPlayerDataSocket().pdos.getEntities().isEmpty());
        gameServer.getPlayerDataSocket().fillEntities();
        assert(gameServer.getPlayerDataSocket().getAllEntities().size() == 100);
        for (Entity e1 : gameServer.getPlayerDataSocket().getAllEntities()) {
            for (Entity e2 : gameServer.getPlayerDataSocket().getAllEntities()) {
                if (e1 == e2) continue;
                assert(!e1.getId().equals(e2.getId()));
                assert(e1.getxPos() != e2.getxPos() || e1.getxPos() != e2.getxPos());
            }
        }

    }

    @Test
    public void updatePlayerListTest() {
        String dummyGameId = "123e4567-e89b-12d3-a456-426655440000";
        String dummyCreatorId = "999e4567-e89b-12d3-a456-426655440000";
        Game dummyGame = new Game("Cygar.io", dummyGameId, dummyCreatorId);
        gameServer = new GameServer(dummyGame, null, null, null, null);
        gameServer.setPlayerDataSocket(new PlayerDataSocket());
        gameServer.setLeaderboardSocket(new LeaderboardSocket());
        gameServer.getPlayerDataSocket().pdos = new PlayerDataObjects();
        gameServer.init();

        HashMap<String, Player> testData = new HashMap<>();
        for (int i = 0; i < 1000; i++)
            testData.put(UUID.randomUUID().toString(), new Player(UUID.randomUUID().toString()));

        gameServer.updatePlayerList(testData);

            for (String key : testData.keySet()) {
                assert(gameServer.players.containsKey(key));
                assert(gameServer.players.containsValue(testData.get(key)));
        }
    }

    @Test
    public void updateLeaderboardTest() {
        String dummyGameId = "123e4567-e89b-12d3-a456-426655440000";
        String dummyCreatorId = "999e4567-e89b-12d3-a456-426655440000";
        Game dummyGame = new Game("Cygar.io", dummyGameId, dummyCreatorId);
        gameServer = new GameServer(dummyGame, null, null, null, null);
        gameServer.setPlayerDataSocket(new PlayerDataSocket());
        gameServer.setLeaderboardSocket(new LeaderboardSocket());
        gameServer.getPlayerDataSocket().pdos = new PlayerDataObjects();
        gameServer.init();

        LeaderBoard testLeaderBoard = new LeaderBoard();
        testLeaderBoard.generateDummyData();

        gameServer.updateLeaderBoard(testLeaderBoard);

        for (Player player : testLeaderBoard.getLeaderList()) {
            assert(gameServer.leaderBoard.getLeaderList().contains(player));
        }
    }

    @Test
    public void updateEntityListTest() {
        String dummyGameId = "123e4567-e89b-12d3-a456-426655440000";
        String dummyCreatorId = "999e4567-e89b-12d3-a456-426655440000";
        Game dummyGame = new Game("Cygar.io", dummyGameId, dummyCreatorId);
        gameServer = new GameServer(dummyGame, null, null, null, null);
        gameServer.setPlayerDataSocket(new PlayerDataSocket());
        gameServer.setLeaderboardSocket(new LeaderboardSocket());
        gameServer.getPlayerDataSocket().pdos = new PlayerDataObjects();
        gameServer.init();

        HashMap<String, Entity> testData = new HashMap<>();
        for (int i = 0; i < 1000; i++)
            testData.put(UUID.randomUUID().toString(), new Entity());

        gameServer.updateEntityList(testData);

        for (String key : testData.keySet()) {
            assert(gameServer.entities.containsKey(key));
            assert(gameServer.entities.containsValue(testData.get(key)));
        }
    }

}
