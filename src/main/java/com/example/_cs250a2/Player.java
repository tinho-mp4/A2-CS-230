package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents the player in the game.
 * Manages player's position, movement, interactions, and inventory.
 * @author Evans
 * @version 1.0
 */
public class Player extends Entity{
    /** The game controller managing the player's interactions and game state. */
    private final GameController GAME_CONTROLLER;

    /** The inventory of items collected by the player. */
    private final ArrayList<Item> INVENTORY;

    /** The image representing the player's character. */
    private static final Image PLAYER_TILE =
            new Image(Objects.requireNonNull(Player.class.getResourceAsStream("sprites/player.png")));

    /** Flag indicating whether the player can move (e.g., not stuck in a trap). */
    private boolean canMove = true;

    /** The current x-coordinate of the player's position. */
    private int x;

    /** The current y-coordinate of the player's position. */
    private int y;

    /** The previous x-coordinate of the player's position. */
    private int prevX;

    /** The previous y-coordinate of the player's position. */
    private int prevY;


    /**
     * Creates a player at specified coordinates and associates it with a game controller.
     *
     * @param x              X-coordinate of the player.
     * @param y              Y-coordinate of the player.
     * @param gameController GameController to manage the game logic.
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
     * @param x     x coordinate
     * @param y     y coordinate
     * @param newX  The new x coordinate
     * @param newY  The new y coordinate
     */
    public void event(
            final int x,
            final int y,
            final int newX,
            final int newY) {
        Tile currentTile = LevelLoader.getTile(x, y);
        if (currentTile instanceof Trap) {
            Trap trap = (Trap) currentTile;
            if (trap.isStuck()) {
                canMove = true;
            }
        }
    }

    /**
     * Handles player movement based on keyboard input. Moves the player in the specified direction if the
     * destination is not blocked by solid tiles or immovable entities.
     *
     * @param event The KeyEvent corresponding to the player's keyboard input.
     */
    public void move(KeyEvent event) {
        prevX = x;
        prevY = y;

        if (canMove) {
            switch (event.getCode()) {
                case RIGHT:
                    // Right key was pressed. So move the player right by one cell.
                    if (!LevelLoader.getTile(x + 1, y).isSolid()
                            && !((LevelLoader.getEntityWithCoords(x + 1, y) instanceof Block)
                            && !LevelLoader.getTile(x + 2, y).isPushableBlock())) {
                        interact(x + 1, y);
                    }
                    break;
                case LEFT:
                    // Left key was pressed. So move the player left by one cell.
                    if (!LevelLoader.getTile(x - 1, y).isSolid()
                            && !((LevelLoader.getEntityWithCoords(x - 1, y) instanceof Block)
                            && !LevelLoader.getTile(x - 2, y).isPushableBlock())) {
                        interact(x - 1, y);
                    }
                    break;
                case UP:
                    // Up key was pressed. So move the player up by one cell.
                    if (!LevelLoader.getTile(x, y - 1).isSolid()
                            && !((LevelLoader.getEntityWithCoords(x, y - 1) instanceof Block)
                            && !LevelLoader.getTile(x, y - 2).isPushableBlock())) {
                        interact(x, y - 1);
                    }
                    break;
                case DOWN:
                    // Down key was pressed. So move the player down by one cell.
                    if (!LevelLoader.getTile(x, y + 1).isSolid()
                            && !((LevelLoader.getEntityWithCoords(x, y + 1) instanceof Block)
                            && !LevelLoader.getTile(x, y + 2).isPushableBlock())) {
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
     * Performs the player's interactions within the game.
     * @param newX new x coordinate if the player has been moved by environment.
     * @param newY new y coordinate if the player has been moved by environment.
     */
    private void interact(int newX, int newY) {
        Tile currentTile = LevelLoader.getTile(newX, newY);
        Entity currentEntity = LevelLoader.getEntityWithCoords(newX - (newX - x), newY - (newY - y));
        Entity nextEntity = LevelLoader.getEntityWithCoords(newX, newY);
        boolean movedAfterBlock = false;

        if (currentTile instanceof Button) {
            Button button = (Button) currentTile;
            button.press();
        }

        if (nextEntity instanceof Block block) {
            Ice.event(block.getX(), block.getY(), block.getX() + (newX - x), block.getY() + (newY - y), false);
            Ice.event(currentEntity.getX(), currentEntity.getY(), newX, newY, true);
            movedAfterBlock = true;
        }

        Tile prevTile = LevelLoader.getTile(prevX, prevY);
        if (prevTile instanceof Button) {
            Button prevButton = (Button) prevTile;
            prevButton.unpress();
        }


        if (currentEntity != null) {
            currentEntity.event(x, y, newX, newY);
        }

        if (currentTile.getName() != "ice" && !movedAfterBlock) { // Player movement on ice is handled in Ice.java
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
                GAME_CONTROLLER.showHighScores();
                break;
            case "button":
                Button button = (Button) currentTile;
                button.press();
                break;
            case "trap":
                Trap trap = (Trap) currentTile;
                if (!trap.isStuck()) {
                    canMove = false;
                } else {
                    canMove = true;
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
                Ice.event(x, y, newX, newY, false);
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

        if (isOnMonster()) {
            GAME_CONTROLLER.clearLevel();
            GameOver.playerDeathMonster();
        }

        this.event(this.x, this.y, this.x, this.y);
    }

    /**
     * Check if the player is on the same tile as a monster.
     * @return True if the player is on the same tile as a monster, false otherwise.
     */
    private boolean isOnMonster() {
        for (Entity entity : LevelLoader.getEntityList()) {
            if (entity instanceof Monster) {
                if (entity.getX() == x && entity.getY() == y) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Prints the Player's inventory
     */
    private void displayInventory() {
        System.out.println("Inventory:");
        for (Item item : INVENTORY) {
            //System.out.println("- " + item);
        }
    }

    /**
     * Sets the player's position to the given coordinates.
     * @param newX The new x-coordinate.
     * @param newY The new y-coordinate.
     */
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
     * Removes an item from the player's inventory.
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
     * @return The player's x-coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * @return The player's y-coordinate.
     */
    public int getY() {
        return y;
    }


    /**
     * Get the chips in the player's inventory.
     * @return The amount of chips in player's inventory.
     */
    public  int getChips() {
        return (int) INVENTORY.stream().filter(item -> item instanceof Chip).count();
    }

    /**
     * Draws the player on the given graphics context at specified coordinates and size.
     * @param gc   GraphicsContext to draw on.
     * @param x    X-coordinate for drawing.
     * @param y    Y-coordinate for drawing.
     * @param size Size of the player image.
     */
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(PLAYER_TILE, x * size, y * size);
    }
}
