package com.cyio.backend.controller;

import com.cyio.backend.exception.ResourceNotFoundException;
import com.cyio.backend.model.Game;
import com.cyio.backend.model.GameServer;
import com.cyio.backend.payload.ApiResponse;
import com.cyio.backend.repository.GameRepository;
import com.cyio.backend.websockets.LeaderboardSocket;
import com.cyio.backend.websockets.NotificationSocket;
import jdk.nashorn.internal.ir.debug.JSONWriter;
import jdk.nashorn.internal.runtime.JSONFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import java.util.Map;

@Controller
@Component
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

    @RequestMapping("/join")
    public ApiResponse join(@RequestParam(value="server-id", defaultValue="") String serverId) {
        GameServer server = gs; //serverRepository.findGameServerByServerId(serverId);//.orElseThrow(() -> new ResourceNotFoundException("GameServer", "id", serverId));
        Map<String, String> joinData = server.getJoinData();
        JSONObject jo = new JSONObject(joinData);
        return new ApiResponse(true, jo.toString());
    }

    @RequestMapping("/find")
    public ApiResponse find(@RequestParam(value="game-id", defaultValue="") String gameId) {
//        String serverId = serverRepository.findGameServerByGameId(gameId);
//        if (serverId == null) {
//            GameServer server = new GameServer(gameRepository.getOne(UUID.fromString(gameId)));
//            serverRepository.save(server);
//        }
//        return join(serverId);
        return join(gs.getServerID());
    }
}
