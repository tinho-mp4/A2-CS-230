package com.example._cs250a2;

import java.io.*;
import java.nio.file.*;
import java.util.List;

public class HighScoreFileManager {

    private static final String DIRECTORY_PATH = "src/main/resources/com/example/_cs250a2/HighScores/HighScores/";
    private static final String FILE_PATH = DIRECTORY_PATH + "highscores.txt";

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

