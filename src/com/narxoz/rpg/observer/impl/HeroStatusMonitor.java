package com.narxoz.rpg.observer.impl;

import com.narxoz.rpg.observer.GameObserver;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import com.narxoz.rpg.combatant.Hero;
import java.util.List;
import java.util.stream.Collectors;

public class HeroStatusMonitor implements GameObserver {
    private List<Hero> heroes;

    public HeroStatusMonitor(List<Hero> heroes) {
        this.heroes = heroes;
    }

    private void printStatus() {
        String status = heroes.stream()
                .map(h -> String.format("%s: %d/%d HP%s",
                        h.getName(), h.getCurrentHp(), h.getMaxHp(), h.isAlive() ? "" : " (МЁРТВ)"))
                .collect(Collectors.joining(" | "));
        System.out.println("[СТАТУС ГЕРОЕВ] " + status);
    }

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.HERO_LOW_HP || event.getType() == GameEventType.HERO_DIED) {
            printStatus();
        }
    }
}