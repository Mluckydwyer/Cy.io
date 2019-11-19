package com.cyio.backend.observerpatterns;

import com.cyio.backend.model.Player;

import java.util.HashMap;

public interface EntitiesListObserver extends Observer {
    public void update(EntitiesListObserver playerListObserver, HashMap<String, Player> players);
}
