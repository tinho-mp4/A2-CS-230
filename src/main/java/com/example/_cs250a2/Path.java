package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.Objects;

/**
 * Represents a path block in the game.
 * This class extends {@code Tile} to manage the properties and rendering of path blocks.
 *
 * @author Evans
 */
public class Path extends Tile {
    private static final Image PATH_IMAGE =
            new Image(Objects.requireNonNull(Path.class.getResourceAsStream("sprites/path.png")));

    /**
     * Constructs a Path tile at specified coordinates.
     *
     * @param x X-coordinate of the path.
     * @param y Y-coordinate of the path.
     */
    public Path(int x, int y) {
        super("path", x, y, false);
        this.setPushableBlock(true);
    }

    /**
     * Draws the path tile on the given graphics context at specified coordinates and size.
     *
     * @param gc   GraphicsContext to draw on.
     * @param x    X-coordinate for drawing.
     * @param y    Y-coordinate for drawing.
     * @param size Size of the path image.
     */
    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(PATH_IMAGE, x * size, y * size);
    }
}
