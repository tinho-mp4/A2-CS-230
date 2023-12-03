package com.example._cs250a2;

/**
 * javadoc to go here
 */
//TODO write pathfind and move
public class Frog extends Monster {

    public Frog (int speed, char startingDirection, int[] startingLocation) {
        allowedTiles.remove("Trap");
        this.speed = speed;
        direction = startingDirection;
        location = startingLocation;
        monsterX = location[0];
        monsterY = location[1];
        checkDirection();
        checkLocation();
    }

    //pathfind will tell the move method where to go
    private void pathFind() {

    }

    public void move() {

    }
}
