package com.narxoz.rpg.observer.impl;

import com.narxoz.rpg.observer.GameObserver;
import com.narxoz.rpg.observer.GameEvent;

public class BattleLogger implements GameObserver {
    @Override
    public void onEvent(GameEvent event) {
        switch (event.getType()) {
            case ATTACK_LANDED:
                System.out.printf("[БОЙ] %s нанёс %d урона%n",
                        event.getSourceName(), event.getValue());
                break;
            case HERO_LOW_HP:
                System.out.printf("[СОБЫТИЕ] Герой %s при смерти! Осталось HP: %d%n",
                        event.getSourceName(), event.getValue());
                break;
            case HERO_DIED:
                System.out.printf("[СОБЫТИЕ] %s погиб!%n", event.getSourceName());
                break;
            case BOSS_PHASE_CHANGED:
                System.out.printf("[СОБЫТИЕ] Босс перешёл в фазу %d!%n", event.getValue());
                break;
            case BOSS_DEFEATED:
                System.out.printf("[СОБЫТИЕ] Босс повержен! Бой длился %d раундов.%n", event.getValue());
                break;
        }
    }
}