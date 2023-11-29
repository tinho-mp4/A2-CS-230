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
    }

    //pink ball move method to be called by tick
    
    public void move() {
        //w is up
        if (direction == 'w') {
            //next location is 1 tile above current position
            int[] locationNext = {monsterX, monsterY++};
            //method to check the tile is legal
            if (checkTile(locationNext)) {
                monsterY = monsterY++;
            } else {
                direction = 's';
                move();
            }
        }
    }
}
