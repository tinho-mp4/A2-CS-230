package com.example._cs250a2.tile;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class PathTile extends Tile {


    Private static final Image PATH_IMAGE = new Image(BlockTile.class.getResourceAsStream("/com/example/_cs250a2/Block.png"));


    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(PATH_IMAGE, x, y, size, size);
    }
    String getText() {
        return "path";
    }
}