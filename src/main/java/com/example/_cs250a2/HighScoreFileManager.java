package com.example._cs250a2;

import java.io.*;
import java.nio.file.*;
import java.util.List;

/**
 * Manages high score data for levels in a game, including saving and loading scores.
 * High scores are stored in files with a '.dat' extension in a specific directory.
 * Each level's high scores are stored in a separate file.
 *
 * @author Ben Foord
 * @version 1.0
 */
public class HighScoreFileManager {

    /**
     * The directory path where the high scores are stored.
     */
    private static final String DIRECTORY_PATH =
            "src/main/resources/com/example/_cs250a2/HighScores/HighScores/";
    /**
     * The file extension for the high score files.
     */
    private static final String FILE_EXTENSION = ".dat";

    /**
     * Saves the high scores to a file.
     * @param highScores the high scores to save
     * @param levelName the name of the level
     */
    public static void saveHighScores(final List<ScoreEntry> highScores,
                                      final String levelName) {

        try {
            // Create the directory if it doesn't exist
            Files.createDirectories(Paths.get(DIRECTORY_PATH));
            String filePath = DIRECTORY_PATH + levelName + FILE_EXTENSION;

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
                oos.writeObject(highScores);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the high scores from a file.
     * @param levelName the name of the level
     * @return the high scores
     */
    public static List<ScoreEntry> loadHighScores(final String levelName) {
        String filePath = DIRECTORY_PATH + levelName + FILE_EXTENSION;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<ScoreEntry>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return List.of();
        }
    }
}
