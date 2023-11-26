package com.example._cs250a2.tile;

import javafx.scene.canvas.GraphicsContext;

/**
 * Enumeration list for each corner of the wall on an ice block
 */
enum Corner {TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT};
class IceTile extends Tile {
    /**
     * Instantiation of the corner variable
     */
    private Corner blockedCorner;

    /**
     * Sets the variable for the corner of which a wall is placed
     * @param blockedCorner the blocked corner enum variable (can be null)
     */
    public IceTile(Corner blockedCorner) {
        this.blockedCorner = blockedCorner;
    }

    @Override
    void draw(GraphicsContext gc, double x, double y, double size) {
        gc.setFill(javafx.scene.paint.Color.LIGHTBLUE);
        gc.fillRect(x, y, size, size);
    }
    String getText() {
        return "ice";
    }
}