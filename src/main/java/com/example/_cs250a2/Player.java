package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.Objects;

public class Player{

    private GameController gameController;
    private boolean canMove = true;
    private boolean playerOnButton = false;
    private static final Image PLAYER_TILE = new Image(Objects.requireNonNull(Player.class.getResourceAsStream("sprites/player.png")));

    // X and Y coordinate of player on the grid.
    private static int x;
    private static int y;
    private final ArrayList<Item> inventory;

    public Player(int x, int y, GameController gameController) {
        Player.x = x;
        Player.y = y;
        inventory = new ArrayList<>();
        this.gameController = gameController;
    }
    public void move(KeyEvent event) {
        if (canMove) {
            switch (event.getCode()) {
                case RIGHT:
                    // Right key was pressed. So move the player right by one cell.
                    if (LevelLoader.getTile(x + 1, y).getName() != "block"
                            && (!LevelLoader.getTile(x + 1, y).isSolid())) {
                        // && Block.isBlocked(x+1, y, x+2, y))): // not really sure what this is for :/
                        interact(x + 1, y);
                    }
                    break;
                case LEFT:
                    // Left key was pressed. So move the player left by one cell.
                    if (LevelLoader.getTile(x - 1, y).getName() != "block"
                            && (!LevelLoader.getTile(x - 1, y).isSolid())) {
                        // && Block.isBlocked(x-1, y, x-2, y))):
                        interact(x - 1, y);
                    }
                    break;
                case UP:
                    // Up key was pressed. So move the player up by one cell.
                    if (LevelLoader.getTile(x, y - 1).getName() != "block"
                            && (!LevelLoader.getTile(x, y - 1).isSolid())) {
                        // && Block.isBlocked(x, y-1, x, y-2))):
                        interact(x, y - 1);
                    }
                    break;
                case DOWN:
                    // Down key was pressed. So move the player down by one cell.
                    if (LevelLoader.getTile(x, y + 1).getName() != "block"
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
                gameController.clearLevel();
                break;
            case "button":
                Button button = (Button) currentTile;
                button.press();
                break;
            case "trap":
                Trap trap = (Trap) currentTile;
                if (trap.isStuck()) {
                    canMove = false;
                }
                break;
            case "water":
                gameController.clearLevel();
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
                        if (t instanceof ChipSocket socket) {
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
                        if (t instanceof LockedDoor door) {
                            door.checkUnlock(inventory);
                        }
                    }
                }

            }
        }

        if(Level.isOnMonster()) {
            gameController.clearLevel();
            GameOver.playerDeathMonster();
        }

        if(Level.isOnBlock()) {
            Block block = new Block(x, y);
            block.moveBlock(newX, newY);
        }
    }

    public boolean isOnExit() {
        Tile currentTile = LevelLoader.getTile(x, y);
        return currentTile instanceof Exit;
    }

    private void displayInventory() {
        System.out.println("Inventory:");
        for (Item item : inventory) {
            System.out.println("- " + item);
        }
    }

    public void setPosition(int newX, int newY) {
        this.x = newX;
        this.y = newY;
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
