package com.example._cs250a2;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class HighScoreFileManager {

    private static final String DIRECTORY_PATH = "src/main/resources/com/example/_cs250a2/HighScores/HighScores/";
    private static final String FILE_PATH = DIRECTORY_PATH + "highscores.txt";

    //save all high scores to a file
    public static void saveHighScores(List<ScoreEntry> highScores, String levelName) {
        String filePath = DIRECTORY_PATH + levelName + "_highscores.txt";

        try {
            Files.createDirectories(Paths.get(DIRECTORY_PATH));
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
                oos.writeObject(highScores);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //load all the scores from a file
    public static List<ScoreEntry> loadHighScores(String levelName) {
        String filePath = FILE_PATH;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<ScoreEntry>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return List.of();
        }
    }
}
