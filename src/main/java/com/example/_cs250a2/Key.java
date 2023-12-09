package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

enum KeyColour {RED, GREEN, BLUE, YELLOW};


public class Key extends Item {
    private static final Image KEY_IMAGE = new Image(Key.class.getResourceAsStream("sprites/key.png"));
    private KeyColour keyColour;

    public Key(int x, int y, char keyColourChar) {
        super("key", x, y);
        this.keyColour = getKeyColour(keyColourChar);
    }



    public KeyColour getKeyColour() {
        return keyColour;
    }

    private KeyColour getKeyColour(char keyColourChar) {
        return switch (keyColourChar) {
            case 'R' -> KeyColour.RED;
            case 'G' -> KeyColour.GREEN;
            case 'B' -> KeyColour.BLUE;
            case 'Y' -> KeyColour.YELLOW;
            default -> null;
        };
    }

    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        switch (this.keyColour) {
            case RED -> gc.drawImage(KEY_IMAGE, x * size, y * size);
            case GREEN -> gc.drawImage(KEY_IMAGE, x * size, y * size);
            case BLUE -> gc.drawImage(KEY_IMAGE, x * size, y * size);
            case YELLOW -> gc.drawImage(KEY_IMAGE, x * size, y * size);
        }

    }
}
