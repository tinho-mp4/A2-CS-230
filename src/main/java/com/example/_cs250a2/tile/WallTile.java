package com.example._cs250a2.tile;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class WallTile extends Tile {

    private static final Image WALL_IMAGE = new Image(WallTile.class.getResourceAsStream("/com/example/_cs250a2/frog.png"));
    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(WALL_IMAGE, x, y, size, size);

    }
    String getText() {
        return "wall";
    }
}