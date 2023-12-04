package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Door extends Tile{
    private static final Image DOOR_IMAGE = new Image(Door.class.getResourceAsStream("door.png"));

    public Door(int x, int y) {
        super("door", x, y, true);
    }

    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(DOOR_IMAGE, x*size, y*size);
    }
}
