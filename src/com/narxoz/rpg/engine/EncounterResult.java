package com.narxoz.rpg.engine;

import java.util.List;

public class EncounterResult {
    private final boolean heroesWon;
    private final int roundsPlayed;
    private final List<String> survivingHeroes;

    public EncounterResult(boolean heroesWon, int roundsPlayed, List<String> survivingHeroes) {
        this.heroesWon = heroesWon;
        this.roundsPlayed = roundsPlayed;
        this.survivingHeroes = survivingHeroes;
    }

    public boolean isHeroesWon() { return heroesWon; }
    public int getRoundsPlayed() { return roundsPlayed; }
    public List<String> getSurvivingHeroes() { return survivingHeroes; }
}