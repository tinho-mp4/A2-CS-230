package com.example._cs250a2;

import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class Player {
    // X and Y coordinate of player on the grid.
    private int X;
    private int Y;
    private ArrayList<String> inventory;

    public Player(int x, int y) {
        X = x;
        Y = y;
    }

    public void move(KeyEvent event) {
        switch (event.getCode()) {
            case RIGHT:
                // Right key was pressed. So move the player right by one cell.
//                if (Level.checkTile(x+1, y) != "Wall"
//                        && !(Level.checkTile(x+1, y) == "Block"
//                        && Block.isBlocked(x+1, y, x+2, y))):
//                setY(x+1)
//                interact(x+1, y)
                X = X + 1;
                break;
            case LEFT:
                // Left key was pressed. So move the player left by one cell.
//                if (Level.checkTile(x-1, y) != "Wall"
//                        && !(Level.checkTile(x-1, y) == "Block"
//                        && Block.isBlocked(x-1, y, x-2, y))):
//                setY(x-1)
//                interact(x-1, y)
                X = X - 1;
                break;
            case UP:
                // Up key was pressed. So move the player up by one cell.
//                if (Level.checkTile(x, y+1) != "Wall"
//                        && !(Level.checkTile(x, y+1) == "Block"
//                        && Block.isBlocked(x, y+1, x, y+2))):
//                setY(y+1)
//                interact(x, y+1)
                Y = Y - 1;
                break;
            case DOWN:
                // Down key was pressed. So move the player down by one cell.
//                if (Level.checkTile(x, y-1) != "Wall"
//                        && !(Level.checkTile(x, y-1) == "Block"
//                        && Block.isBlocked(x, y-1, x, y-2))):
//                setY(y-1)
//                interact(x, y-1)
                Y = Y + 1;
                break;
            default:
                // Do nothing for all other keys.
                break;
        }
    }

    private void interact(int newX, int newY) {
        Tile currentTile = Level.checkTile(X, Y);
        switch(currentTile.getName()) {
            case "Dirt":
                Dirt.event();
            case "Exit":
                Level.nextLevel();
            case "Button":
                Button.event();
            case "Trap":
                Trap.event();
            case "Water":
                GameOver.playerDeathDrown();
            case "Chip Socket":
                ChipSocket chipSocket = new ChipSocket(0);
                chipSocket.event(inventory);
            case "Locked Door":
                LockedDoor.event(inventory);
            case "Ice":
                Ice.event();
            default:
//            Path
                break;
        }

        if (Level.isOnitem()) {
            addToInventory(Item.getItemName);
        }

        if(Level.isOnMonster()) {
            GameOver.playerDeathMonster();
        }

        if(Level.isOnBlock()) {
            Block block = new Block(X,Y);
            block.moveBlock(newX, newY);
        }
    }

    public void addToInventory(String item){
        inventory.add(item);
    }

//    public void removeToInventory(String item){
//        inventory.add(item);
//    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }
}
