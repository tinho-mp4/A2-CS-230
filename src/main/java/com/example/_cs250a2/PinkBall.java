package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * javadoc to go here
 */
public class PinkBall extends Monster {

    private static final Image BALL_IMAGE = new Image(Key.class.getResourceAsStream("sprites/ball.png"));
    public PinkBall(int speed, char startingDirection, int[] startingLocation) {
        super(startingLocation[0], startingLocation[1], startingDirection);
        this.speed = speed;
        direction = startingDirection;
        location = startingLocation;
        x = location[0];
        y = location[1];
        checkDirection(startingDirection);
        checkLocation(startingLocation);
        arrayLocationX = countMonsters;
        countMonsters++;
        arrayLocationY = countMonsters;
        countMonsters++;
        monsterLocations.add(x);
        monsterLocations.add(y);
    }

    public void move() {
        //w is up, s is down, a is left and d is right
        if (direction == 'w') {
            //next location is 1 tile above current position
            int[] locationNext = {x, y++};
            //method to check the tile is legal
            if (checkTile(locationNext)) {
                y++;
            } else {
                direction = 's';
                move();
            }
        } else if (direction == 's') {
            //next location is 1 tile below current position
            int[] locationNext = {x, y--};
            //check tile legality
            if (checkTile(locationNext)) {
                y--;
                playerKill();
            } else {
                direction = 'w';
                move();
            }
        } else if (direction == 'a') {
            int[] locationNext = {x--, y};
            if (checkTile(locationNext)) {
                x--;
                playerKill();
            } else {
                direction = 'd';
                move();
            }
        } else if (direction == 'd') {
            int[] locationNext = {x++, y};
            if (checkTile(locationNext)) {
                x++;
                playerKill();
            } else {
                direction = 'a';
                move();
            }
        }
    }

    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(BALL_IMAGE, x*size, y*size);
    }
}
