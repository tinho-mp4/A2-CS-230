package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

/**
 * Represents a wall block in the game.
 * This class extends {@code Tile} and uses an image to render the wall.
 */
public class Wall extends Tile {
    private static final Image WALL_IMAGE = new Image(Objects.requireNonNull(Wall.class.getResourceAsStream("sprites/wall.png")));

    /**
     * Initializes a wall tile at specified coordinates.
     * The wall is set as non-passable.
     *
     * @param x X-coordinate of the wall.
     * @param y Y-coordinate of the wall.
     */
    public Wall(int x, int y) {
        super("wall", x, y, true);
    }

    /**
     * Draws the wall tile on the canvas.
     *
     * @param gc   GraphicsContext for drawing.
     * @param x    X-coordinate for drawing.
     * @param y    Y-coordinate for drawing.
     * @param size Size of the tile.
     */
    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(WALL_IMAGE, x * size, y * size);
    }
}
