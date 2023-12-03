package com.example._cs250a2.tile;

import javafx.scene.canvas.GraphicsContext;

public class ComputerChipTile extends Tile {
    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.setFill(javafx.scene.paint.Color.SILVER);
        gc.fillRect(x, y, size, size);
    }
}
