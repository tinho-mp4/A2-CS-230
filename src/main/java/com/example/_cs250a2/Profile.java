package com.example._cs250a2;

import java.util.List;

public class Profile {
    private String name;

    public Profile(String name) {
        this.name = name;
    }



    public String getName() {
        return name;
    }

    public int levelReached;

    @Override
    public String toString() {
        return name;
    }
}