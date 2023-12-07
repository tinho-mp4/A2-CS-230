package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * javadoc to go here
 */
public class Bug extends Monster {
    //which wall to 'hug' when it moves
    private final boolean left;

    private static final Image BUG_IMAGE = new Image(Key.class.getResourceAsStream("sprites/bug.png"));

    public Bug(int speed, char startingDirection, int[] startingLocation, boolean side) {
        super(startingLocation[0], startingLocation[1], startingDirection);
        this.speed = speed;
        direction = startingDirection;
        location = startingLocation;
        x = location[0];
        y = location[1];
        left = side;
        checkDirection(startingDirection);
        checkLocation(startingLocation);
        arrayLocationX = countMonsters;
        countMonsters++;
        arrayLocationY = countMonsters;
        countMonsters++;
    }

    public void move() {
        int[] toTheLeft = {x--, y};
        int[] above = {x, y++};
        int[] below = {x, y--};
        int[] toTheRight = {x++, y};
        //checks which side to keep the bug on
        if (left) {
            if (direction == 'w') {
                //the bug wants to go forwards or to its' left (or right), checks if it can
                if (!checkTile(toTheLeft) && !checkTile(above)) {
                    //bug cant go where it wants to, so it turns right and tries again
                    direction = 'd';
                    move();
                    //it can either go one way or both, if it cant go left it goes in its' current facing
                } else if (!(checkTile(toTheLeft))){
                    y++;
                    playerKill();
                    //if both tiles are available it is on an outside corner and so
                    // turns (and moves in the new direction) to go around
                } else {
                    direction = 'a';
                    x--;
                    playerKill();
                }
            } else if (direction == 's') {
                if (!checkTile(below) && !checkTile(toTheRight)) {
                    direction = 'a';
                    move();
                } else if (!checkTile(toTheRight)) {
                    y--;
                    playerKill();
                } else {
                    direction = 'd';
                    x++;
                    playerKill();
                }
            } else if (direction == 'a') {
                if (!checkTile(toTheLeft) && !checkTile(below)) {
                    direction = 'w';
                    move();
                } else if (!checkTile(below)) {
                    x--;
                    playerKill();
                } else {
                    direction = 's';
                    y--;
                    playerKill();
                }
            } else if (direction == 'd') {
                if (!checkTile(toTheRight) && !checkTile(above)) {
                    direction = 's';
                    move();
                } else if (!checkTile(above)) {
                    x++;
                    playerKill();
                } else {
                    direction = 'w';
                    y++;
                    playerKill();
                }
            }
        } else {
            if (direction == 'w') {
                if (!checkTile(toTheRight) && !checkTile(above)) {
                    direction = 'a';
                    move();
                } else if (!checkTile(toTheRight)) {
                    y++;
                    playerKill();
                } else {
                    direction = 'd';
                    x++;
                    playerKill();
                }
            } else if (direction == 's') {
                if (!checkTile(toTheLeft) && !checkTile(below)) {
                    direction = 'd';
                    move();
                } else if (!checkTile(toTheLeft)) {
                    y++;
                    playerKill();
                } else {
                    direction = 'a';
                    x--;
                    playerKill();
                }
            } else if (direction == 'a') {
                if (!checkTile(toTheLeft) && !checkTile(above)) {
                    direction = 's';
                    move();
                } else if (!checkTile(above)) {
                    x--;
                    playerKill();
                } else {
                    direction = 'w';
                    y++;
                    playerKill();
                }
            } else if (direction == 'd') {
                if (!checkTile(toTheRight) && !checkTile(below)) {
                    direction = 'w';
                    move();
                } else if (!checkTile(below)) {
                    x++;
                    playerKill();
                } else {
                    direction = 's';
                    y--;
                    playerKill();
                }
            }
        }
    }

    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(BUG_IMAGE, x*size, y*size);
    }
}
