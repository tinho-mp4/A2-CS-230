package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * The {@code LockedDoor} class represents a locked door in the game
 * @author idk
 * @version 1.0
 */

public class LockedDoor extends Tile {
    private static final Image RED_LOCKED_DOOR_IMAGE = new Image(LockedDoor.class.getResourceAsStream("sprites/redLockedDoor.png"));
    private static final Image BLUE_LOCKED_DOOR_IMAGE = new Image(LockedDoor.class.getResourceAsStream("sprites/blueLockedDoor.png"));
    private static final Image GREEN_LOCKED_DOOR_IMAGE = new Image(LockedDoor.class.getResourceAsStream("sprites/greenLockedDoor.png"));
    private static final Image YELLOW_LOCKED_DOOR_IMAGE = new Image(LockedDoor.class.getResourceAsStream("sprites/yellowLockedDoor.png"));

    private final DoorColour doorType;

    public LockedDoor(int x, int y, char doorTypeChar) {
        super("lockedDoor", x, y, true);
        this.doorType = getDoorType(doorTypeChar);
    }

    public void event(ArrayList<Item> inventory) {
        if (correctKey(inventory)) {
            LevelLoader.setTile(getX(), getY(), new Path(getX(), getY()));
        }
    }

    private boolean correctKey(ArrayList<Item> inventory){
        for (Item item : inventory) {
            if (item instanceof Key key) {
                if (key.getColour() == this.getColour()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void checkUnlock(ArrayList<Item> inventory) {
        if(correctKey(inventory)) {
            setSolid(false);
        }
    }





    private DoorColour getDoorType() {
        return doorType;
    }

    private DoorColour getDoorType(char doorTypeChar) {
        switch (doorTypeChar) {
            case '0':
                return DoorColour.RED;
            case '1':
                return DoorColour.GREEN;
            case '2':
                return DoorColour.BLUE;
            case '3':
                return DoorColour.YELLOW;
            default:
                return DoorColour.RED;
        }
    }

    public DoorColour getColour() {
        return this.doorType;
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
