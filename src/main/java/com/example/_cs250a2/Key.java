package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Key extends Item {
    private static final Image KEY_IMAGE = new Image(Key.class.getResourceAsStream("key.png"));

    public Key(int x, int y) {
        super("key", x, y, false);
    }

    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(KEY_IMAGE, x*size, y*size);
    }
}
