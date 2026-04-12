package com.narxoz.rpg.observer.impl;

import com.narxoz.rpg.observer.GameObserver;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import java.util.Random;

public class LootDropper implements GameObserver {
    private Random random = new Random();

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.BOSS_PHASE_CHANGED) {
            int phase = event.getValue();
            switch (phase) {
                case 1:
                    System.out.println("[ЛУТ] Переход в фазу 1: Малое зелье здоровья");
                    break;
                case 2:
                    System.out.println("[ЛУТ] Переход в фазу 2: Эпический меч!");
                    break;
                case 3:
                    System.out.println("[ЛУТ] Переход в фазу 3: Легендарный шлем!");
                    break;
            }
        } else if (event.getType() == GameEventType.BOSS_DEFEATED) {
            String[] rareLoot = {"Свиток воскрешения", "Кольцо власти", "Драконий глаз"};
            String loot = rareLoot[random.nextInt(rareLoot.length)];
            System.out.printf("[ЛУТ] Победа над боссом! Вы получили: %s%n", loot);
        }
    }
}