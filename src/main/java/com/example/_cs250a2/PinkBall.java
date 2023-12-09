package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * javadoc to go here
 */
//TODO test
public class PinkBall extends Monster {

    private static int speed;

    private static final Image BALL_IMAGE = new Image(Key.class.getResourceAsStream("sprites/pinkBall.png"));
    public PinkBall(int ticks, char startingDirection, int[] startingLocation) {
        super(startingLocation[0], startingLocation[1], startingDirection);
        speed = ticks;
        direction = startingDirection;
        location = startingLocation;
        x = location[0];
        y = location[1];
        checkDirection(startingDirection);
        checkLocation(startingLocation);
        //saves the index that the monsters' x and y coordinates are stored
        //X is stored at 2 * the number of monsters since each monster stores two values and y is after that
        arrayLocationX = countMonsters*2;
        arrayLocationY = (countMonsters*2)+1;
        countMonsters++;
        monsterLocations.add(x);
        monsterLocations.add(y);
        PinkBallList.add(this);
    }

    public static int getSpeed() {
        return speed;
    }

    public void move() {
        //w is up, s is down, a is left and d is right
        if (direction == 'w') {
            //next location is 1 tile above current position
            int[] locationNext = {x, y+1};
            //method to check the tile is legal
            if (checkTile(locationNext)) {
                y++;
                locationUpdate(arrayLocationY, y);
                playerKill();
            } else {
                direction = 's';
                move();
            }
        } else if (direction == 's') {
            //next location is 1 tile below current position
            int[] locationNext = {x, y-1};
            //check tile legality
            if (checkTile(locationNext)) {
                y--;
                playerKill();
                locationUpdate(arrayLocationY, y);
            } else {
                direction = 'w';
                move();
            }
        } else if (direction == 'a') {
            int[] locationNext = {x-1, y};
            if (checkTile(locationNext)) {
                x--;
                playerKill();
                locationUpdate(arrayLocationX, x);
            } else {
                direction = 'd';
                move();
            }
        } else if (direction == 'd') {
            int[] locationNext = {x+1, y};
            if (checkTile(locationNext)) {
                x++;
                playerKill();
                locationUpdate(arrayLocationX, x);
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
