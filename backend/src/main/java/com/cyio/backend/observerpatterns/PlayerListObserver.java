package com.cyio.backend.observerpatterns;

import com.cyio.backend.model.Player;

import java.util.HashMap;

public interface PlayerListObserver extends Observer {
    public void updatePlayerList(HashMap<String, Player> players);
}
