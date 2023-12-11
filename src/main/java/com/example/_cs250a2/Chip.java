package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

/**
 * The {@code Chip} class represents a computer chip in the game. It extends the {@code Item} class,
 * inheriting its basic properties and functionalities. This class is specifically for handling the
 * appearance and behavior of the chip item within the game environment.
 *
 * @author Pele Mayle
 * @version 1.0
 */
public class Chip extends Item {
    /**
     * The image representation of the chip. This static final field holds the graphical representation
     * of the chip, used for rendering on the game canvas.
     */
    private static final Image CHIP_IMAGE =
            new Image(Objects.requireNonNull(Chip.class.getResourceAsStream("sprites/chip.png")));

    /**
     * Constructs a new Chip object at the specified coordinates.
     * The chip is initialized with the name "chip" and its position is set based on the provided x and y coordinates.
     *
     * @param x The x-coordinate where the chip is located.
     * @param y The y-coordinate where the chip is located.
     */
    public Chip(final int x, final int y) {
        super("chip", x, y);
    }

    /**
     * Draws the chip on the provided GraphicsContext. The chip is rendered at the specified
     * coordinates and scaled to the given size.
     *
     * @param gc   The GraphicsContext used for drawing the chip.
     * @param x    The x-coordinate on the canvas where the chip will be drawn.
     * @param y    The y-coordinate on the canvas where the chip will be drawn.
     * @param size The size to which the chip image will be scaled.
     */
    @Override
    public void draw(final GraphicsContext gc,
                     final double x,
                     final double y,
                     final double size) {
        gc.drawImage(CHIP_IMAGE, x * size, y * size);
    }
}
