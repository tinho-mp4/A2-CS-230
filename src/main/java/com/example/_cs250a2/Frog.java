package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * The {@code Frog} class represents a Frog (monster) in the game.
 * @author idk
 * @version 1.0
 */
//TODO test more
    //seems to be working relatively well
public class Frog extends Monster {

    /**
     * The speed of the frog.
     */
    private static int speed;

    /**
     * Image of frog.
     */
    private static final Image FROG_IMAGE
    = new Image(Key.class.getResourceAsStream("sprites/frog.png"));

    /**
     * Creates the frog.
     * @param ticks speed
     * @param startingDirection direction
     * @param startingLocation location
     */
    public Frog(final int ticks,
                final char startingDirection,
                final int[] startingLocation) {
        super(startingLocation[0], startingLocation[1], startingDirection);
        allowedTiles.remove("Trap");
        speed = ticks;
        direction = startingDirection;
        location = startingLocation;
        this.setX(location[0]);
        this.setY(location[1]);
        checkDirection(startingDirection);
        checkLocation(startingLocation);
        //saves the index that the monsters' x and y coordinates are stored
        //X is stored at 2 * the number of monsters
        // since each monster stores two values and y is after that
        arrayLocationX = countMonsters * 2;
        arrayLocationY = (countMonsters * 2) + 1;
        countMonsters++;
        monsterLocations.add(this.getX());
        monsterLocations.add(this.getY());
        FrogList.add(this);
    }

    /**
     * Gets the speed of the frog.
     * @return speed
     */
    public int getSpeed() {
        return speed;
    }


    /**
     * try and make the frog x equal to player x then do the same with y.
     * @param x original x
     * @param y original y
     * @param newX new x
     * @param newY new y
     */
    public void event(final int x,
                      final int y,
                      final int newX,
                      final int newY) { }

    //this move method will try and make
    // the frog x equal to player x then do the same with y

    /**
     * Moves the frog.
     */
    public void move() {
        int[] currentTile = {this.getX(), this.getY()};
        int[] checkLeft = {this.getX() - 1, this.getY()};
        int[] checkRight = {this.getX() + 1, this.getY()};
        int[] checkBelow = {this.getX(), this.getY() - 1};
        int[] checkAbove = {this.getX(), this.getY() + 1};
        Player player = (Player) LevelLoader.getEntityByClass(Player.class);
        int playerX = player.getX();
        int playerY = player.getY();
        if (this.getX() > playerX && checkTile(checkLeft, currentTile)) {
            this.setX(this.getX() - 1);
            playerKill();
            locationUpdate(arrayLocationX, this.getX());
            direction = 'a';
        } else if (this.getX() < playerX && checkTile(
                checkRight, currentTile)) {
            this.setX(this.getX() + 1);
            playerKill();
            locationUpdate(arrayLocationX, this.getX());
            direction = 'd';
        } else if (this.getY() > playerY
                && checkTile(checkBelow, currentTile)) {
            this.setY(this.getY() - 1);
            playerKill();
            locationUpdate(arrayLocationY, this.getY());
            direction = 's';
        } else if (this.getY() < playerY
                && checkTile(checkAbove, currentTile)) {
            this.setY(this.getY() + 1);
            playerKill();
            locationUpdate(arrayLocationY, this.getY());
            direction = 'w';
        } else if (direction == 'a' && checkTile(checkAbove, currentTile)) {
            this.setY(this.getY() + 1);
            playerKill();
            locationUpdate(arrayLocationY, this.getY());
            direction = 'w';
        } else if (direction == 'a' && checkTile(checkBelow, currentTile)) {
            this.setY(this.getY() - 1);
            playerKill();
            locationUpdate(arrayLocationY, this.getY());
            direction = 's';
        } else if (direction == 'a') {
            this.setX(this.getX() + 1);
            playerKill();
            locationUpdate(arrayLocationX, this.getX());
            direction = 'd';
        } else if (direction == 'w' && checkTile(checkLeft, currentTile)) {
            this.setX(this.getX() - 1);
            playerKill();
            locationUpdate(arrayLocationX, this.getX());
            direction = 'a';
        } else if (direction == 'w' && checkTile(checkRight, currentTile)) {
            this.setX(this.getX() + 1);
            playerKill();
            locationUpdate(arrayLocationX, this.getX());
            direction = 'd';
        } else if (direction == 'w') {
            this.setY(this.getY() - 1);
            playerKill();
            locationUpdate(arrayLocationY, this.getY());
            direction = 's';
        } else if (direction == 's' && checkTile(checkRight, currentTile)) {
            this.setX(this.getX() + 1);
            playerKill();
            locationUpdate(arrayLocationX, this.getX());
            direction = 'd';
        } else if (direction == 's' && checkTile(checkLeft, currentTile)) {
            this.setX(this.getX() - 1);
            playerKill();
            locationUpdate(arrayLocationX, this.getX());
            direction = 'a';
        } else if (direction == 's') {
            this.setY(this.getY() + 1);
            playerKill();
            locationUpdate(arrayLocationY, this.getY());
            direction = 'w';
        } else if (direction == 'd' && checkTile(checkBelow, currentTile)) {
            this.setY(this.getY() - 1);
            playerKill();
            locationUpdate(arrayLocationY, this.getY());
            direction = 's';
        } else if (direction == 'd' && checkTile(checkAbove, currentTile)) {
            this.setY(this.getY() + 1);
            playerKill();
            locationUpdate(arrayLocationY, this.getY());
            direction = 'w';
        } else if (direction == 'd') {
            this.setY(this.getY() - 1);
            playerKill();
            locationUpdate(arrayLocationY, this.getY());
            direction = 'a';
        }
    }

    /**
     * Draws the frog.
     * @param gc The graphics context.
     * @param x The X position of the frog
     * @param y The Y position of the frog
     * @param size The size of the frog
     */
    @Override
    public void draw(final GraphicsContext gc,
                     final double x,
                     final double y,
                     final double size) {
        gc.drawImage(FROG_IMAGE, x * size, y * size);
    }
}
