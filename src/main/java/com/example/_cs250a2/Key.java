package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

enum KeyType { RED, GREEN, BLUE, YELLOW }

public class Key extends Item {
    private static final Image RED_KEY_IMAGE = new Image(Objects.requireNonNull(Key.class.getResourceAsStream("sprites/redKey.png")));
    private static final Image GREEN_KEY_IMAGE = new Image(Objects.requireNonNull(Key.class.getResourceAsStream("sprites/greenKey.png")));
    private static final Image BLUE_KEY_IMAGE = new Image(Objects.requireNonNull(Key.class.getResourceAsStream("sprites/blueKey.png")));
    private static final Image YELLOW_KEY_IMAGE = new Image(Objects.requireNonNull(Key.class.getResourceAsStream("sprites/yellowKey.png")));

    private final KeyType keyType;

    public Key(int x, int y, char keyTypeChar) {
        super("key", x, y);
        this.keyType = getKeyType(keyTypeChar);
    }

    private KeyType getKeyType(char keyTypeChar) {
        switch (keyTypeChar) {
            case 'G':
                return KeyType.GREEN;
            case 'B':
                return KeyType.BLUE;
            case 'Y':
                return KeyType.YELLOW;
            case 'R':
                return KeyType.RED;
            default:
                System.out.println("Unknown key type: " + keyTypeChar);
                return KeyType.RED;
        }
    }


    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        switch (this.keyType) {
            case RED -> gc.drawImage(RED_KEY_IMAGE, x * size, y * size);
            case GREEN -> gc.drawImage(GREEN_KEY_IMAGE, x * size, y * size);
            case BLUE -> gc.drawImage(BLUE_KEY_IMAGE, x * size, y * size);
            case YELLOW -> gc.drawImage(YELLOW_KEY_IMAGE, x * size, y * size);
        }
    }
}
