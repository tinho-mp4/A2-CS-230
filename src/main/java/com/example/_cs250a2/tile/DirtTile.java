package com.example._cs250a2.tile;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class DirtTile extends Tile {

    private static final Image DIRT_IMAGE = new Image(DirtTile.class.getResourceAsStream("/com/example/_cs250a2/dirt.png"));

    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(DIRT_IMAGE, x, y, size, size);
    }

    String getText() {
        return "dirt";
    }
}
