package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The {@code ChipSocket} class represents a chip socket in the game.
 * @author Pele Mayle
 * @version 1.0
 */
public class ChipSocket extends Tile {
    /**
     * The number of chips needed to unlock the socket.
     */
    private final int CHIPS_NEEDED;
    /**
     * The image of the chip socket tile 1.
     */
    private static final Image CHIP_SOCKET1_IMAGE =
            new Image(Objects.requireNonNull(ChipSocket.class.getResourceAsStream(
                    "sprites/chipSocket1.png")));
    /**
     * The image of the chip socket tile 2.
     */
    private static final Image CHIP_SOCKET2_IMAGE =
            new Image(Objects.requireNonNull(ChipSocket.class.getResourceAsStream(
                    "sprites/chipSocket2.png")));
    /**
     * The image of the chip socket tile 3.
     */
    private static final Image CHIP_SOCKET3_IMAGE =
            new Image(Objects.requireNonNull(ChipSocket.class.getResourceAsStream(
                    "sprites/chipSocket3.png")));
    /**
     * The image of the chip socket tile 4.
     */
    private static final Image CHIP_SOCKET4_IMAGE =
            new Image(Objects.requireNonNull(ChipSocket.class.getResourceAsStream(
                    "sprites/chipSocket4.png")));
    /**
     * The image of the chip socket tile 5.
     */
    private static final Image CHIP_SOCKET5_IMAGE =
            new Image(Objects.requireNonNull(ChipSocket.class.getResourceAsStream(
                    "sprites/chipSocket5.png")));
    /**
     * The image of the chip socket tile 6.
     */
    private static final Image CHIP_SOCKET6_IMAGE =
            new Image(Objects.requireNonNull(ChipSocket.class.getResourceAsStream(
                    "sprites/chipSocket6.png")));
    /**
     * The image of the chip socket tile 7.
     */
    private static final Image CHIP_SOCKET7_IMAGE =
            new Image(Objects.requireNonNull(ChipSocket.class.getResourceAsStream(
                    "sprites/chipSocket7.png")));
    /**
     * The image of the chip socket tile 8.
     */
    private static final Image CHIP_SOCKET8_IMAGE =
            new Image(Objects.requireNonNull(ChipSocket.class.getResourceAsStream(
                    "sprites/chipSocket8.png")));
    /**
     * The image of the chip socket tile 9.
     */
    private static final Image CHIP_SOCKET9_IMAGE =
            new Image(Objects.requireNonNull(ChipSocket.class.getResourceAsStream(
                    "sprites/chipSocket9.png")));





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
     * Removes the required chips out of the players inventory.
     * Transforms the chip socket into a path.
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
     * Checks if the player has enough chips in inventory.
     * @param inventory player's Inventory.
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
    public void draw(GraphicsContext gc, double x, double y, double size) {
        Image chipSocketImage = null;
        switch (CHIPS_NEEDED) {
            case 1:
                chipSocketImage = CHIP_SOCKET1_IMAGE;
                break;
            case 2:
                chipSocketImage = CHIP_SOCKET2_IMAGE;
                break;
            case 3:
                chipSocketImage = CHIP_SOCKET3_IMAGE;
                break;
            case 4:
                chipSocketImage = CHIP_SOCKET4_IMAGE;
                break;
            case 5:
                chipSocketImage = CHIP_SOCKET5_IMAGE;
                break;
            case 6:
                chipSocketImage = CHIP_SOCKET6_IMAGE;
                break;
            case 7:
                chipSocketImage = CHIP_SOCKET7_IMAGE;
                break;
            case 8:
                chipSocketImage = CHIP_SOCKET8_IMAGE;
                break;
            case 9:
                chipSocketImage = CHIP_SOCKET9_IMAGE;
                break;
            default:
                break;

        }

        gc.drawImage(chipSocketImage, x*size, y*size);
    }
}
