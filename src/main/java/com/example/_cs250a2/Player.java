package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class Player{
    private static final Image PLAYER_TILE = new Image(Player.class.getResourceAsStream("player.png"));


    // X and Y coordinate of player on the grid.
    private static int x;
    private static int y;
    private ArrayList<String> inventory;

    public Player(int x, int y) {
        Player.x = x;
        Player.y = y;
    }

    // TODO: When this is finished, removed all occurrences of X = X +- n, and Y = Y +- n
    public void move(KeyEvent event) {
        switch (event.getCode()) {
            case RIGHT:
                // Right key was pressed. So move the player right by one cell.
//                if (Level.checkTile(x+1, y) != "Wall"
//                        && !(Level.checkTile(x+1, y) == "Block"
//                        && Block.isBlocked(x+1, y, x+2, y))):
//                setX(x+1)
//                interact(x+1, y)
                x = x + 1;
                break;
            case LEFT:
                // Left key was pressed. So move the player left by one cell.
//                if (Level.checkTile(x-1, y) != "Wall"
//                        && !(Level.checkTile(x-1, y) == "Block"
//                        && Block.isBlocked(x-1, y, x-2, y))):
//                setX(x-1)
//                interact(x-1, y)
                x = x - 1;
                break;
            case UP:
                // Up key was pressed. So move the player up by one cell.
//                if (Level.checkTile(x, y+1) != "Wall"
//                        && !(Level.checkTile(x, y+1) == "Block"
//                        && Block.isBlocked(x, y+1, x, y+2))):
//                setY(y+1)
//                interact(x, y+1)
                y = y - 1;
                break;
            case DOWN:
                // Down key was pressed. So move the player down by one cell.
//                if (Level.checkTile(x, y-1) != "Wall"
//                        && !(Level.checkTile(x, y-1) == "Block"
//                        && Block.isBlocked(x, y-1, x, y-2))):
//                setY(y-1)
//                interact(x, y-1)
                y = y + 1;
                break;
            default:
                // Do nothing for all other keys.
                break;
        }
    }

    private void interact(int newX, int newY) {
        Tile currentTile = Level.checkTile(x, y);
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
                ChipSocket chipSocket = new ChipSocket(0,0,0);
                chipSocket.event(inventory);
            case "Locked Door":
                LockedDoor.event(inventory);
            case "Ice":
                Ice.event(x, y, newX, newY);
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
            Block block = new Block(x, y);
            block.moveBlock(newX, newY);
        }
    }

    public void addToInventory(String item){
        inventory.add(item);
    }

    public void removeToInventory(String item){
        inventory.add(item);
    }

    public static int getX() {
        return x;
    }

    public static void setX(int x) {
        Player.x = x;
    }

    public static int getY() {
        return y;
    }

    public static void setY(int y) {
        Player.y = y;
    }

    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(PLAYER_TILE, x*size, y*size);
    }
}
