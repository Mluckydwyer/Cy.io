package com.cyio.backend.observerpatterns;

public interface LeaderboardSubject extends Subject {
    public void registerObserver(LeaderboardObserver observer);
    public void removeObserver(LeaderboardObserver observer);
    public void notifyLeaderBoardObservers();
}
