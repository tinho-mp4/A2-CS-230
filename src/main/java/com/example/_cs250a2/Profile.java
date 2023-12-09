package com.example._cs250a2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;

public class Profile {
    private String name;

    private int levelReached;
    private Map<String, Integer> levelScores;
    private Map<String, PriorityQueue<ScoreEntry>> highScores;



    public Profile(String name) {
        this.name = name;
        this.levelReached = 3;
        this.levelScores = new HashMap<>();
        this.highScores = new HashMap<>();

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

    public void addScoreToHighScores(String level, int score) {
        highScores.putIfAbsent(level, new PriorityQueue<>(Comparator.comparingInt(ScoreEntry::getScore)));
        PriorityQueue<ScoreEntry> levelHighScores = highScores.get(level);
        if (levelHighScores.size() < 10 || score > levelHighScores.peek().getScore()) {
            if (levelHighScores.size() == 10) {
                levelHighScores.poll();
            }
            levelHighScores.add(new ScoreEntry(name, score));
        }
    }

    public List<ScoreEntry> getHighScores(String level) {
        if (!highScores.containsKey(level)) {
            return Collections.emptyList();
        }
        List<ScoreEntry> scores = new ArrayList<>(highScores.get(level));
        scores.sort(Comparator.comparingInt(ScoreEntry::getScore).reversed());
        return scores;
    }


    @Override
    public String toString() {
        return name;
    }
}