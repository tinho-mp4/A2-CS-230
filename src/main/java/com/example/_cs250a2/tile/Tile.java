package com.example._cs250a2.tile;

import javafx.scene.canvas.GraphicsContext;

public abstract class Tile {

    public boolean solid;

    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

//    public Tile (boolean solid) {
//        this.solid = solid;
//    }

    public abstract void draw(GraphicsContext gc, double x, double y, double size);
}






















