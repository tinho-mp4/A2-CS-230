package com.example._cs250a2;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Collections;
import java.util.ArrayList;

/**
 * Handles player profiles in the game.
 * A profile contains the player's name, the highest level they have reached, their scores for each level,
 * and the high scores for each level. The profile can be saved to and loaded from a file.
 *
 * @author Ben
 */
public class Profile implements Serializable {

    private final String name;
    private int levelReached;
    private Map<String, Integer> levelScores;
    private Map<String, PriorityQueue<ScoreEntry>> highScores;
    public static final int MAX_LEVEL = 5;
    public static final int LEVEL_HIGH_SCORE = 10;

    /**
     * Creates a new profile with the given name.
     *
     * @param name The name of the profile.
     */
    public Profile(String name) {
        this.name = name;
        this.levelReached = MAX_LEVEL;
        this.levelScores = new HashMap<>();
        this.highScores = new HashMap<>();
    }

    /**
     *  Create a new profile with the given name and load it from the given file.
     */
    public String getName() {
        return name;
    }

    public void nextLevel() {
        levelReached = (levelReached + 1);
    }

    /**
     * Get the highest level reached by the player.
     * @return The highest level reached by the player.
     */
    public int getLevelReached() {
        return levelReached;
    }

    /**
     * Set the highest level reached by the player.
     * @param levelReached  The highest level reached by the player.
     */
    public void setLevelReached(int levelReached) {
        this.levelReached = levelReached;
    }

    /**
     * Get the score for the given level.
     * @param level The level to get the score for.
     */
    public int getScoreForLevel(String level) {
        return levelScores.getOrDefault(level, 0);
    }

    /**
     * Set the score for the given level.
     * @param level The level to set the score for.
     * @param score The score to set.
     */
    public void setScoreForLevel(String level, int score) {
        levelScores.put(level, score);
    }

    /**
     * Saves the profile to a file.
     *
     * @param filePath The path to the file to save the profile to.
     */
    public void saveToFile(String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load the profile from a file.
     * @param filePath The path to the file to load the profile from.
     */
    private void loadFromFile(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            Profile savedProfile = (Profile) ois.readObject();
            this.levelReached = savedProfile.levelReached;
            this.levelScores = savedProfile.levelScores;
            this.highScores = savedProfile.highScores;
        } catch (FileNotFoundException e) {
            // Ignore if the file doesn't exist (first run)
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * Add a score to the high scores for the given level.
     * @param level The level to add the score to.
     * @param score The score to add.
     */
    public void addScoreToHighScores(String level, int score) {
        highScores.putIfAbsent(level, new PriorityQueue<>(Comparator.comparingInt(ScoreEntry::getScore)));
        PriorityQueue<ScoreEntry> levelHighScores = highScores.get(level);
        if (levelHighScores.size() < LEVEL_HIGH_SCORE || score > levelHighScores.peek().getScore()) {
            if (levelHighScores.size() == LEVEL_HIGH_SCORE) {
                levelHighScores.poll();
            }
            levelHighScores.add(new ScoreEntry(name, score));
        }
    }

    /**
     * Get the high scores for the given level.
     * @param level The level to get the high scores for.
     * @return The high scores for the given level.
     */
    public List<ScoreEntry> getHighScores(String level) {
        if (!highScores.containsKey(level)) {
            return Collections.emptyList();
        }
        List<ScoreEntry> scores = new ArrayList<>(highScores.get(level));
        scores.sort(Comparator.comparingInt(ScoreEntry::getScore).reversed());
        return scores;
    }

    /**
     * Provides a string representation of the profile.
     * Currently, it returns the name of the profile.
     *
     * @return The name of the profile.
     */
    @Override
    public String toString() {
        return name;
    }
}