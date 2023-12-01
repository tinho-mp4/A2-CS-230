package com.example._cs250a2;

import java.util.ArrayList;

public class ChipSocket {
    private final int chipsNeeded;

    public ChipSocket(int chipsNeeded) {
        this.chipsNeeded = chipsNeeded;
    }

    public void event(ArrayList<String> inventory) {
        int chipsInInventory = 0;

        // Count the number of computer chips in the inventory
        for (String item : inventory) {
            if (item.equals("ComputerChip")) {
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
}

