package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * The {@code Exit} class handles the exit tile.
 * @author Ben Foord
 * @version 1.0
 */
public class Exit extends Tile {
    /**
     * The image of the exit tile.
     */
    private static final Image EXIT_IMAGE
            = new Image(Exit.class.getResourceAsStream("sprites/exit.png"));

    /**
     * Creates a new exit tile.
     * @param x The X position of the exit tile
     * @param y The Y position of the exit tile
     */
    public Exit(final int x, final int y) {
        super("exit", x, y, false);
    }

    public static void event() {
    }


    /**
     * Draws the exit tile.
     * @param gc The graphics context
     * @param x The X position of the exit tile
     * @param y The Y position of the exit tile
     * @param size The size of the exit tile
     */
    @Override
    public void draw(final GraphicsContext gc,
                     final double x,
                     final double y,
                     final double size) {
        gc.drawImage(EXIT_IMAGE, x * size, y * size);
    }
}
