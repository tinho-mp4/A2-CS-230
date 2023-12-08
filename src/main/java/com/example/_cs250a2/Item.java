package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;

public abstract class Item {
    protected String name;
    protected int x;
    protected int y;

    public Item(String name, int x, int y) {
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
        return this.name;
    }

    public abstract void draw(GraphicsContext gc, double x, double y, double size);
}
