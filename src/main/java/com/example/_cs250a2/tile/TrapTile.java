package com.example._cs250a2.tile;

import javafx.scene.canvas.GraphicsContext;

class TrapTile extends Tile {
    private int trapNumber;

    public TrapTile(int trapNumber) {
        this.trapNumber = trapNumber;
    }


    @Override
    void draw(GraphicsContext gc, double x, double y, double size) {
        gc.setFill(javafx.scene.paint.Color.RED);
        gc.fillRect(x, y, size, size);
        // Additional logic to use the number (n) associated with the trap
    }
}