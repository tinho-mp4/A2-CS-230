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

    private final GameController gameController;
    private boolean canMove = true;
    private final boolean playerOnButton = false;
    private static final Image PLAYER_TILE = new Image(Objects.requireNonNull(Player.class.getResourceAsStream("sprites/player.png")));

    // X and Y coordinate of player on the grid.
    private int x;
    private int y;

    private int prevX;

    private int prevY;

    private final ArrayList<Item> inventory;

    public Player(int x, int y, GameController gameController) {
        super(x, y);
        inventory = new ArrayList<>();
        this.gameController = gameController;
    }

    /**
     * event ?.
     * @param x
     * @param y
     * @param newX
     * @param newY
     */
    public void event(final int x,
                      final int y,
                      final int newX,
                      final int newY) { }
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

    private void interact(int newX, int newY) {
        Tile currentTile = LevelLoader.getTile(newX, newY);
        Entity currentEntity = LevelLoader.getEntityWithCoords(newX, newY);

        if (currentTile instanceof Button) {
            Button button = (Button) currentTile;
            button.press();
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
        x = newX;
        y = newY;
    }





    public void addToInventory(Item item){
        inventory.add(item);
    }

    public void removeFromInventory(Item item){
        inventory.remove(item);
    }

    public void clearInventory() {
        inventory.clear();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(PLAYER_TILE, x*size, y*size);
    }

    public  int getChips() {
        return (int) inventory.stream().filter(item -> item instanceof Chip).count();
    }



}
