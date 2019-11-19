package com.cyio.backend.observerpatterns;

import com.cyio.backend.model.Player;

import java.util.HashMap;

public interface EntityListObserver extends Observer {
    public void update(HashMap<String, Player> players);
}
