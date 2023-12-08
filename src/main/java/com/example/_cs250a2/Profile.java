package com.example._cs250a2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Profile {
    private String name;

    private int levelReached;
    private Map<String, Integer> levelScores;


    public Profile(String name) {
        this.name = name;
        this.levelReached = 3;
        this.levelScores = new HashMap<>();
    }



    public String getName() {
        return name;
    }

    public int getLevelReached() {
        return levelReached;
    }

    public void setLevelReached(int levelReached) {
        this.levelReached = levelReached;
    }

    public int getScoreForLevel(String level) {
        return levelScores.getOrDefault(level, 0);
    }

    public void setScoreForLevel(String level, int score) {
        levelScores.put(level, score);
    }

    @Override
    public String toString() {
        return name;
    }
}