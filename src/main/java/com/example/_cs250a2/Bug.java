package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

/**
 * The {@code Bug} class represents a bug (monster) in the game.
 * @author Finn P
 * @version 1.0
 */
public class Bug extends Monster {
    private static final Image BUG_IMAGE
    = new Image(Objects.requireNonNull(Key.class.getResourceAsStream("sprites/bug.png")));
    /**
     * which wall to 'hug' when bug moves, true for left or false for right.
     */
    private final boolean left;
    /**
     * The Game Controller.
     */
    private final GameController gameController;
    /**
     * speed is how many ticks between movement.
     */
    private static int speed;

    /**
     * constructor.
     * checks the location and direction are valid, also adds location to a list of monster locations
     * @param ticks number of ticks between movement (speed)
     * @param startingDirection direction
     * @param startingLocation location
     * @param side left or right
     * @param gameController gameController
     */
    public Bug(final int ticks, final char startingDirection, final int[] startingLocation,
               final boolean side, GameController gameController) {
        super(startingLocation[0], startingLocation[1], startingDirection);
        speed = ticks;
        direction = startingDirection;
        location = startingLocation;
        this.setX(location[0]);
        this.setY(location[1]);
        left = side;
        this.gameController = gameController;
        checkDirection(startingDirection);
        checkLocation(startingLocation);
        //saves the index that the monsters' x and y coordinates are stored
        //X is stored at 2 * the number of
        // monsters since each monster stores two values and y is after that
        arrayLocationX = countMonsters * 2;
        arrayLocationY = (countMonsters * 2) + 1;
        countMonsters++;
        monsterLocations.add(this.getX());
        monsterLocations.add(this.getY());
    }

    /**
     * gets the speed of the bug.
     * @return speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * event ?.
     * @param x    The current x-coordinate.
     * @param y    The current y-coordinate.
     * @param newX The new x-coordinate after the event.
     * @param newY The new y-coordinate after the event.
     */
    public void event(final int x, final int y, final int newX, final int newY) {
        Tile tile = LevelLoader.getTile(x, y);
        if (tile instanceof Button) {
            ((Button) tile).checkIfEntityOnButton();
        }
    }

