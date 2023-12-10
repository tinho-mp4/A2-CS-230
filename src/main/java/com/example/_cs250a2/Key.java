package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * The {@code Key} class represents a key in the game
 * @author idk
 * @version 1.0
 */
enum DoorColour {RED, GREEN, BLUE, YELLOW};


public class Key extends Item {
    private static final Image BLUE_KEY_IMAGE = new Image(Key.class.getResourceAsStream("sprites/blueKey.png"));
    private static final Image RED_KEY_IMAGE = new Image(Key.class.getResourceAsStream("sprites/redKey.png"));
    private static final Image YELLOW_KEY_IMAGE = new Image(Key.class.getResourceAsStream("sprites/yellowKey.png"));
    private static final Image GREEN_KEY_IMAGE = new Image(Key.class.getResourceAsStream("sprites/greenKey.png"));
    private DoorColour colour;
    private DoorColour doorColour;

    public Key(int x, int y, char keyColourChar) {
        super("key", x, y);
        this.colour = getKeyColour(keyColourChar);
    }

    public DoorColour getColour() {
        return this.colour;
    }




    private DoorColour getKeyColour(char keyColourChar) {
        return switch (keyColourChar) {
            case 'R' -> DoorColour.RED;
            case 'G' -> DoorColour.GREEN;
            case 'B' -> DoorColour.BLUE;
            case 'Y' -> DoorColour.YELLOW;
            default -> null;
        };
    }




    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        switch (this.colour) {
            case RED -> gc.drawImage(RED_KEY_IMAGE, x * size, y * size);
            case GREEN -> gc.drawImage(GREEN_KEY_IMAGE, x * size, y * size);
            case BLUE -> gc.drawImage(BLUE_KEY_IMAGE, x * size, y * size);
            case YELLOW -> gc.drawImage(YELLOW_KEY_IMAGE, x * size, y * size);
        }

    }

    @Override
    public String toString() {
        return "Key{" +
                "name='" + getName() + '\'' +
                ", x=" + getX() +
                ", y=" + getY() +
                ", color=" + colour +
                '}';
    }
}
