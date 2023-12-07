package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Exit extends Tile{
    private static final Image EXIT_IMAGE = new Image(Exit.class.getResourceAsStream("sprites/exit.png"));

    public Exit(int x, int y) {
        super("exit", x, y, false);
    }

    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(EXIT_IMAGE, x*size, y*size);
    }
}
