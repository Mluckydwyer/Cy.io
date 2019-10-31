package com.cyio.backend.controller;

import com.cyio.backend.model.LeaderBoard;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class LeaderBoardController {
    @MessageMapping("/leaderboard")
    @SendTo("/game/leaderboard")
    public LeaderBoard leaderBoard() throws Exception{
        Thread.sleep(1000);
        LeaderBoard lb = new LeaderBoard();
        lb.generateDummyData();
        return lb;
    }
}
