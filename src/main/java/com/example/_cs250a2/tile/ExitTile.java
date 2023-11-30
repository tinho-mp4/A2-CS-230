package com.example._cs250a2.tile;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

class ExitTile extends Tile {
    private static final Image EXIT_IMAGE = new Image(DirtTile.class.getResourceAsStream("/com/example/_cs250a2/exit.png"));

    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(EXIT_IMAGE, x, y, size, size);
    }
    String getText() {
        return "exit";
    }
}