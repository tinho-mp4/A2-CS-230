package com.example._cs250a2.tile;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

class BlockTile extends Tile {
    private static final Image BLOCK_IMAGE = new Image(BlockTile.class.getResourceAsStream("com/example/_cs250a2/Block.png"));
    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(BLOCK_IMAGE, x, y, size, size);
    }
}