package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

/**
 * The {@code Bug} class represents a bug monster in the game. It extends the {@code Monster} class,
 * inheriting its basic properties and behaviors. This class specifically handles the appearance and
 * behavior of the bug monster within the game environment.
 *
 * @version 1.0
 * @author Finn P
 */
public class Bug extends Monster {

    /**
     * The image representation of the bug. This static final field holds the graphical representation
     * of the bug, used for rendering on the game canvas.
     */
    private static final Image BUG_IMAGE
            = new Image(Objects.requireNonNull(Bug.class.getResourceAsStream("sprites/bug.png")));

    /**
     * Indicates the wall-following behavior of the bug. If true, the bug 'hugs' the left wall;
     * if false, it 'hugs' the right wall.
     */
    private final boolean left;

    /**
     * The controller managing the game logic and state, used for interactions within the game.
     */
    private final GameController gameController;

    /**
     * Constructs a Bug monster with specified parameters. Initializes the monster with a speed (ticks),
     * starting direction, location, wall-following behavior, and a reference to the game controller.
     * It also checks the validity of the starting direction and location, and updates the monster management lists.
     *
     * @param ticks            The speed of the bug, defined as the number of ticks between movements.
     * @param startingDirection The initial direction of the bug (W, A, S, D).
     * @param startingLocation  An array containing the starting x and y coordinates of the bug in the game grid.
     * @param side             Indicates the wall-following behavior (true for left, false for right).
     * @param gameController   The GameController instance managing game logic and state.
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
        monsterList.add(this);
    }

    /**
     * Retrieves the speed of the monster. Speed is defined by the number of game ticks between each movement.
     *
     * @return The speed of the monster.
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Placeholder or unimplemented method intended for handling events involving a change in position.
     * Currently, this method is empty and does not perform any actions. It should be overridden or
     * implemented in subclasses to provide specific event handling logic based on the provided coordinates.
     *
     * @param x    The original x-coordinate before the event.
     * @param y    The original y-coordinate before the event.
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
     * Moves the bug according to its movement algorithm. The bug's movement is determined by its current direction,
     * the side it 'hugs' (left or right), and the surrounding environment. It checks for possible moves and adjusts
     * its direction and position accordingly. The method also handles interactions with the player and updates
     * the bug's location in the game.

     * The movement logic involves checking adjacent tiles based on the current direction and the side the bug
     * is 'hugging'. If a move is not possible in the desired direction, the bug turns and tries a different direction.
     * This process is repeated until a valid move is made or the maximum number of turns is reached.
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
     * Draws the bug on the provided GraphicsContext. The bug is rendered at the specified
     * coordinates and scaled to the given size. This method utilizes the BUG_IMAGE to visually
     * represent the bug on the game canvas.
     *
     * @param gc   The GraphicsContext used for drawing the bug.
     * @param x    The x-coordinate on the canvas where the bug will be drawn, multiplied by the size for scaling.
     * @param y    The y-coordinate on the canvas where the bug will be drawn, multiplied by the size for scaling.
     * @param size The size to which the bug image will be scaled. This adjusts the bug's size relative to the game grid.
     */
    @Override
    public void draw(final GraphicsContext gc,
                     final double x,
                     final double y,
                     final double size) {
        gc.drawImage(BUG_IMAGE, x * size, y * size);
    }
}
