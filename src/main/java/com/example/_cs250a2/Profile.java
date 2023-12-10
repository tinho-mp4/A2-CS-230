package com.example._cs250a2;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;

/**
 * The {@code Profile} class handles player profiles in the game.
 * <p>
 *     A profile contains the player's name, the highest level they have reached, and their scores for each level.
 *     It also contains the high scores for each level.
 *     The profile can be saved to a file and loaded from a file.
 *     The profile is serializable.
 *     @author Ben Foord
 *     @version 1.0
 */
public class Profile implements Serializable{

    private final String name;

    private int levelReached;
    private Map<String, Integer> levelScores;
    private Map<String, PriorityQueue<ScoreEntry>> highScores;



    /**
     * Create a new profile with the given name.
     * @param name The name of the profile.
     */
    public Profile(String name) {
        this.name = name;
        this.levelReached = 5;
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
     * Save the profile to a file.
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
        if (levelHighScores.size() < 10 || score > levelHighScores.peek().getScore()) {
            if (levelHighScores.size() == 10) {
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


    @Override
    public String toString() {
        return name;
    }
}