package com.example._cs250a2.tile;


import javafx.scene.canvas.GraphicsContext;

class ButtonTile extends Tile {

    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.setFill(javafx.scene.paint.Color.ORANGE);
        gc.fillRect(x, y, size, size);
        // Additional logic to use the button number as needed
    }
}