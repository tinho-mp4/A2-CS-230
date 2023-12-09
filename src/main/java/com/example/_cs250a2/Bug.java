package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * javadoc to go here
 */
//TODO test more
public class Bug extends Monster {

    private static final Image BUG_IMAGE = new Image(Key.class.getResourceAsStream("sprites/bug.png"));
    //which wall to 'hug' when it moves
    private final boolean left;
    private static int speed;

    public Bug(int ticks, char startingDirection, int[] startingLocation, boolean side) {
        super(startingLocation[0], startingLocation[1], startingDirection);
        speed = ticks;
        direction = startingDirection;
        location = startingLocation;
        x = location[0];
        y = location[1];
        left = side;
        checkDirection(startingDirection);
        checkLocation(startingLocation);
        //saves the index that the monsters' x and y coordinates are stored
        //X is stored at 2 * the number of monsters since each monster stores two values and y is after that
        arrayLocationX = countMonsters*2;
        arrayLocationY = (countMonsters*2)+1;
        countMonsters++;
        monsterLocations.add(x);
        monsterLocations.add(y);
        BugList.add(this);
    }

    public static int getSpeed() {
        return speed;
    }

    public void move() {
        int[] toTheLeft = {x+1, y};
        int[] above = {x, y-1};
        int[] below = {x, y+1};
        int[] toTheRight = {x-1, y};
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
                    y--;
                    playerKill();
                    locationUpdate(arrayLocationY, y);
                    //if both tiles are available it is on an outside corner and so
                    // turns (and moves in the new direction) to go around
                } else {
                    direction = 'a';
                    x++;
                    playerKill();
                    locationUpdate(arrayLocationX, x);
                }
            } else if (direction == 's') {
                if (!checkTile(below) && !checkTile(toTheRight)) {
                    direction = 'a';
                    move();
                } else if (!checkTile(toTheRight)) {
                    y++;
                    playerKill();
                    locationUpdate(arrayLocationY, y);
                } else {
                    direction = 'd';
                    x--;
                    playerKill();
                    locationUpdate(arrayLocationX, x);
                }
            } else if (direction == 'a') {
                if (!checkTile(toTheLeft) && !checkTile(below)) {
                    direction = 'w';
                    move();
                } else if (!checkTile(below)) {
                    x++;
                    playerKill();
                    locationUpdate(arrayLocationX, x);
                } else {
                    direction = 's';
                    y++;
                    playerKill();
                    locationUpdate(arrayLocationY, y);
                }
            } else if (direction == 'd') {
                if (!checkTile(toTheRight) && !checkTile(above)) {
                    direction = 's';
                    move();
                } else if (!checkTile(above)) {
                    x--;
                    playerKill();
                    locationUpdate(arrayLocationX, x);
                } else {
                    direction = 'w';
                    y--;
                    playerKill();
                    locationUpdate(arrayLocationY, y);
                }
            }
        } else {
            if (direction == 'w') {
                if (!checkTile(toTheRight) && !checkTile(above)) {
                    direction = 'a';
                    move();
                } else if (!checkTile(toTheRight)) {
                    y--;
                    playerKill();
                    locationUpdate(arrayLocationY, y);
                } else {
                    direction = 'd';
                    x--;
                    playerKill();
                    locationUpdate(arrayLocationX, x);
                }
            } else if (direction == 's') {
                if (!checkTile(toTheLeft) && !checkTile(below)) {
                    direction = 'd';
                    move();
                } else if (!checkTile(toTheLeft)) {
                    y--;
                    playerKill();
                    locationUpdate(arrayLocationY, y);
                } else {
                    direction = 'a';
                    x++;
                    playerKill();
                    locationUpdate(arrayLocationX, x);
                }
            } else if (direction == 'a') {
                if (!checkTile(toTheLeft) && !checkTile(above)) {
                    direction = 's';
                    move();
                } else if (!checkTile(above)) {
                    x++;
                    playerKill();
                    locationUpdate(arrayLocationX,x);
                } else {
                    direction = 'w';
                    y--;
                    playerKill();
                    locationUpdate(arrayLocationY, y);
                }
            } else if (direction == 'd') {
                if (!checkTile(toTheRight) && !checkTile(below)) {
                    direction = 'w';
                    move();
                } else if (!checkTile(below)) {
                    x--;
                    playerKill();
                    locationUpdate(arrayLocationX, x);
                } else {
                    direction = 's';
                    y++;
                    playerKill();
                    locationUpdate(arrayLocationY, y);
                }
            }
        }
    }

    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(BUG_IMAGE, x*size, y*size);
    }
}
