package com.cyio.backend.observerpatterns;

import com.cyio.backend.model.Entity;
import com.cyio.backend.model.Player;

import java.util.HashMap;

public interface EntityListObserver extends Observer {
    public void updateEntityList(HashMap<String, Entity> entities);
}
