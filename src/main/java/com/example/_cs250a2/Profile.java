package com.example._cs250a2;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

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

    private int lastCompletedLevel;

    private final Map<String, Integer> levelScores;
    public static final int MAX_LEVEL = 5;

    /**
     * Creates a new profile with the given name.
     *
     * @param name The name of the profile.
     */
    public Profile(String name) {
        this.name = name;
        this.levelReached = 0;
        this.levelScores = new HashMap<>();
    }

    /**
     *  Create a new profile with the given name and load it from the given file.
     */
    public String getName() {
        return name;
    }

    public void nextLevel() {
        // Check if the current level is the one immediately following the last completed level
        if (lastCompletedLevel + 1 == levelReached) {
            levelReached++;
            lastCompletedLevel = levelReached - 1; // Update the last completed level
        }
    }

    /**
     * Get the highest level reached by the player.
     * @return The highest level reached by the player.
     */
    public int getLevelReached() {
        return levelReached;
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

    public void setLastCompletedLevel(int level) {
        lastCompletedLevel = level;
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