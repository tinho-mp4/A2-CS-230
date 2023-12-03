package com.example._cs250a2;

public class Tile {
    private String name;
    private int x;
    private int y;

    public Tile(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }
}
