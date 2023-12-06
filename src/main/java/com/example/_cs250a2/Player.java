package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class Player extends Entity{
    private static final Image PLAYER_TILE = new Image(Player.class.getResourceAsStream("player.png"));


    // X and Y coordinate of player on the grid.
    private static int x;
    private static int y;
    private ArrayList<Item> inventory;

    public Player(int x, int y) {
        Player.x = x;
        Player.y = y;
    }
    public void move(KeyEvent event) {
        switch (event.getCode()) {
            case RIGHT:
                // Right key was pressed. So move the player right by one cell.
                if (LevelLoader.getTile(x+1, y).getName() != "wall"
                        && (LevelLoader.getTile(x+1, y).getName() != "block")) {
                        // && Block.isBlocked(x+1, y, x+2, y))): // not really sure what this is for :/
                    setX(x+1);
                    interact(x+1, y);
                }
                break;
            case LEFT:
                // Left key was pressed. So move the player left by one cell.
                if (LevelLoader.getTile(x-1, y).getName() != "wall"
                        && (LevelLoader.getTile(x-1, y).getName() != "block")) {
                    // && Block.isBlocked(x-1, y, x-2, y))):
                    setX(x - 1);
                    interact(x - 1, y);
                }
                break;
            case UP:
                // Up key was pressed. So move the player up by one cell.
                if (LevelLoader.getTile(x, y-1).getName() != "wall"
                        && (LevelLoader.getTile(x, y-1).getName() != "block")) {
                    // && Block.isBlocked(x, y-1, x, y-2))):
                    setY(y - 1);
                    interact(x, y - 1);
                }
                break;
            case DOWN:
                // Down key was pressed. So move the player down by one cell.
                if (LevelLoader.getTile(x, y+1).getName() != "wall"
                        && (LevelLoader.getTile(x, y+1).getName() != "block")) {
                    // && Block.isBlocked(x, y+1, x, y+2))):
                    setY(y + 1);
                    interact(x, y + 1);
                }
                break;

            default:
                // Do nothing for all other keys.
                break;
        }
    }

    private void interact(int newX, int newY) {
        Tile currentTile = LevelLoader.getTile(x, y);
        switch(currentTile.getName()) {
            case "dirt":
                Dirt.event();
            case "exit":
                Level.nextLevel();
            case "button":
                Button.event();
            case "trap":
                Trap.event();
            case "water":
                GameOver.playerDeathDrown();
            case "chipSocket":
                ChipSocket chipSocket = new ChipSocket(0,0,0);
                chipSocket.event(inventory);
            case "lockedDoor":
                LockedDoor.event(inventory);
            case "ice":
                Ice.event(x, y, newX, newY);
            default:
//            Path
                break;
        }

        if (Level.isOnitem()) {
            // ???
            // addToInventory(Item.getItemName());
        }

        if(Level.isOnMonster()) {
            GameOver.playerDeathMonster();
        }

        if(Level.isOnBlock()) {
            Block block = new Block(x, y);
            block.moveBlock(newX, newY);
        }
    }

    public void addToInventory(Item item){
        inventory.add(item);
    }

    public void removeToInventory(Item item){
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
