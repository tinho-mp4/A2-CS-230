package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Water extends Tile{
    private static final Image WATER_TILE = new Image(Water.class.getResourceAsStream("sprites/water.png"));

    public Water(int x, int y) {
        super("water",x, y, false);
    }

    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(WATER_TILE, x*size, y*size);
    }
}
