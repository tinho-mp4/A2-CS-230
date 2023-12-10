package com.example._cs250a2;

import java.io.*;
import java.nio.file.*;
import java.util.List;

public class HighScoreFileManager {

    private static final String DIRECTORY_PATH = "src/main/resources/com/example/_cs250a2/HighScores/HighScores/";
    private static final String FILE_EXTENSION = ".dat";

    public static void saveHighScores(List<ScoreEntry> highScores, String levelName) {

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

    public static List<ScoreEntry> loadHighScores(String levelName) {
        String filePath = DIRECTORY_PATH + levelName + FILE_EXTENSION;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<ScoreEntry>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return List.of();
        }
    }
}
