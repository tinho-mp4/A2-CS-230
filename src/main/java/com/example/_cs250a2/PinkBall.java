package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * The {@code PinkBall} class represents a pink ball (monster) in the game
 * @author Finn P
 * @version 1.0
 */
//TODO doesnt move after dying and starting again
public class PinkBall extends Monster {

    private static final Image BALL_IMAGE = new Image(Key.class.getResourceAsStream("sprites/pinkBall.png"));
    private static int speed;

    private final GameController gameController;
    public PinkBall(int ticks, char startingDirection, int[] startingLocation, GameController gameController) {
        super(startingLocation[0], startingLocation[1], startingDirection);
        speed = ticks;
        direction = startingDirection;
        location = startingLocation;
        this.gameController = gameController;
        checkDirection(direction);
        checkLocation(location);
        arrayLocationX = countMonsters*2;
        arrayLocationY = countMonsters*2 + 1;
        countMonsters++;
        monsterLocations.add(this.getX());
        monsterLocations.add(this.getY());
        monsterList.add(this);
    }

    public void event (int x, int y, int newX, int newY) {}
    public int getSpeed() {
        return speed;
    }

    public void move() {
        moveCount++;
        int[] currentTile = {this.getX(), this.getY()};
        //w is up, s is down, a is left and d is right
        if (direction == 'w') {
            //next location is 1 tile above current position
            int[] locationNext = {this.getX(), this.getY()+1};
            //method to check the tile is legal
            if (checkTile(locationNext, currentTile)) {
                this.setY(this.getY()+1);
                locationUpdate(arrayLocationY, this.getY());
                playerKill(gameController);
                moveCount = 0;
            } else if (moveCount < MAXTURNS){
                direction = 's';
                move();
            } else {
                moveCount = 0;
            }
        } else if (direction == 's') {
            //next location is 1 tile below current position
            int[] locationNext = {this.getX(), this.getY()-1};
            //check tile legality
            if (checkTile(locationNext, currentTile)) {
                this.setY(this.getY()-1);
                playerKill(gameController);
                locationUpdate(arrayLocationY, this.getY());
            } else if (moveCount < MAXTURNS){
                direction = 'w';
                move();
            } else {
                moveCount = 0;
            }
        } else if (direction == 'a') {
            int[] locationNext = {this.getX()-1, this.getY()};
            if (checkTile(locationNext, currentTile)) {
                this.setX(this.getX()-1);
                playerKill(gameController);
                locationUpdate(arrayLocationX, this.getX());
            } else if (moveCount < MAXTURNS){
                direction = 'd';
                move();
            } else {
                moveCount = 0;
            }
        } else if (direction == 'd') {
            int[] locationNext = {this.getX() + 1, this.getY()};
            if (checkTile(locationNext, currentTile)) {
                this.setX(this.getX() + 1);
                playerKill(gameController);
                locationUpdate(arrayLocationX, this.getX());
            } else if (moveCount < MAXTURNS){
                direction = 'a';
                move();
            } else {
                moveCount = 0;
            }
        }
        if (moveCount == MAXTURNS+1) {
            moveCount = 0;
        }
    }

    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(BALL_IMAGE, x*size, y*size);
    }
}
