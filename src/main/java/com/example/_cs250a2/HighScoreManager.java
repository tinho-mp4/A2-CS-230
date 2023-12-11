package com.example._cs250a2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages the high scores for different levels in the game.
 * Stores and updates scores, and provides access to the top scores.
 * @author Ben Foord
 * @version 1.0
 */
public class HighScoreManager {

    /**
     * The maximum number of high scores to be stored for each level.
     */
    public static final int MAX_SCORES = 10;

    /**
     * A map that stores lists of high scores for each level, with the level name as the key.
     * Each list contains ScoreEntry objects representing the top scores for that level.
     */
    private final Map<String, List<ScoreEntry>> highScores;


    /**
     * Constructs a new HighScoreManager.
     */
    public HighScoreManager() {
        highScores = new HashMap<>();
    }

    /**
     * Adds a score entry for a specific level.
     * If the score qualifies as a high score, it is added to the list for that level.
     *
     * @param levelName   The name of the level.
     * @param profileName The name of the player's profile.
     * @param score       The score to be added.
     */
    public void addScore(final String levelName, final String profileName, final int score) {
        List<ScoreEntry> scores = highScores.computeIfAbsent(levelName, k -> new ArrayList<>());

        int maxScores = MAX_SCORES;
        if (scores.size() < maxScores || score > getLowestScore(scores)) {
            scores.add(new ScoreEntry(profileName, score));

            scores.sort((s1, s2) -> Integer.compare(s2.getScore(), s1.getScore()));

            if (scores.size() > maxScores) {
                scores.remove(maxScores);
            }
        }
    }

    /**
     * Retrieves the list of high scores for a given level.
     *
     * @param levelName The name of the level.
     * @return A list of ScoreEntry objects representing the high scores for that level.
     */
    public List<ScoreEntry> getHighScores(final String levelName) {
        return highScores.getOrDefault(levelName, new ArrayList<>());
    }

    /**
     * Saves the high scores for a specific level to a file.
     *
     * @param highScores The list of high scores to save.
     * @param levelName  The name of the level.
     */
    public void saveHighScores(List<ScoreEntry> highScores, String levelName) {
        HighScoreFileManager.saveHighScores(highScores, levelName);
    }

    /**
     * Gets the lowest score in a list of score entries.
     *
     * @param scores The list of ScoreEntry objects.
     * @return The lowest score found in the list.
     */
    private int getLowestScore(final List<ScoreEntry> scores) {
        if (scores.isEmpty()) {
            return 0;
        }
        return scores.get(scores.size() - 1).getScore();
    }
}
