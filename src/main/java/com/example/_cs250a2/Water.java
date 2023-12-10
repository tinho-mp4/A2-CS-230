package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

/**
 * Represents a water block in the game as a non-passable and pushable tile.
 * It utilizes a static image to render the water tile.
 */
public class Water extends Tile {
    private static final Image WATER_TILE = new Image(Objects.requireNonNull(Water.class.getResourceAsStream("sprites/water.png")));

    /**
     * Constructor to initialize a water tile with specific coordinates.
     *
     * @param x X-coordinate of the water block.
     * @param y Y-coordinate of the water block.
     */
    public Water(int x, int y) {
        super("water", x, y, false);
        this.setPushableBlock(true);
    }

    /**
     * Draws the water tile on the canvas.
     *
     * @param gc   GraphicsContext for drawing.
     * @param x    X-coordinate for drawing.
     * @param y    Y-coordinate for drawing.
     * @param size Size of the tile.
     */
    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(WATER_TILE, x * size, y * size);
    }
}
