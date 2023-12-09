package com.example._cs250a2;
import java.util.HashMap;
import java.util.Map;

public class HighScore {
    private Map<String, ScoreEntry> highScores;

    public HighScore() {
        highScores = new HashMap<>();
    }

    public void addScore(String levelName, String profileName, int score) {
        ScoreEntry currentHighScore = highScores.get(levelName);
        if (currentHighScore == null || score > currentHighScore.getScore()) {
            highScores.put(levelName, new ScoreEntry(profileName, score));
        }
    }

    public ScoreEntry getHighScore(String levelName) {
        return highScores.get(levelName);
    }
}
