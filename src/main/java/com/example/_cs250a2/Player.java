package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;

public class Player{
    private static final Image PLAYER_TILE = new Image(Player.class.getResourceAsStream("sprites/player.png"));

    // X and Y coordinate of player on the grid.
    private static int x;
    private static int y;
    private ArrayList<Item> inventory;

    public Player(int x, int y) {
        Player.x = x;
        Player.y = y;
        inventory = new ArrayList<>();
    }
    public void move(KeyEvent event) {
        switch (event.getCode()) {
            case RIGHT:
                // Right key was pressed. So move the player right by one cell.
                if (LevelLoader.getTile(x+1, y).getName() != "block"
                        && (!LevelLoader.getTile(x + 1, y).isSolid())) {
                    // && Block.isBlocked(x+1, y, x+2, y))): // not really sure what this is for :/
                    interact(x+1, y);
                }
                break;
            case LEFT:
                // Left key was pressed. So move the player left by one cell.
                if (LevelLoader.getTile(x-1, y).getName() != "block"
                        && (!LevelLoader.getTile(x - 1, y).isSolid())) {
                    // && Block.isBlocked(x-1, y, x-2, y))):
                    interact(x - 1, y);
                }
                break;
            case UP:
                // Up key was pressed. So move the player up by one cell.
                if (LevelLoader.getTile(x, y-1).getName() != "block"
                        && (!LevelLoader.getTile(x, y - 1).isSolid())) {
                    // && Block.isBlocked(x, y-1, x, y-2))):
                    interact(x, y - 1);
                }
                break;
            case DOWN:
                // Down key was pressed. So move the player down by one cell.
                if (LevelLoader.getTile(x, y+1).getName() != "block"
                        && (!LevelLoader.getTile(x, y + 1).isSolid())) {
                    // && Block.isBlocked(x, y+1, x, y+2))):
                    interact(x, y + 1);
                }
                break;

            default:
                // Do nothing for all other keys.
                break;
        }
    }

    private void interact(int newX, int newY) {
        Tile currentTile = LevelLoader.getTile(newX, newY);
        Entity currentEntity = LevelLoader.getEntityWithCoords(newX, newY);

        if (currentEntity != null) {
            currentEntity.event(x, y, newX, newY);
        }

        if (currentTile.getName() != "ice") { // Player movement on ice is handled in Ice.java
            Player.setX(newX);
            Player.setY(newY);
        }



        switch (currentTile.getName()) {
            case "dirt":
                Dirt dirt = (Dirt) currentTile;
                dirt.compact();
                break;
            case "exit":
                Exit.event();
                break;
            case "button":
                Button.event();
                break;
            case "trap":
                Trap.event();
                break;
            case "water":
                GameOver.playerDeathDrown();
                break;
            case "chipSocket":
                ChipSocket chipSocket = (ChipSocket) currentTile;
                chipSocket.event(inventory);
                ChipSocket.resetAllLocks();
                break;
            case "lockedDoor":
                LockedDoor lockedDoor = (LockedDoor) currentTile;
                lockedDoor.event(inventory);
                displayInventory();
                break;
            case "ice":
                Ice.event(x, y, newX, newY);
                break;
            default:
//            Path
                break;
        }

        if (Level.isOnItem()) {
            Item currentItem = Level.getCurrentItem();
            if (currentItem instanceof Chip) {

                addToInventory(currentItem);
                LevelLoader.removeItem(currentItem);
                System.out.println(getChips());

                for (ArrayList<Tile> row : LevelLoader.getTileGrid()) {
                    for (Tile t : row) {
                        if (t instanceof ChipSocket) {
                            ChipSocket socket = (ChipSocket) t;
                            socket.checkUnlock(inventory);
                        }
                    }
                }




            } else if (currentItem instanceof Key) {
                addToInventory(currentItem);
                LevelLoader.removeItem(currentItem);
                displayInventory();

                for (ArrayList<Tile> row : LevelLoader.getTileGrid()) {
                    for (Tile t : row) {
                        if (t instanceof LockedDoor) {
                            LockedDoor door = (LockedDoor) t;
                            door.checkUnlock(inventory);
                        }
                    }
                }

            }
        }

        if(Level.isOnMonster()) {
            GameOver.playerDeathMonster();
        }

        if(Level.isOnBlock()) {
            Block block = new Block(x, y);
            block.moveBlock(newX, newY);
        }
    }

    private void displayInventory() {
        System.out.println("Inventory:");
        for (Item item : inventory) {
            System.out.println("- " + item);
        }
    }





    public void addToInventory(Item item){
        inventory.add(item);
    }

    public void removeFromInventory(Item item){
        inventory.remove(item);
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

    public  int getChips() {
        return (int) inventory.stream().filter(item -> item instanceof Chip).count();
    }



}
