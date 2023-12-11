package com.example._cs250a2;

import java.io.*;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Represents an entry for a score in the game, including the profile name and the score.
 * This class supports JavaFX properties and is serializable for easy storage and retrieval.
 * @author Ben Foord
 * @version 1.0
 */
public class ScoreEntry implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    // Transient properties to exclude from default serialization
    private transient SimpleStringProperty profileNameProperty;
    private transient SimpleIntegerProperty scoreProperty;

    /** The profile name associated with this score entry. */
    private final String profileName;

    /** The score achieved by the profile. */
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

    /**
     * Gets the name of the profile associated with this score entry.
     *
     * @return The profile name.
     */
    public String getProfileName() {
        return profileName;
    }

    /**
     * Gets the score value associated with this score entry.
     *
     * @return The score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Provides the property wrapper for the profile name. This is useful for JavaFX bindings and UI updates.
     * The property is lazily initialized on first access to ensure it exists even after deserialization.
     *
     * @return A SimpleStringProperty object representing the profile name.
     */
    public SimpleStringProperty profileNameProperty() {
        if (profileNameProperty == null) {
            initializeProperties();
        }
        return profileNameProperty;
    }

    /**
     * Provides the property wrapper for the score. This is useful for JavaFX bindings and UI updates.
     * The property is lazily initialized on first access to ensure it exists even after deserialization.
     *
     * @return A SimpleIntegerProperty object representing the score.
     */
    public SimpleIntegerProperty scoreProperty() {
        if (scoreProperty == null) {
            initializeProperties();
        }
        return scoreProperty;
    }

    /**
     * Custom serialization method for ScoreEntry. Writes the default object state,
     * and then writes the additional properties for profile name and score.
     * This method is necessary to serialize JavaFX properties which are not serializable by default.
     *
     * @param out The ObjectOutputStream to write the object to.
     * @throws IOException If an I/O error occurs while writing the object.
     */
    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(profileNameProperty.get());
        out.writeObject(scoreProperty.get());
    }

    /**
     * Custom deserialization method for ScoreEntry. Reads the default object state,
     * and then reads the additional properties for profile name and score.
     * Initializes JavaFX properties after deserialization.
     *
     * @param in The ObjectInputStream to read the object from.
     * @throws IOException            If an I/O error occurs while reading the object.
     * @throws ClassNotFoundException If the class of a serialized object cannot be found.
     */
    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        String profileNameValue = (String) in.readObject();
        int scoreValue = (int) in.readObject();
        initializeProperties();
        profileNameProperty.set(profileNameValue);
        scoreProperty.set(scoreValue);
    }

    /**
     * Returns a string representation of the ScoreEntry, including the profile name and score.
     * Useful for logging and displaying the score entry in a text-based format.
     *
     * @return A string in the format "ScoreEntry{profileName='[profileName]', score=[score]}".
     */
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
