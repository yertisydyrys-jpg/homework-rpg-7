package com.narxoz.rpg.engine;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.boss.DungeonBoss;
import com.narxoz.rpg.observer.EventBus;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DungeonEngine {
    private List<Hero> heroes;
    private DungeonBoss boss;
    private EventBus eventBus;
    private int maxRounds;
    private Set<Hero> lowHpTriggered = new HashSet<>();

    public DungeonEngine(List<Hero> heroes, DungeonBoss boss, EventBus eventBus, int maxRounds) {
        this.heroes = heroes;
        this.boss = boss;
        this.eventBus = eventBus;
        this.maxRounds = maxRounds;
    }

    public EncounterResult run() {
        int round = 0;
        boolean bossDefeated = false;
        boolean allHeroesDead = false;

        while (round < maxRounds && boss.isAlive() && heroes.stream().anyMatch(Hero::isAlive)) {
            round++;
            System.out.printf("%n=== РАУНД %d ===%n", round);

            // Ход героев
            for (Hero hero : heroes) {
                if (!hero.isAlive()) continue;
                int damage = hero.calculateDamage() - boss.calculateDefense();
                if (damage < 1) damage = 1;
                boss.takeDamage(damage);
                eventBus.fireEvent(new GameEvent(GameEventType.ATTACK_LANDED, hero.getName(), damage));
                if (!boss.isAlive()) {
                    bossDefeated = true;
                    break;
                }
            }
            if (bossDefeated) break;

            // Ход босса
            for (Hero hero : heroes) {
                if (!hero.isAlive()) continue;
                int damage = boss.calculateDamage() - hero.calculateDefense();
                if (damage < 1) damage = 1;
                hero.takeDamage(damage);
                eventBus.fireEvent(new GameEvent(GameEventType.ATTACK_LANDED, boss.getName(), damage));
                if (!hero.isAlive()) {
                    eventBus.fireEvent(new GameEvent(GameEventType.HERO_DIED, hero.getName(), 0));
                } else if (!lowHpTriggered.contains(hero) && hero.getCurrentHp() <= hero.getMaxHp() * 0.3) {
                    lowHpTriggered.add(hero);
                    eventBus.fireEvent(new GameEvent(GameEventType.HERO_LOW_HP, hero.getName(), hero.getCurrentHp()));
                }
            }
        }

        List<String> survivingHeroes = new ArrayList<>();
        for (Hero h : heroes) {
            if (h.isAlive()) survivingHeroes.add(h.getName());
        }
        boolean heroesWon = bossDefeated;
        return new EncounterResult(heroesWon, round, survivingHeroes);
    }
}