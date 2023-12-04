package com.example._cs250a2;

import java.util.ArrayList;

/**
 * javadoc to go here
 */
public class Bug extends Monster {
    //which wall to 'hug' when it moves
    private final boolean left;

    public Bug(int speed, char startingDirection, int[] startingLocation, boolean side) {
        this.speed = speed;
        direction = startingDirection;
        location = startingLocation;
        monsterX = location[0];
        monsterY = location[1];
        left = side;
        checkDirection(startingDirection);
        checkLocation(startingLocation);
    }

    public void move() {
        int[] toTheLeft = {monsterX--, monsterY};
        int[] above = {monsterX, monsterY++};
        int[] below = {monsterX, monsterY--};
        int[] toTheRight = {monsterX++, monsterY};
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
                    monsterY++;
                    playerKill();
                    //if both tiles are available it is on an outside corner and so
                    // turns (and moves in the new direction) to go around
                } else {
                    direction = 'a';
                    monsterX--;
                    playerKill();
                }
            } else if (direction == 's') {
                if (!checkTile(below) && !checkTile(toTheRight)) {
                    direction = 'a';
                    move();
                } else if (!checkTile(toTheRight)) {
                    monsterY--;
                    playerKill();
                } else {
                    direction = 'd';
                    monsterX++;
                    playerKill();
                }
            } else if (direction == 'a') {
                if (!checkTile(toTheLeft) && !checkTile(below)) {
                    direction = 'w';
                    move();
                } else if (!checkTile(below)) {
                    monsterX--;
                    playerKill();
                } else {
                    direction = 's';
                    monsterY--;
                    playerKill();
                }
            } else if (direction == 'd') {
                if (!checkTile(toTheRight) && !checkTile(above)) {
                    direction = 's';
                    move();
                } else if (!checkTile(above)) {
                    monsterX++;
                    playerKill();
                } else {
                    direction = 'w';
                    monsterY++;
                    playerKill();
                }
            }
        } else {
            if (direction == 'w') {
                if (!checkTile(toTheRight) && !checkTile(above)) {
                    direction = 'a';
                    move();
                } else if (!checkTile(toTheRight)) {
                    monsterY++;
                    playerKill();
                } else {
                    direction = 'd';
                    monsterX++;
                    playerKill();
                }
            } else if (direction == 's') {
                if (!checkTile(toTheLeft) && !checkTile(below)) {
                    direction = 'd';
                    move();
                } else if (!checkTile(toTheLeft)) {
                    monsterY++;
                    playerKill();
                } else {
                    direction = 'a';
                    monsterX--;
                    playerKill();
                }
            } else if (direction == 'a') {
                if (!checkTile(toTheLeft) && !checkTile(above)) {
                    direction = 's';
                    move();
                } else if (!checkTile(above)) {
                    monsterX--;
                    playerKill();
                } else {
                    direction = 'w';
                    monsterY++;
                    playerKill();
                }
            } else if (direction == 'd') {
                if (!checkTile(toTheRight) && !checkTile(below)) {
                    direction = 'w';
                    move();
                } else if (!checkTile(below)) {
                    monsterX++;
                    playerKill();
                } else {
                    direction = 's';
                    monsterY--;
                    playerKill();
                }
            }
        }
    }
}
