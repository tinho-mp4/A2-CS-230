package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

/**
 * Represents a pink ball (monster) in the game.
 * This class extends {@code Monster} and manages the behavior and rendering of the pink ball.
 *
 * @author Finn
 */
//TODO test
public class PinkBall extends Monster {

    /**
     * Speed of pink ball monsters.
     */
    private static int speed;

    /**
     * Image for pink ball monsters.
     */
    private static final Image BALL_IMAGE =
            new Image(Objects.requireNonNull(Key.class.getResourceAsStream("sprites/pinkBall.png")));

    /**
     * Constructs a PinkBall with specified speed, starting direction, and starting location.
     *
     * @param ticks             The speed of the pink ball.
     * @param startingDirection The initial direction of the pink ball.
     * @param startingLocation  The starting coordinates of the pink ball.
     */
    public PinkBall(int ticks, char startingDirection, int[] startingLocation) {
        super(startingLocation[0], startingLocation[1], startingDirection);
        speed = ticks;
        direction = startingDirection;
        location = startingLocation;
        checkDirection(direction);
        checkLocation(location);
        arrayLocationX = countMonsters*2;
        arrayLocationY = countMonsters*2 + 1;
        countMonsters++;
        monsterLocations.add(this.getX());
        monsterLocations.add(this.getY());
        PinkBallList.add(this);
    }

    /**
     * Handles the events triggered by the pink ball in the game.
     *
     * @param x    The current x-coordinate of the pink ball.
     * @param y    The current y-coordinate of the pink ball.
     * @param newX The new x-coordinate.
     * @param newY The new y-coordinate.
     */
    public void event(int x, int y, int newX, int newY) {}

    /**
     * Gets the speed of the pink ball.
     *
     * @return The speed of the pink ball.
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Moves the pink ball according to its logic in the game.
     */
    public void move() {
        int[] currentTile = {this.getX(), this.getY()};
        //w is up, s is down, an is left and d is right
        if (direction == 'w') {
            //next location is 1 tile above current position
            int[] locationNext = {this.getX(), this.getY()+1};
            //method to check the tile is legal
            if (checkTile(locationNext, currentTile)) {
                this.setY(this.getY()+1);
                locationUpdate(arrayLocationY, this.getY());
                playerKill();
            } else {
                direction = 's';
                move();
            }
        } else if (direction == 's') {
            //next location is 1 tile below current position
            int[] locationNext = {this.getX(), this.getY()-1};
            //check tile legality
            if (checkTile(locationNext, currentTile)) {
                this.setY(this.getY()-1);
                playerKill();
                locationUpdate(arrayLocationY, this.getY());
            } else {
                direction = 'w';
                move();
            }
        } else if (direction == 'a') {
            int[] locationNext = {this.getX()-1, this.getY()};
            if (checkTile(locationNext, currentTile)) {
                this.setX(this.getX()-1);
                playerKill();
                locationUpdate(arrayLocationX, this.getX());
            } else {
                direction = 'd';
                move();
            }
        } else if (direction == 'd') {
            int[] locationNext = {this.getX()+1, this.getY()};
            if (checkTile(locationNext, currentTile)) {
                this.setX(this.getX()+1);
                playerKill();
                locationUpdate(arrayLocationX, this.getX());
            } else {
                direction = 'a';
                move();
            }
        }
    }

    /**
     * Draws the pink ball on the given graphics context.
     *
     * @param gc   The graphics context to draw on.
     * @param x    The x-coordinate for drawing.
     * @param y    The y-coordinate for drawing.
     * @param size The size of the pink ball image.
     */
    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(BALL_IMAGE, x*size, y*size);
    }
}