    /**
     * moves the monster and calls checks to kill player.
     */
    public void move() {
        event(this.getX(), this.getY(), this.getX(), this.getY());
        moveCount++;
        int[] currentTile = {this.getX(), this.getY()};
        int[] toTheLeft = {this.getX() - 1, this.getY()};
        int[] above = {this.getX(), this.getY() + 1};
        int[] below = {this.getX(), this.getY() - 1};
        int[] toTheRight = {this.getX() + 1, this.getY()};
        //checks which side to keep the bug on
        if (left) {
            if (direction == 'w') {
                //the bug wants to go forwards
                // or to its left (or right), checks if it can
                if (!checkTile(toTheLeft, currentTile) && !checkTile(above, currentTile)
                        && moveCount < MAXTURNS) {
                    //bug cant go where it wants to,
                    // so it turns right and tries again
                    direction = 'd';
                    move();
                    //it can either go one way or both,
                    // if it cant go left it goes in its current facing
                } else if (!(checkTile(toTheLeft, currentTile))) {
                    this.setY(this.getY()+1);
                    playerKill(gameController);
                    locationUpdate(arrayLocationY, this.getY());
                    //reset count on move
                    moveCount = 0;
                    //if both tiles are available it is on an outside corner
                    //and so turns (and moves in the new direction) to go
                } else {
                    direction = 'a';
                    this.setX(this.getX()-1);
                    playerKill(gameController);
                    locationUpdate(arrayLocationX, this.getX());
                    moveCount = 0;
                }
            } else if (direction == 's') {
                if (!checkTile(below, currentTile) && !checkTile(toTheRight, currentTile)
                        && moveCount < MAXTURNS) {
                    direction = 'a';
                    move();
                } else if (!checkTile(toTheRight, currentTile)) {
                    this.setY(this.getY()-1);
                    playerKill(gameController);
                    locationUpdate(arrayLocationY, this.getY());
                    moveCount = 0;
                } else {
                    direction = 'd';
                    this.setX(this.getX()+1);
                    playerKill(gameController);
                    locationUpdate(arrayLocationX, this.getX());
                    moveCount = 0;
                }
            } else if (direction == 'a') {
                if (!checkTile(toTheLeft, currentTile) && !checkTile(below, currentTile)
                && moveCount < MAXTURNS) {
                    direction = 'w';
                    move();
                } else if (!checkTile(below, currentTile)) {
                    this.setX(this.getX()-1);
                    playerKill(gameController);
                    locationUpdate(arrayLocationX, this.getX());
                    moveCount = 0;
                } else {
                    direction = 's';
                    this.setY(this.getY()-1);
                    playerKill(gameController);
                    locationUpdate(arrayLocationY, this.getY());
                    moveCount = 0;
                }
            } else if (direction == 'd') {
                if (!checkTile(toTheRight, currentTile) && !checkTile(above, currentTile)
                && moveCount < MAXTURNS) {
                    direction = 's';
                    move();
                } else if (!checkTile(above, currentTile)) {
                    this.setX(this.getX()+1);
                    playerKill(gameController);
                    locationUpdate(arrayLocationX, this.getX());
                    moveCount = 0;
                } else {
                    direction = 'w';
                    this.setY(this.getY()+1);
                    playerKill(gameController);
                    locationUpdate(arrayLocationY, this.getY());
                    moveCount = 0;
                }
            }
        } else {
            if (direction == 'w') {
                if (!checkTile(toTheRight, currentTile) && !checkTile(above, currentTile)
                && moveCount < MAXTURNS) {
                    direction = 'a';
                    move();
                } else if (!checkTile(toTheRight, currentTile)) {
                    this.setY(this.getY()+1);
                    playerKill(gameController);
                    locationUpdate(arrayLocationY, this.getY());
                    moveCount = 0;
                } else {
                    direction = 'd';
                    this.setX(this.getX()+1);
                    playerKill(gameController);
                    locationUpdate(arrayLocationX, this.getX());
                    moveCount = 0;
                }
            } else if (direction == 's') {
                if (!checkTile(toTheLeft, currentTile) && !checkTile(below, currentTile)
                && moveCount < MAXTURNS) {
                    direction = 'd';
                    move();
                } else if (!checkTile(toTheLeft, currentTile)) {
                    this.setY(this.getY()+1);
                    playerKill(gameController);
                    locationUpdate(arrayLocationY, this.getY());
                    moveCount = 0;
                } else {
                    direction = 'a';
                    this.setX(this.getX()-1);
                    playerKill(gameController);
                    locationUpdate(arrayLocationX, this.getX());
                    moveCount = 0;
                }
            } else if (direction == 'a') {
                if (!checkTile(toTheLeft, currentTile) && !checkTile(above, currentTile)
                && moveCount < MAXTURNS) {
                    direction = 's';
                    move();
                } else if (!checkTile(above, currentTile)) {
                    this.setX(this.getX()+1);
                    playerKill(gameController);
                    locationUpdate(arrayLocationX, this.getX());
                    moveCount = 0;
                } else {
                    direction = 'w';
                    this.setY(this.getY()+1);
                    playerKill(gameController);
                    locationUpdate(arrayLocationY, this.getY());
                    moveCount = 0;
                }
            } else if (direction == 'd') {
                if (!checkTile(toTheRight, currentTile) && !checkTile(below, currentTile)
                && moveCount < MAXTURNS) {
                    direction = 'w';
                    move();
                } else if (!checkTile(below, currentTile)) {
                    this.setX(this.getX()+1);
                    playerKill(gameController);
                    locationUpdate(arrayLocationX, this.getX());
                    moveCount = 0;
                } else {
                    direction = 's';
                    this.setY(this.getY()-1);
                    playerKill(gameController);
                    locationUpdate(arrayLocationY, this.getY());
                    moveCount = 0;
                }
            }
        }
    }

    /**
     * draws the bug.
     * @param gc graphics context
     * @param x location x
     * @param y location y
     * @param size size
     */
    @Override
    public void draw(final GraphicsContext gc,
                     final double x,
                     final double y,
                     final double size) {
        gc.drawImage(BUG_IMAGE, x * size, y * size);
    }
}
