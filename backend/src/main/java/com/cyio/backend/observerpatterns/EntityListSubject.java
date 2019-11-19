package com.cyio.backend.observerpatterns;

public interface EntityListSubject extends Subject {
    public void registerObserver(EntityListObserver observer);
    public void removeObserver(EntityListObserver observer);
    public void notifyEntityListObservers();
}
