package com.cyio.backend.controller;

import com.cyio.backend.exception.ResourceNotFoundException;
import com.cyio.backend.model.Game;
import com.cyio.backend.model.GameServer;
import com.cyio.backend.payload.ApiResponse;
import com.cyio.backend.repository.GameRepository;
import com.cyio.backend.repository.ServerRepository;
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
import sun.plugin.javascript.ocx.JSObject;

import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@Component
public class GameServerController {

    @Autowired
    ServerRepository serverRepository; //using autowire to create an instance of server repository

    @Autowired
    GameRepository gameRepository; //using autowire to create an instance of server repository

    public GameServerController() {
        generateDummyServer();
    }

    private void generateDummyServer() {
        String dummyGameId = "123-456-789";
        GameServer dummyGame = new GameServer(gameRepository.getOne(UUID.fromString(dummyGameId)));
        serverRepository.save(dummyGame);
    }

    @RequestMapping("/join")
    public ApiResponse join(@RequestParam(value="server-id", defaultValue="") String serverId) {
        GameServer server = serverRepository.findGameServerByServerId(serverId);//.orElseThrow(() -> new ResourceNotFoundException("GameServer", "id", serverId));
        Map<String, String> joinData = server.getJoinData();
        JSONObject jo = new JSONObject(joinData);
        return new ApiResponse(true, jo.toString());
    }

    @RequestMapping("/find")
    public ApiResponse find(@RequestParam(value="game-id", defaultValue="") String gameId) {
        String serverId = serverRepository.findGameServerByGameId(gameId);//.orElseThrow(() -> new ResourceNotFoundException("GameServer", "id", serverId));
        return join(serverId);
    }
}
