package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class LockedDoor extends Tile {
    //TODO: Change key.png to lockedDoor.png
    private static final Image LOCKED_DOOR_IMAGE = new Image(Key.class.getResourceAsStream("key.png"));

    public LockedDoor(int x, int y) {
        super("lockedDoor", x, y, true);
    }
    public static void event(ArrayList<String> inventory) {
    }

    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(LOCKED_DOOR_IMAGE, x*size, y*size);
    }
}
