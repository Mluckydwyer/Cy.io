package com.cyio.backend.observerpatterns;

import com.cyio.backend.model.LeaderBoard;
import com.cyio.backend.model.Player;

import java.util.HashMap;

public interface LeaderboardObserver extends Observer {
    public void update(LeaderBoard leaderBoard);
}
