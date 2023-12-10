package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;

/**
 * The {@code Tile} abstract class represents a framework for all items in the game
 * @author idk
 * @version 1.0
 */
public abstract class Tile {
    protected String name;
    protected int x;
    protected int y;
    protected boolean solid;
    protected boolean pushableBlock;

    public Tile(String name, int x, int y, boolean solid) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.solid = solid;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    public boolean isPushableBlock() {
        return pushableBlock;
    }

    public void setPushableBlock(boolean pushableBlock) {
        this.pushableBlock = pushableBlock;
    }

    public String getName() {
        return this.name;
    }

    public abstract void draw(GraphicsContext gc, double x, double y, double size);
}
