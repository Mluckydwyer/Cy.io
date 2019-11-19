package com.cyio.backend.observerpatterns;

public interface PlayerListSubject extends Subject {
    public void registerObserver(PlayerListObserver observer);
    public void removeObserver(PlayerListObserver observer);
    public void notifyPlayerListObservers();
}
