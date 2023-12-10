package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * The {@code Chip} class represents the computer chip in the game.
 * @author Mwenya Sikazwe
 * @version 1.0
 */
public class Chip extends Item {
    /**
     * image of chip.
     */
    private static final Image CHIP_IMAGE =
    new Image(Chip.class.getResourceAsStream("sprites/chip.png"));

    /**
     * constructor.
     * @param x x
     * @param y y
     */
    public Chip(final int x, final int y) {
        super("chip", x, y);
    }


    /**
     * draws the chip.
     * @param gc graphics context
     * @param x x cooridnate
     * @param y y coordinate
     * @param size size
     */
    @Override
    public void draw(final GraphicsContext gc,
                     final double x,
                     final double y,
                     final double size) {
        gc.drawImage(CHIP_IMAGE, x * size, y * size);
    }
}
