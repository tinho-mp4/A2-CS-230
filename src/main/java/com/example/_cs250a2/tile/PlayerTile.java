package com.example._cs250a2.tile;

import javafx.scene.canvas.GraphicsContext;

class PlayerTile extends Tile {
    @Override
    void draw(GraphicsContext gc, double x, double y, double size) {
        gc.setFill(javafx.scene.paint.Color.PLUM);
        gc.fillRect(x, y, size, size);
    }
}