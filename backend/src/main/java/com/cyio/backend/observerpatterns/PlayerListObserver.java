package com.cyio.backend.observerpatterns;

import com.cyio.backend.model.Player;

import java.util.HashMap;

public interface PlayerListObserver extends Observer {
    public void update(PlayerListObserver playerListObserver, HashMap<String, Player> players);
}
