package com.narxoz.rpg.observer;

import java.util.ArrayList;
import java.util.List;

public class EventBus {
    private static EventBus instance;
    private List<GameObserver> observers = new ArrayList<>();

    private EventBus() {}

    public static EventBus getInstance() {
        if (instance == null) {
            instance = new EventBus();
        }
        return instance;
    }

    public void register(GameObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void unregister(GameObserver observer) {
        observers.remove(observer);
    }

    public void fireEvent(GameEvent event) {
        for (GameObserver observer : observers) {
            observer.onEvent(event);
        }
    }
}