package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class ChipSocket extends Tile{
    private static final Image CHIP_SOCKET_IMAGE = new Image(ChipSocket.class.getResourceAsStream("chipSocket.png"));

    private final int chipsNeeded;

    public ChipSocket(int chipsNeeded, int x, int y) {
        super("chipSocket", x, y, false);
        this.chipsNeeded = chipsNeeded;
    }

    public void event(ArrayList<Item> inventory) {
        int chipsInInventory = 0;

        // Count the number of computer chips in the inventory
        // TODO: Adjust ChipSocket(0, 0, 0) as these are dummy values
        for (Item item : inventory) {
            if (item.equals(new ChipSocket(0, 0, 0))) {
                chipsInInventory++;
            }
        }

        // Check if the player is on the chip socket and has enough chips to open it
        if (chipsInInventory >= chipsNeeded) {
            // Taking out the chips
            for (int i = 0; i < chipsNeeded; i++) {
                inventory.remove("ComputerChip");
            }

            // Replace the chip socket with a path or update the game state accordingly (UNFINISHED)

            System.out.println("open!");
        } else {
            System.out.println("Not enough computer chips to open the chip socket.");
        }
    }

    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(CHIP_SOCKET_IMAGE, x*size, y*size);
    }
}

