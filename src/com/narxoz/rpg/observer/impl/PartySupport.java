package com.narxoz.rpg.observer.impl;

import com.narxoz.rpg.observer.GameObserver;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import com.narxoz.rpg.combatant.Hero;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class PartySupport implements GameObserver {
    private List<Hero> heroes;
    private Random random = new Random();
    private static final int HEAL_AMOUNT = 20;

    public PartySupport(List<Hero> heroes) {
        this.heroes = heroes;
    }

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.HERO_LOW_HP) {
            List<Hero> aliveHeroes = heroes.stream().filter(Hero::isAlive).collect(Collectors.toList());
            if (aliveHeroes.isEmpty()) return;
            int index = random.nextInt(aliveHeroes.size());
            Hero target = aliveHeroes.get(index);
            target.heal(HEAL_AMOUNT);
            System.out.printf("[ПОДДЕРЖКА] %s исцелён на %d HP (текущее HP: %d)%n",
                    target.getName(), HEAL_AMOUNT, target.getCurrentHp());
        }
    }
}