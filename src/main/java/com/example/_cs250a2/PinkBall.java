package com.example._cs250a2;

import java.util.ArrayList;

/**
 * javadoc to go here
 */
public class PinkBall extends Monster {

    //pinkBall's own constructor
    public PinkBall(ArrayList<Tile> tiles, int speed, char startingDirection, int[] startingLocation) {
        blockingTiles = tiles;
        this.speed = speed;
        direction = startingDirection;
        location = startingLocation;
        monsterX = location[0];
        monsterY = location[1];
        checkDirection();
        checkLocation();
    }

    //pink ball move method to be called by tick

    public void move() {

        //w is up, s is down, a is left and d is right
        if (direction == 'w') {
            //next location is 1 tile above current position
            int[] locationNext = {monsterX, monsterY++};
            //method to check the tile is legal
            if (checkTile(locationNext)) {
                monsterY++;

            } else {
                direction = 's';
                move();
            }

        } else if (direction == 's') {
            //next location is 1 tile below current position
            int[] locationNext = {monsterX, monsterY--};
            //check tile legality
            if (checkTile(locationNext)) {
                monsterY--;
            } else {
                direction = 'w';
                move();
            }

        } else if (direction == 'a') {
            int[] locationNext = {monsterX--, monsterY};
            if (checkTile(locationNext)) {
                monsterX--;
            } else {
                direction = 'd';
                move();
            }

        } else if (direction == 'd') {
            int[] locationNext = {monsterX++, monsterY};
            if (checkTile(locationNext)) {
                monsterX++;
            } else {
                direction = 'a';
                move();
            }
    }
    }
}
