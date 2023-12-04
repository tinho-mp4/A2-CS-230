package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Wall extends Tile{
    private static final Image WALL_IMAGE = new Image(Wall.class.getResourceAsStream("wall.png"));

    public Wall(int x, int y) {
        super("wall",x,y);
    }

    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(WALL_IMAGE, x*size, y*size);
    }
}
