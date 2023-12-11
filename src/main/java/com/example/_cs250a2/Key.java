package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

/**
 * Enum representing the color of a door in the game.
 */
enum DoorColour {RED, GREEN, BLUE, YELLOW}

/**
 * Represents a key item in the game, with a specific color associated with a door.
 *
 * @author Pele
 */
public class Key extends Item {
    private static final Image BLUE_KEY_IMAGE =
            new Image(Objects.requireNonNull(Key.class.getResourceAsStream("sprites/blueKey.png")));
    private static final Image RED_KEY_IMAGE =
            new Image(Objects.requireNonNull(Key.class.getResourceAsStream("sprites/redKey.png")));
    private static final Image YELLOW_KEY_IMAGE =
            new Image(Objects.requireNonNull(Key.class.getResourceAsStream("sprites/yellowKey.png")));
    private static final Image GREEN_KEY_IMAGE =
            new Image(Objects.requireNonNull(Key.class.getResourceAsStream("sprites/greenKey.png")));
    private final DoorColour colour;
    private DoorColour doorColour;

    /**
     * Constructs a Key object with specified coordinates and color.
     *
     * @param x The x-coordinate of the key.
     * @param y The y-coordinate of the key.
     * @param keyColourChar Character representing the key's color.
     */
    public Key(int x, int y, char keyColourChar) {
        super("key", x, y);
        this.colour = getKeyColour(keyColourChar);
    }

    /**
     * Returns the color of the key.
     *
     * @return The DoorColour of the key.
     */
    public DoorColour getColour() {
        return this.colour;
    }

    /**
     * Determines the DoorColour based on the provided character.
     *
     * @param keyColourChar Character representing the color.
     * @return The corresponding DoorColour.
     */
    private DoorColour getKeyColour(char keyColourChar) {
        return switch (keyColourChar) {
            case 'R' -> DoorColour.RED;
            case 'G' -> DoorColour.GREEN;
            case 'B' -> DoorColour.BLUE;
            case 'Y' -> DoorColour.YELLOW;
            default -> null;
        };
    }

    /**
     * Draws the key on the canvas at specified coordinates and size.
     *
     * @param gc GraphicsContext for drawing.
     * @param x X-coordinate for drawing the key.
     * @param y Y-coordinate for drawing the key.
     * @param size Size for drawing the key.
     */
    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        switch (this.colour) {
            case RED -> gc.drawImage(RED_KEY_IMAGE, x * size, y * size);
            case GREEN -> gc.drawImage(GREEN_KEY_IMAGE, x * size, y * size);
            case BLUE -> gc.drawImage(BLUE_KEY_IMAGE, x * size, y * size);
            case YELLOW -> gc.drawImage(YELLOW_KEY_IMAGE, x * size, y * size);
            default -> {}
        }

    }

    /**
     * Returns a string representation of the key.
     *
     * @return String describing the key's attributes.
     */
    @Override
    public String toString() {
        return "Key{"
                + "name='" + getName() + '\''
                + ", x=" + getX()
                + ", y=" + getY()
                + ", color=" + colour
                + '}';
    }
}
