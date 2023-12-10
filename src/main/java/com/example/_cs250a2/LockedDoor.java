package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a locked door in the game.
 * This class extends {@code Tile} to manage the properties and rendering of locked doors.
 *
 * @author Pele
 */
public class LockedDoor extends Tile {
    private static final Image RED_LOCKED_DOOR_IMAGE =
            new Image(Objects.requireNonNull(LockedDoor.class.getResourceAsStream("sprites/redLockedDoor.png")));
    private static final Image BLUE_LOCKED_DOOR_IMAGE =
            new Image(Objects.requireNonNull(LockedDoor.class.getResourceAsStream("sprites/blueLockedDoor.png")));
    private static final Image GREEN_LOCKED_DOOR_IMAGE =
            new Image(Objects.requireNonNull(LockedDoor.class.getResourceAsStream("sprites/greenLockedDoor.png")));
    private static final Image YELLOW_LOCKED_DOOR_IMAGE =
            new Image(Objects.requireNonNull(LockedDoor.class.getResourceAsStream("sprites/yellowLockedDoor.png")));

    /**
     * The color type of the locked door.
     */
    private final DoorColour doorType;

    /**
     * Constructs a LockedDoor at specified coordinates with a specific door type.
     *
     * @param x           X-coordinate of the locked door.
     * @param y           Y-coordinate of the locked door.
     * @param doorTypeChar Character representing the door type (color).
     */
    public LockedDoor(int x, int y, char doorTypeChar) {
        super("lockedDoor", x, y, true);
        this.doorType = getDoorType(doorTypeChar);
    }

    /**
     * Triggers an event based on the inventory items.
     * If the correct key is present in the inventory, changes the locked door to a path.
     *
     * @param inventory The player's inventory.
     */
    public void event(ArrayList<Item> inventory) {
        if (correctKey(inventory)) {
            LevelLoader.setTile(getX(), getY(), new Path(getX(), getY()));
        }
    }

    /**
     * Checks if the correct key is in the player's inventory.
     *
     * @param inventory The player's inventory.
     * @return true if the correct key is present, false otherwise.
     */
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

    /**
     * Unlocks the door if the correct key is present in the inventory.
     *
     * @param inventory The player's inventory.
     */
    public void checkUnlock(ArrayList<Item> inventory) {
        if (correctKey(inventory)) {
            setSolid(false);
        }
    }

    /**
     * Determines the door color based on the provided character.
     *
     * @param doorTypeChar A character representing the door type.
     * @return The corresponding {@code DoorColour} enumeration value.
     */
    private DoorColour getDoorType(char doorTypeChar) {
        return switch (doorTypeChar) {
            case '1' -> DoorColour.GREEN;
            case '2' -> DoorColour.BLUE;
            case '3' -> DoorColour.YELLOW;
            default -> DoorColour.RED;
        };
    }

    /**
     * Gets the door color type of this locked door.
     *
     * @return The {@code DoorColour} of the locked door.
     */
    public DoorColour getColour() {
        return this.doorType;
    }

    /**
     * Draws the locked door with the corresponding image based on its color.
     *
     * @param gc   GraphicsContext to draw on.
     * @param x    X-coordinate for drawing.
     * @param y    Y-coordinate for drawing.
     * @param size Size of the locked door image.
     */
    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        switch (this.doorType) {
            case RED -> gc.drawImage(RED_LOCKED_DOOR_IMAGE, x * size, y * size);
            case GREEN -> gc.drawImage(GREEN_LOCKED_DOOR_IMAGE, x * size, y * size);
            case BLUE -> gc.drawImage(BLUE_LOCKED_DOOR_IMAGE, x * size, y * size);
            case YELLOW -> gc.drawImage(YELLOW_LOCKED_DOOR_IMAGE, x * size, y * size);
            default -> {} // Default case added
        }
    }
}
