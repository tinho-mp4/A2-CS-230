package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Path extends Tile {
    private static final Image PATH_IMAGE = new Image(Path.class.getResourceAsStream("sprites/path.png"));

    public Path(int x, int y) {
        super("path",x, y, false);
    }


    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(PATH_IMAGE, x*size, y*size);
    }
}
