package com.example._cs250a2;

import java.io.*;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Represents an entry for a score in the game, including the profile name and the score.
 * This class supports JavaFX properties and is serializable for easy storage and retrieval.
 */
public class ScoreEntry implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    // Transient properties to exclude from default serialization
    private transient SimpleStringProperty profileNameProperty;
    private transient SimpleIntegerProperty scoreProperty;

    // Fields for score entry data
    private final String profileName;
    private final int score;

    /**
     * Constructs a ScoreEntry with a profile name and a score.
     *
     * @param profileName Name of the profile.
     * @param score       Score achieved.
     */
    public ScoreEntry(String profileName, int score) {
        this.profileName = profileName;
        this.score = score;
        initializeProperties();
    }

    /**
     * Initializes the JavaFX properties.
     */
    private void initializeProperties() {
        this.profileNameProperty = new SimpleStringProperty(profileName);
        this.scoreProperty = new SimpleIntegerProperty(score);
    }

    public String getProfileName() {
        return profileName;
    }

    public int getScore() {
        return score;
    }

    public SimpleStringProperty profileNameProperty() {
        if (profileNameProperty == null) {
            initializeProperties();
        }
        return profileNameProperty;
    }

    public SimpleIntegerProperty scoreProperty() {
        if (scoreProperty == null) {
            initializeProperties();
        }
        return scoreProperty;
    }

    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(profileNameProperty.get());
        out.writeObject(scoreProperty.get());
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        String profileNameValue = (String) in.readObject();
        int scoreValue = (int) in.readObject();
        initializeProperties();
        profileNameProperty.set(profileNameValue);
        scoreProperty.set(scoreValue);
    }

    @Override
    public String toString() {
        return "ScoreEntry{"
                +
                "profileName='"
                + profileName + '\''
                +
                ", score=" + score
                +
                '}';
    }
}
