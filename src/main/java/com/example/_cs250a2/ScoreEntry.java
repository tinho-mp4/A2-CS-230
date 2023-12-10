package com.example._cs250a2;


public class ScoreEntry {
    private String profileName;
    private int score;

    public ScoreEntry(String profileName, int score) {
        this.profileName = profileName;
        this.score = score;
    }

    public String getProfileName() {
        return profileName;
    }

    public int getScore() {
        return score;
    }
}
