package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.Objects;

public class Player{
    private static final Image PLAYER_TILE = new Image(Objects.requireNonNull(Player.class.getResourceAsStream("sprites/player.png")));

    private static int x;
    private static int y;
    private final ArrayList<Item> inventory;

    public Player(int x, int y) {
        Player.x = x;
        Player.y = y;
        this.inventory = new ArrayList<>();
    }

    public void move(KeyEvent event) {
        switch (event.getCode()) {
            case RIGHT:
                // Right key was pressed. So move the player right by one cell.
                if (!Objects.equals(LevelLoader.getTile(x + 1, y).getName(), "wall")
                        && (!Objects.equals(LevelLoader.getTile(x + 1, y).getName(), "block"))) {
                        // && Block.isBlocked(x+1, y, x+2, y))): // not really sure what this is for :/
                    interact(x+1, y);
                }
                break;
            case LEFT:
                // Left key was pressed. So move the player left by one cell.
                if (!Objects.equals(LevelLoader.getTile(x - 1, y).getName(), "wall")
                        && (!Objects.equals(LevelLoader.getTile(x - 1, y).getName(), "block"))) {
                    // && Block.isBlocked(x-1, y, x-2, y))):
                    interact(x - 1, y);
                }
                break;
            case UP:
                // Up key was pressed. So move the player up by one cell.
                if (!Objects.equals(LevelLoader.getTile(x, y - 1).getName(), "wall")
                        && (!Objects.equals(LevelLoader.getTile(x, y - 1).getName(), "block"))) {
                    // && Block.isBlocked(x, y-1, x, y-2))):
                    interact(x, y - 1);
                }
                break;
            case DOWN:
                // Down key was pressed. So move the player down by one cell.
                if (!Objects.equals(LevelLoader.getTile(x, y + 1).getName(), "wall")
                        && (!Objects.equals(LevelLoader.getTile(x, y + 1).getName(), "block"))) {
                    // && Block.isBlocked(x, y+1, x, y+2))):
                    interact(x, y + 1);
                }
                break;

            default:
                // Do nothing for all other keys.
                break;
        }
        System.out.println("Player's new position: (" + x + ", " + y + ")");
    }

    private void interact(int newX, int newY) {

        Tile currentTile = LevelLoader.getTile(newX, newY);
        if (!Objects.equals(currentTile.getName(), "ice")) { // Player movement on ice is handled in Ice.java
            Player.setX(newX);
            Player.setY(newY);
        }
        Item item = Level.getItem(newX, newY);
        if (item instanceof Key) {
            addToInventory(item);
            Level.removeItem(newX, newY);
        }

        switch (currentTile.getName()) {
            case "dirt" -> {
                Dirt dirt = (Dirt) currentTile;
                dirt.compact();
            }
            case "exit" -> Level.nextLevel();
            case "button" -> Button.event();
            case "trap" -> Trap.event();
            case "water" -> GameOver.playerDeathDrown();
            case "chipSocket" -> {
                ChipSocket chipSocket = (ChipSocket) currentTile;
                chipSocket.event(inventory);
            }
            case "lockedDoor" -> {
                LockedDoor lockedDoor = (LockedDoor) currentTile;
                LockedDoor.event(inventory, newX, newY, lockedDoor.getDoorType());
            }
            case "ice" -> Ice.event(x, y, newX, newY);
            default -> {
            }
            // Path or any other tile
        }


        if (Level.isOnItem(x, y)) {
            if (item != null) {
                System.out.println("Player is on an item: " + item.getClass().getSimpleName());
                addToInventory(item);
                Level.removeItem(x, y);
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

    private void addToInventory(Item item) {
        if (item instanceof Key) {
            inventory.add(item);
        }
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
        gc.drawImage(PLAYER_TILE, x * size, y * size);
    }
}
