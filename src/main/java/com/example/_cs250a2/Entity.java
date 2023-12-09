package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;

public abstract class Entity {

    protected int x;

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

    protected int y;

    public int[] getLocation() {
        return location;
    }

    public void setLocation(int[] location) {
        this.location = location;
    }

    protected int[] location = {x, y};

    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
        this.location = new int[]{x, y};
    }

    public abstract void event(int x, int y, int newX, int newY);

    public abstract void draw(GraphicsContext gc, double x, double y, double size);
}
