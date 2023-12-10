package com.example._cs250a2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HighScoreManager {

    private final int MAX_SCORES = 10;
    private final Map<String, List<ScoreEntry>> highScores;

    public HighScoreManager() {
        highScores = new HashMap<>();
    }

    public void addScore(final String levelName, final String profileName, final int score) {
        List<ScoreEntry> scores = highScores.get(levelName);

        if (scores == null) {
            scores = new ArrayList<>();
            highScores.put(levelName, scores);
        }

        if (scores.size() < MAX_SCORES || score > getLowestScore(scores)) {
            scores.add(new ScoreEntry(profileName, score));

            scores.sort((s1, s2) -> Integer.compare(s2.getScore(), s1.getScore()));

            if (scores.size() > MAX_SCORES) {
                scores.remove(MAX_SCORES);
            }
        }
    }

    public List<ScoreEntry> getHighScores(final String levelName) {
        return highScores.getOrDefault(levelName, new ArrayList<>());
    }

    public void saveHighScores(List<ScoreEntry> highScores, String levelName) {
        HighScoreFileManager.saveHighScores(highScores, levelName);
    }

    public List<ScoreEntry> loadHighScores(String levelName) {
        return HighScoreFileManager.loadHighScores(levelName);
    }

    private int getLowestScore(final List<ScoreEntry> scores) {
        if (scores.isEmpty()) {
            return 0;
        }
        return scores.get(scores.size() - 1).getScore();
    }
}
