package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * The {@code Path} class represents a path block in the game
 * @author idk
 * @version 1.0
 */
public class Path extends Tile {
    private static final Image PATH_IMAGE = new Image(Path.class.getResourceAsStream("sprites/path.png"));

    public Path(int x, int y) {
        super("path",x, y, false);
        this.setPushableBlock(true);
    }


    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(PATH_IMAGE, x*size, y*size);
    }
}
