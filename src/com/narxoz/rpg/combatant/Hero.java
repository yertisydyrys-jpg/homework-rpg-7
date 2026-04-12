package com.narxoz.rpg.combatant;

import com.narxoz.rpg.strategy.CombatStrategy;

public class Hero {
    private String name;
    private int maxHp;
    private int currentHp;
    private int attackPower;
    private int defense;
    private CombatStrategy strategy;

    public Hero(String name, int maxHp, int attackPower, int defense, CombatStrategy strategy) {
        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.attackPower = attackPower;
        this.defense = defense;
        this.strategy = strategy;
    }

    public String getName() { return name; }
    public int getCurrentHp() { return currentHp; }
    public int getMaxHp() { return maxHp; }
    public int getAttackPower() { return attackPower; }
    public int getDefense() { return defense; }
    public boolean isAlive() { return currentHp > 0; }

    public void setStrategy(CombatStrategy strategy) {
        this.strategy = strategy;
        System.out.printf("[ГЕРОЙ %s] Сменил стратегию на %s%n", name, strategy.getName());
    }

    public CombatStrategy getStrategy() { return strategy; }

    public int calculateDamage() {
        return strategy.calculateDamage(attackPower);
    }

    public int calculateDefense() {
        return strategy.calculateDefense(defense);
    }

    public void takeDamage(int damage) {
        if (damage < 0) damage = 0;
        currentHp = Math.max(0, currentHp - damage);
    }

    public void heal(int amount) {
        if (!isAlive()) return;
        currentHp = Math.min(maxHp, currentHp + amount);
    }

    @Override
    public String toString() {
        return name + " (HP:" + currentHp + "/" + maxHp + ")";
    }
}