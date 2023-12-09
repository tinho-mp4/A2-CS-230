package com.example._cs250a2;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HighScore {
    private Map<String, List<ScoreEntry>> highScores;

    public HighScore() {
        highScores = new HashMap<>();
    }

    public void addScore(String levelName, String profileName, int score) {
        List<ScoreEntry> scores = highScores.get(levelName);

        if (scores == null) {
            scores = new ArrayList<>();
            highScores.put(levelName, scores);
        }

        if (scores.size() < 10 || score > getLowestScore(scores)) {
            // Add the new score
            scores.add(new ScoreEntry(profileName, score));

            // Sort the list in descending order by score
            scores.sort((s1, s2) -> Integer.compare(s2.getScore(), s1.getScore()));

            // Keep only the top 10 scores
            if (scores.size() > 10) {
                scores.remove(10);
            }
        }
    }

    public List<ScoreEntry> getHighScores(String levelName) {
        return highScores.getOrDefault(levelName, new ArrayList<>());
    }

    private int getLowestScore(List<ScoreEntry> scores) {
        if (scores.isEmpty()) {
            return 0;
        }
        return scores.get(scores.size() - 1).getScore();
    }
}

