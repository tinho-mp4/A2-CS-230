package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Dirt extends Tile{
    private static final Image DIRT_IMAGE = new Image(Dirt.class.getResourceAsStream("dirt.png"));

    public Dirt(int x, int y) {
        super("dirt",x, y);
    }

    public static void event(){}

    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(DIRT_IMAGE, x*size, y*size);
    }
}
