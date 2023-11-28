package com.example._cs250a2.tile;

import javafx.scene.canvas.GraphicsContext;

class ChipSocketTile extends Tile {
    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.setFill(javafx.scene.paint.Color.LIGHTGRAY);
        gc.fillRect(x, y, size, size);
    }
}