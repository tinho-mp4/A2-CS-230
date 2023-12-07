package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

enum DoorType {RED, GREEN, BLUE, YELLOW};

public class LockedDoor extends Tile {
    private static final Image RED_LOCKED_DOOR_IMAGE = new Image(LockedDoor.class.getResourceAsStream("sprites/redLockedDoor.png"));
    private static final Image BLUE_LOCKED_DOOR_IMAGE = new Image(LockedDoor.class.getResourceAsStream("sprites/blueLockedDoor.png"));
    private static final Image GREEN_LOCKED_DOOR_IMAGE = new Image(LockedDoor.class.getResourceAsStream("sprites/greenLockedDoor.png"));
    private static final Image YELLOW_LOCKED_DOOR_IMAGE = new Image(LockedDoor.class.getResourceAsStream("sprites/yellowLockedDoor.png"));

    private DoorType doorType;

    public LockedDoor(int x, int y, char doorTypeChar) {
        super("lockedDoor", x, y, true);
        this.doorType = getDoorType(doorTypeChar);
    }

    public static void event(ArrayList<Item> inventory) {
    }

    private DoorType getDoorType(char doorTypeChar) {
        switch (doorTypeChar) {
            case '0':
                return DoorType.RED;
            case '1':
                return DoorType.GREEN;
            case '2':
                return DoorType.BLUE;
            case '3':
                return DoorType.YELLOW;
            default:
                return DoorType.RED;
        }
    }


    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        switch (this.doorType) {
            case RED:
                gc.drawImage(RED_LOCKED_DOOR_IMAGE, x * size, y * size);
                break;
            case GREEN:
                gc.drawImage(GREEN_LOCKED_DOOR_IMAGE, x * size, y * size);
                break;
            case BLUE:
                gc.drawImage(BLUE_LOCKED_DOOR_IMAGE, x * size, y * size);
                break;
            case YELLOW:
                gc.drawImage(YELLOW_LOCKED_DOOR_IMAGE, x * size, y * size);
                break;
        }
    }
}
