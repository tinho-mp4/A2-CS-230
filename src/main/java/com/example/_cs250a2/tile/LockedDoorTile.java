package com.example._cs250a2.tile;

import javafx.scene.canvas.GraphicsContext;

class LockedDoorTile extends Tile {
    @Override
    void draw(GraphicsContext gc, double x, double y, double size) {
        gc.setFill(javafx.scene.paint.Color.DARKGRAY);
        gc.fillRect(x, y, size, size);
    }
}
