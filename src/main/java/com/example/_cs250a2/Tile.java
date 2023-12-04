package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;

public abstract class Tile {
    protected String name;
    protected int x;
    protected int y;
    protected boolean solid;

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

    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean isSolid) {
        this.solid = isSolid;
    }

    public String getName() {
        return this.name;
    }

    public Corner getBlockedCorner() {
        return Corner.TOP_LEFT;
    }

    public abstract void draw(GraphicsContext gc, double x, double y, double size);
}
