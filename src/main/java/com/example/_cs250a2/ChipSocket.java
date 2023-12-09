package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ChipSocket extends Tile{
    private static final Image CHIP_SOCKET_IMAGE = new Image(ChipSocket.class.getResourceAsStream("sprites/chipSocket.png"));
    //other chips sprites
    private final int chipsNeeded;



    public ChipSocket(int type, int x, int y) {
        super("chipSocket", x, y, true);
        this.chipsNeeded = type;
    }

    public static void resetAllLocks() {
        for(ArrayList<Tile> row : LevelLoader.getTileGrid()) {
            for(Tile t : row) {
                if(t instanceof ChipSocket socket) {
                    socket.setSolid(true);
                }
            }
        }
    }


    public void event(ArrayList<Item> inventory) {
        if (enoughChips(inventory)) {
            LevelLoader.setTile(getX(), getY(), new Path(getX(), getY()));
            AtomicInteger removeChips = new AtomicInteger(chipsNeeded);
            inventory.removeIf(item -> {
                if (item instanceof Chip && removeChips.get() > 0) {
                    removeChips.getAndDecrement();
                    return true;
                }
                return false;
            });

        }

    }

    private boolean enoughChips(ArrayList<Item> inventory) {
        int chipCount = 0;
        for (Item item : inventory) {
            if(item instanceof Chip) {
                chipCount++;
            }
        }
        return chipCount >= chipsNeeded;
    }

    public void checkUnlock(ArrayList<Item> inventory) {
        if(enoughChips(inventory)) {
            setSolid(false);
        }
    }


    @Override
    //case chipsneeded 1-5, draw chipsocket dependant on which it is
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(CHIP_SOCKET_IMAGE, x*size, y*size);
    }
}