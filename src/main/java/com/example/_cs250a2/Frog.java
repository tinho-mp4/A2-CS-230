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

    //this move method will try and make the frog x equal to player x then do the same with y
    public void move() {
        int[] checkLeft = {monsterX--,monsterY};
        if (monsterX > Player.getX() && checkTile(checkLeft)) {

        }
    }
}
