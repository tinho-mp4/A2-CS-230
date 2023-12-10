package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The {@code ChipSocket} class represents a chip socket in the game.
 * @author Mwenya Sikazwe
 * @version 1.0
 */
public class ChipSocket extends Tile {
    private final int CHIPS_NEEDED;
    private static final Image CHIP_SOCKET_IMAGE =
    new Image(ChipSocket.class.getResourceAsStream("sprites/chipSocket.png"));
    //other chips sprites.

    /**
     * Creates a computer chip at coordinates (x,y).
     * @param chipsRequired how many chips needed.
     * @param x x coordinate.
     * @param y y coordinate.
     */
    public ChipSocket(
            final int chipsRequired,
            final int x,
            final int y) {
        super("chipSocket", x, y, true);
        this.CHIPS_NEEDED = chipsRequired;
    }

    /**
     * Resets all the locks.
     */
    public static void resetAllLocks() {
        for (ArrayList<Tile> row : LevelLoader.getTileGrid()) {
            for (Tile t : row) {
                if (t instanceof ChipSocket socket) {
                    socket.setSolid(true);
                }
            }
        }
    }

    /**
     * Event that happens when player is on the chip socket.
     * @param inventory player's inventory.
     */
    public void event(final ArrayList<Item> inventory) {
        if (enoughChips(inventory)) {
            LevelLoader.setTile(getX(), getY(), new Path(getX(), getY()));
            AtomicInteger removeChips = new AtomicInteger(CHIPS_NEEDED);
            inventory.removeIf(item -> {
                if (item instanceof Chip && removeChips.get() > 0) {
                    removeChips.getAndDecrement();
                    return true;
                }
                return false;
            });
        }
    }

    /**
     * checks if the player has enough chips in inventory.
     * @param inventory player's Inventory
     * @return true if the player has enough chips, otherwise false.
     */
    private boolean enoughChips(final ArrayList<Item> inventory) {
        int chipCount = 0;
        for (Item item : inventory) {
            if (item instanceof Chip) {
                chipCount++;
            }
        }
        return chipCount >= CHIPS_NEEDED;
    }

    /**
     * Checks if the player has enough chips to unlock.
     * @param inventory player's inventory.
     */
    public void checkUnlock(final ArrayList<Item> inventory) {
        if (enoughChips(inventory)) {
            setSolid(false);
        }
    }

    /**
     * Draws the chip socket.
     * @param gc graphics context
     * @param x x coordinate
     * @param y y coordinate
     * @param size size
     */
    @Override
    public void draw(final GraphicsContext gc,
                     final double x,
                     final double y,
                     final double size) {
        gc.drawImage(CHIP_SOCKET_IMAGE, x * size, y * size);
    }
}
