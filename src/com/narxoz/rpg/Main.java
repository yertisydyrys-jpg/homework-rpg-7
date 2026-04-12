package com.narxoz.rpg;

import com.narxoz.rpg.strategy.*;
import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.boss.DungeonBoss;
import com.narxoz.rpg.engine.DungeonEngine;
import com.narxoz.rpg.engine.EncounterResult;
import com.narxoz.rpg.observer.EventBus;
import com.narxoz.rpg.observer.impl.*;
import java.util.List;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        EventBus eventBus = EventBus.getInstance();

        // Создаём героев с разными стратегиями
        Hero warrior = new Hero("Воин", 120, 25, 15, new AggressiveStrategy());
        Hero mage = new Hero("Маг", 90, 30, 8, new BalancedStrategy());
        Hero paladin = new Hero("Паладин", 140, 20, 20, new DefensiveStrategy());

        List<Hero> heroes = Arrays.asList(warrior, mage, paladin);

        // Создаём босса
        DungeonBoss boss = new DungeonBoss("Древний Дракон", 400, 35, 20, eventBus);

        // Создаём наблюдателей
        BattleLogger logger = new BattleLogger();
        AchievementTracker achievements = new AchievementTracker();
        PartySupport partySupport = new PartySupport(heroes);
        HeroStatusMonitor statusMonitor = new HeroStatusMonitor(heroes);
        LootDropper lootDropper = new LootDropper();

        // Регистрируем наблюдателей
        eventBus.register(logger);
        eventBus.register(achievements);
        eventBus.register(partySupport);
        eventBus.register(statusMonitor);
        eventBus.register(lootDropper);


        DungeonEngine engine = new DungeonEngine(heroes, boss, eventBus, 50);

        Thread strategySwitchThread = new Thread(() -> {
            try {
                Thread.sleep(2000);
                warrior.setStrategy(new DefensiveStrategy());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        strategySwitchThread.start();


        EncounterResult result = engine.run();

        System.out.printf("%n=== РЕЗУЛЬТАТ ВСТРЕЧИ ===%n");
        System.out.printf("Победили герои? %s%n", result.isHeroesWon() ? "ДА" : "НЕТ");
        System.out.printf("Раундов сыграно: %d%n", result.getRoundsPlayed());
        System.out.printf("Выжившие герои: %s%n", result.getSurvivingHeroes().isEmpty() ? "Никого" : String.join(", ", result.getSurvivingHeroes()));
    }
}