package com.example._cs250a2;

import java.util.List;

public class Profile {
    private String name;

    private int levelReached;

    public Profile(String name) {
        this.name = name;
        this.levelReached = 0;
    }



    public String getName() {
        return name;
    }

    public int getLevelReached() {
        return levelReached;
    }

    public void setLevelReached(int levelReached) {
        this.levelReached = levelReached;
    }

    @Override
    public String toString() {
        return name;
    }
}