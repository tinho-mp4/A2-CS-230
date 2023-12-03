package com.example._cs250a2;

public class Tile {
    private String name;
    private int x;
    private int y;
    private boolean solid;

    public Tile(String name, int x, int y, boolean isSolid) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.solid = isSolid;
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

    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean isSolid) {
        this.solid = isSolid;
    }

    public String getName() {
        return name;
    }
}
