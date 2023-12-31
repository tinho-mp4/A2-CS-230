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
 * @version 1.0
 */
public class Profile implements Serializable {

    /** The name of the player associated with this profile. */
    private final String name;

    /** The highest level the player has reached in the game. */
    private int levelReached;


    /** The number representing the last level the player successfully completed in the game. */
    private int lastCompletedLevel;

    /** A map storing the player's scores for each level. */
    private final Map<String, Integer> levelScores;


    /**
     * Creates a new profile with the given name.
     *
     * @param name The name of the profile.
     */
    public Profile(String name) {
        this.name = name;
        this.levelReached = 5;
        this.levelScores = new HashMap<>();
    }

    /**
     *  Create a new profile with the given name and load it from the given file.
     *
     * @return The name of the profile.
     */
    public String getName() {
        return name;
    }

    /**
     * Advances the player's progress to the next level. This method increments the level reached by the player.
     * It should be called when the player successfully completes a level to update their progress.
     */
    public void nextLevel() {
        levelReached++;
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
     *
     * @param level The level to get the score for.
     * @return The score for the given level.
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