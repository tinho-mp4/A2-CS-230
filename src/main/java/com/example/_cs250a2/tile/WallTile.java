package com.example._cs250a2.tile;

import javafx.scene.canvas.GraphicsContext;

class WallTile extends Tile {
    @Override
    void draw(GraphicsContext gc, double x, double y, double size) {
        gc.setFill(javafx.scene.paint.Color.GRAY);
        gc.fillRect(x, y, size, size);
    }
    String getText() {
        return "wall";
    }
}