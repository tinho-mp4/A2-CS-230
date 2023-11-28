package com.example._cs250a2.tile;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

class WaterTile extends Tile {
    private static final Image WATER_TILE = new Image(WaterTile.class.getResourceAsStream("/com/example/_cs250a2/Water.png"));
    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(WATER_TILE, x, y, size, size);
    }

    String getText() {
        return "water";
    }
}


