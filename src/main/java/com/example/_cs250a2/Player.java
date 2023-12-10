package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.Objects;

/**
 * The {@code Player} class represents the player in the game
 * @author idk
 * @version 1.0
 */
public class Player extends Entity{
    private final GameController GAME_CONTROLLER;
    private final boolean PLAYER_ON_BUTTON = false;
    private final ArrayList<Item> INVENTORY;
    private static final Image PLAYER_TILE =
    new Image(Objects.requireNonNull(Player.class.getResourceAsStream("sprites/player.png")));
    private int x;
    private int y;
    private boolean canMove = true;
    private int prevX;
    private int prevY;

    /**
     * Creates the player at coordinates (x,y).
     * @param x x coordinate
     * @param y y coordinate
     * @param gameController the {@code GameController} //<- look at this
     */
    public Player(int x, int y, GameController gameController) {
        super(x, y);
        this.x = x;
        this.y = y;
        INVENTORY = new ArrayList<>();
        this.GAME_CONTROLLER = gameController;
    }

    /**
     * Handles an event in the game.
     * @param x x coordinate
     * @param y y coordinate
     * @param newX The new x coordinate
     * @param newY The new y coordinate
     */
    public void event(
            final int x,
            final int y,
            final int newX,
            final int newY) {

    }

    /**
     * Moves the player in the game.
     * @param event
     */
    public void move(KeyEvent event) {

        prevX = x;
        prevY = y;


        if (canMove) {
            switch (event.getCode()) {
                case RIGHT:
                    // Right key was pressed. So move the player right by one cell.
                    if (!LevelLoader.getTile(x + 1, y).isSolid()
                            && !((LevelLoader.getEntityWithCoords(x + 1, y) instanceof Block) && !LevelLoader.getTile(x + 2, y).isPushableBlock())) {
                        interact(x + 1, y);
                    }
                    break;
                case LEFT:
                    // Left key was pressed. So move the player left by one cell.
                    if (!LevelLoader.getTile(x - 1, y).isSolid()
                            && !((LevelLoader.getEntityWithCoords(x - 1, y) instanceof Block) && !LevelLoader.getTile(x - 2, y).isPushableBlock())) {
                        interact(x - 1, y);
                    }
                    break;
                case UP:
                    // Up key was pressed. So move the player up by one cell.
                    if (!LevelLoader.getTile(x, y - 1).isSolid()
                            && !((LevelLoader.getEntityWithCoords(x, y - 1) instanceof Block) && !LevelLoader.getTile(x, y - 2).isPushableBlock())) {
                        interact(x, y - 1);
                    }
                    break;
                case DOWN:
                    // Down key was pressed. So move the player down by one cell.
                    if (!LevelLoader.getTile(x, y + 1).isSolid()
                            && !((LevelLoader.getEntityWithCoords(x, y + 1) instanceof Block) && !LevelLoader.getTile(x, y + 2).isPushableBlock())) {
                        interact(x, y + 1);
                    }
                    break;

                default:
                    // Do nothing for all other keys.
                    break;
            }
        }
    }

    /**
     *Performs the player's interactions within the game.
     * @param newX new x coordinate if the player has been moved by environment.
     * @param newY new y coordinate if the player has been moved by environment.
     */
    private void interact(int newX, int newY) {
        Tile currentTile = LevelLoader.getTile(newX, newY);
        Entity currentEntity = LevelLoader.getEntityWithCoords(newX - (newX - x), newY - (newY - y));
        Entity nextEntity = LevelLoader.getEntityWithCoords(newX, newY);

        if (currentTile instanceof Button) {
            Button button = (Button) currentTile;
            button.press();
        }

        if (nextEntity instanceof Block block) {
            if (LevelLoader.getTile(newX + 2*(newX - x), newY + 2*(newY - y)).isSolid()) {
                return;
            }
            Ice.event(block.getX(), block.getY(), block.getX() + (newX - x), block.getY() + (newY - y));
        }

        Tile prevTile = LevelLoader.getTile(prevX, prevY);
        if (prevTile instanceof Button) {
            Button prevButton = (Button) prevTile;
            prevButton.unpress();
        }


        if (currentEntity != null) {
            currentEntity.event(x, y, newX, newY);
        }

        if (currentTile.getName() != "ice") { // Player movement on ice is handled in Ice.java
            this.setX(newX);
            this.setY(newY);
            this.setPosition(newX, newY);

        }

        switch (currentTile.getName()) {
            case "dirt":
                Dirt dirt = (Dirt) currentTile;
                dirt.compact();
                break;
            case "exit":
                Exit.event();
                GAME_CONTROLLER.clearLevel();
                GAME_CONTROLLER.handleShowHighScoresButton();
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
                GAME_CONTROLLER.clearLevel();
                GameOver.playerDeathDrown();
                break;
            case "chipSocket":
                ChipSocket chipSocket = (ChipSocket) currentTile;
                chipSocket.event(INVENTORY);
                ChipSocket.resetAllLocks();
                break;
            case "lockedDoor":
                LockedDoor lockedDoor = (LockedDoor) currentTile;
                lockedDoor.event(INVENTORY);
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
                            socket.checkUnlock(INVENTORY);
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
                            door.checkUnlock(INVENTORY);
                        }
                    }
                }

            }
        }

        if(Level.isOnMonster()) {
            GAME_CONTROLLER.clearLevel();
            GameOver.playerDeathMonster();
        }

        if(Level.isOnBlock()) {
            Block block = new Block(x, y);
            block.moveBlock(newX, newY);
        }
    }

    /**
     *Checks if the player is on an exit tile.
     * @return an instance of exit
     */
    public boolean isOnExit() {
        Tile currentTile = LevelLoader.getTile(x, y);
        return currentTile instanceof Exit;
    }

    /**
     *Prints the Player's inventory
     */
    private void displayInventory() {
        System.out.println("Inventory:");
        for (Item item : INVENTORY) {
            System.out.println("- " + item);
        }
    }

    public void setPosition(int newX, int newY) {
        x = newX;
        y = newY;
    }

    /**
     *Adds an item to the player's inventory
     * @param item The item being added to the inventory.
     */
    public void addToInventory(Item item){
        INVENTORY.add(item);
    }

    /**
     *Removes an item from the player's inventory.
     * @param item The item being removed from the inventory.
     */
    public void removeFromInventory(Item item){
        INVENTORY.remove(item);
    }

    /**
     * Clears player's inventory.
     */
    public void clearInventory() {
        INVENTORY.clear();
    }

    /**
     *
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     *Draws the player.
     * @param gc
     * @param x
     * @param y
     * @param size
     */
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(PLAYER_TILE, x*size, y*size);
    }

    /**
     * Get the chips in the player's inventory.
     * @return The amount of chips in player's inventory.
     */
    public  int getChips() {
        return (int) INVENTORY.stream().filter(item -> item instanceof Chip).count();
    }

}
