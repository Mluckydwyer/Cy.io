package com.cyio.backend.controller;

import com.cyio.backend.model.Game;
import com.cyio.backend.model.GameServer;
import com.cyio.backend.payload.JSONResponse;
import com.cyio.backend.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Map;

@Controller
@Component
@RestController
public class GameServerController {

//    @Autowired
//    ServerRepository serverRepository; //using autowire to create an instance of server repository

    @Autowired
    GameRepository gameRepository; //using autowire to create an instance of game repository

    GameServer gs;

    @PostConstruct
    public void init() {
        generateDummyServer();
    }

    private void generateDummyServer() {
        String dummyGameId = "123e4567-e89b-12d3-a456-426655440000";
        String dummyCreatorId = "999e4567-e89b-12d3-a456-426655440000";
        Game dummyGame = new Game("Game 1", dummyGameId, dummyCreatorId);
        GameServer dummyGameServer = new GameServer(dummyGame);
        this.gs = dummyGameServer;

        //serverRepository.save(dummyGame);
    }

    public Map<String, String> join(String serverId) {
        GameServer server = gs; //serverRepository.findGameServerByServerId(serverId);//.orElseThrow(() -> new ResourceNotFoundException("GameServer", "id", serverId));
        Map<String, String> joinData = server.getJoinData();
        return joinData;
    }

    @RequestMapping("/join")
    public JSONResponse joinRequest(@RequestParam(value="serverId", defaultValue="") String serverId) {
        return new JSONResponse(true, join(serverId));
    }

    @RequestMapping("/find")
    public JSONResponse find(@RequestParam(value="gameId", defaultValue="") String gameId) {
//        String serverId = serverRepository.findGameServerByGameId(gameId);
//        if (serverId == null) {
//            GameServer server = new GameServer(gameRepository.getOne(UUID.fromString(gameId)));
//            serverRepository.save(server);
//        }
//        return join(serverId);
        return new JSONResponse(true, join(gs.getServerID()));
    }
}
