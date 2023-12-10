package com.example._cs250a2;

import java.io.Serializable;

/**
 * The {@code ScoreEntry} class represents an entry in the high scores list,
 * containing the profile name and the corresponding score achieved.
 *
 * @version 1.0
 * @author Ben Foord
 */
public class ScoreEntry implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String profileName;
    private final int score;

    /**
     * Constructs a new ScoreEntry with the given profile name and score.
     *
     * @param profileName The name of the profile associated with the score.
     * @param score The score achieved.
     */
    public ScoreEntry(String profileName, int score) {
        this.profileName = profileName;
        this.score = score;
    }

    /**
     * Gets the profile name associated with the score entry.
     *
     * @return The profile name.
     */
    public String getProfileName() {
        return profileName;
    }

    /**
     * Gets the score associated with the score entry.
     *
     * @return The score.
     */
    public int getScore() {
        return score;
    }
}
