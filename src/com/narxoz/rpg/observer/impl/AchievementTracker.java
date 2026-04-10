package com.narxoz.rpg.observer.impl;

import com.narxoz.rpg.observer.GameObserver;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import java.util.HashSet;
import java.util.Set;

public class AchievementTracker implements GameObserver {
    private int attackCount = 0;
    private boolean firstBloodUnlocked = false;
    private boolean relentlessUnlocked = false;
    private boolean bossSlayerUnlocked = false;
    private Set<String> unlocked = new HashSet<>();

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.ATTACK_LANDED) {
            attackCount++;
            if (!relentlessUnlocked && attackCount >= 10) {
                System.out.println("[ДОСТИЖЕНИЕ] Неутомимый: нанесено 10 атак!");
                relentlessUnlocked = true;
            }
        } else if (event.getType() == GameEventType.HERO_DIED) {
            if (!firstBloodUnlocked) {
                System.out.println("[ДОСТИЖЕНИЕ] Первая кровь: герой пал в бою!");
                firstBloodUnlocked = true;
            }
        } else if (event.getType() == GameEventType.BOSS_DEFEATED) {
            if (!bossSlayerUnlocked) {
                System.out.println("[ДОСТИЖЕНИЕ] Убийца босса: босс повержен!");
                bossSlayerUnlocked = true;
            }
        }
    }
}