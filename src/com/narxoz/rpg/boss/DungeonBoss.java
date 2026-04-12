package com.narxoz.rpg.boss;

import com.narxoz.rpg.strategy.CombatStrategy;
import com.narxoz.rpg.strategy.BossPhase1Strategy;
import com.narxoz.rpg.strategy.BossPhase2Strategy;
import com.narxoz.rpg.strategy.BossPhase3Strategy;
import com.narxoz.rpg.observer.GameObserver;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import com.narxoz.rpg.observer.EventBus;

public class DungeonBoss implements GameObserver {
    private String name;
    private int maxHp;
    private int currentHp;
    private int attackPower;
    private int defense;
    private CombatStrategy activeStrategy;
    private int phase; // 1,2,3
    private EventBus eventBus;

    public DungeonBoss(String name, int maxHp, int attackPower, int defense, EventBus eventBus) {
        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.attackPower = attackPower;
        this.defense = defense;
        this.eventBus = eventBus;
        this.phase = 1;
        this.activeStrategy = new BossPhase1Strategy();
        eventBus.register(this);
    }

    public String getName() { return name; }
    public int getCurrentHp() { return currentHp; }
    public int getMaxHp() { return maxHp; }
    public boolean isAlive() { return currentHp > 0; }
    public int getPhase() { return phase; }

    public int calculateDamage() {
        return activeStrategy.calculateDamage(attackPower);
    }

    public int calculateDefense() {
        return activeStrategy.calculateDefense(defense);
    }

    public void takeDamage(int damage) {
        if (damage < 0) damage = 0;
        int oldHp = currentHp;
        currentHp = Math.max(0, currentHp - damage);
        if (currentHp <= 0) {
            eventBus.fireEvent(new GameEvent(GameEventType.BOSS_DEFEATED, name, oldHp - currentHp));
        } else {
            checkPhaseTransition();
        }
    }

    private void checkPhaseTransition() {
        double hpPercent = (double) currentHp / maxHp * 100;
        int newPhase = phase;
        if (hpPercent <= 30 && phase < 3) newPhase = 3;
        else if (hpPercent <= 60 && phase < 2) newPhase = 2;
        if (newPhase != phase) {
            phase = newPhase;
            eventBus.fireEvent(new GameEvent(GameEventType.BOSS_PHASE_CHANGED, name, phase));
        }
    }

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.BOSS_PHASE_CHANGED && event.getSourceName().equals(name)) {
            int newPhase = event.getValue();
            if (newPhase != phase) {
                phase = newPhase;
                switch (phase) {
                    case 2:
                        activeStrategy = new BossPhase2Strategy();
                        break;
                    case 3:
                        activeStrategy = new BossPhase3Strategy();
                        break;
                    default:
                        activeStrategy = new BossPhase1Strategy();
                }
                System.out.printf("[БОСС %s] Активирована стратегия: %s%n", name, activeStrategy.getName());
            }
        }
    }
}