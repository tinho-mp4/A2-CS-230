package com.example._cs250a2;

import java.util.ArrayList;

/**
 * javadoc to go here
 */
//TODO finish move
public class Bug extends Monster {
    //which wall to 'hug' when it moves
    private boolean left;

    public Bug(ArrayList<Tile> tiles, int speed, char startingDirection, int[] startingLocation, boolean side) {
        blockingTiles = tiles;
        this.speed = speed;
        direction = startingDirection;
        location = startingLocation;
        monsterX = location[0];
        monsterY = location[1];
        left = side;
        checkDirection();
        checkLocation();
    }

    public void move() {
        //checks which side to keep the bug on
        if (left) {
            if (direction == 'w') {

            } else if (direction == 's') {

            } else if (direction == 'a') {

            } else if (direction == 'd') {

            }
        } else {

        }
    }
}
