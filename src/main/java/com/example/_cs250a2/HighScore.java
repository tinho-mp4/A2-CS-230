package com.example._cs250a2;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.List;

/**
 * The {@code HighScore} Stores the high scores for each level.
 * <p>
 *     The high scores are stored in a map,
 *     where the key is the level name and the value is a list of the top 10
 *     scores for that level.
 * @author Ben Foord
 * @version 1.1
 */
public class HighScore {

    private static final String DIRECTORY_PATH = "src/main/resources/com/example/_cs250a2/HighScores/HighScores/";
    private static final String FILE_PATH = DIRECTORY_PATH + "highscores.txt";

    /**
     * The maximum number of scores to store for each level.
     */
    private final int MAX_SCORES = 10;
    private Map<String, List<ScoreEntry>> highScores;

    /**
     * Creates a new HighScore object.
     */
    public HighScore() {
        highScores = new HashMap<>();
    }

    /**
     * Adds a new score to the high scores for the given level.
     *
     * @param levelName   The name of the level.
     * @param profileName The name of the profile that achieved the score.
     * @param score       The score achieved.
     */
    public void addScore(final String levelName,
                         final String profileName,
                         final int score) {
        List<ScoreEntry> scores = highScores.get(levelName);

        if (scores == null) {
            scores = new ArrayList<>();
            highScores.put(levelName, scores);
        }

        if (scores.size() < MAX_SCORES || score > getLowestScore(scores)) {
            // Add the new score
            scores.add(new ScoreEntry(profileName, score));

            // Sort the list in descending order by score
            scores.sort((s1, s2) ->
                    Integer.compare(s2.getScore(), s1.getScore()));

            // Keep only the top 10 scores
            if (scores.size() > MAX_SCORES) {
                scores.remove(MAX_SCORES);
            }
        }
    }

    /**
     * Gets the high scores for the given level.
     *
     * @param levelName The name of the level.
     * @return A list of the top 10 scores for the given level.
     */
    public List<ScoreEntry> getHighScores(final String levelName) {
        return highScores.getOrDefault(levelName, new ArrayList<>());
    }

    /**
     * Gets the lowest score in the given list of scores.
     *
     * @param scores The list of scores.
     * @return The lowest score in the list.
     */
    private int getLowestScore(final List<ScoreEntry> scores) {
        if (scores.isEmpty()) {
            return 0;
        }
        return scores.get(scores.size() - 1).getScore();
    }

    public static void saveHighScores(List<ScoreEntry> highScores) {

        try {
            // Create the directory if it doesn't exist
            Files.createDirectories(Paths.get(DIRECTORY_PATH));
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
                oos.writeObject(highScores);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<ScoreEntry> loadHighScores() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (List<ScoreEntry>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return List.of();
        }
    }
}

