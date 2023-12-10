package com.example._cs250a2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ScoreEntry implements Serializable {
    private static final long serialVersionUID = 1L;
    private transient SimpleStringProperty profileNameProperty; // transient to exclude it from default serialization
    private transient SimpleIntegerProperty scoreProperty; // transient to exclude it from default serialization
    private final String profileName;
    private final int score;

    public ScoreEntry(String profileName, int score) {
        this.profileName = profileName;
        this.score = score;
        initializeProperties();
    }

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

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(profileNameProperty.get());
        out.writeObject(scoreProperty.get());
    }

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
        return "ScoreEntry{" +
                "profileName='" + profileName + '\'' +
                ", score=" + score +
                '}';
    }
}
