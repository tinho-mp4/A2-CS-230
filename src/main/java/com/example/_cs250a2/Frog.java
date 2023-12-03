package com.example._cs250a2;

/**
 * javadoc to go here
 */
//TODO test when level available
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
        int[] checkLeft = {monsterX--, monsterY};
        int[] checkRight = {monsterX++, monsterY};
        int[] checkBelow = {monsterX, monsterY--};
        int[] checkAbove = {monsterX, monsterY++};
        int playerX = Player.getX;
        int playerY = Player.getY;
        if (monsterX > playerX && checkTile(checkLeft)) {
            monsterX--;
            direction = 'a';
        } else if (monsterX < playerX && checkTile(checkRight)) {
            monsterX++;
            direction = 'd';
        } else if (monsterY > playerY && checkTile(checkBelow)) {
            monsterY--;
            direction = 's';
        } else if (monsterY < playerY && checkTile(checkAbove)) {
            monsterY++;
            direction = 'w';
        } else if (direction == 'a' && checkTile(checkAbove)) {
            monsterY++;
            direction = 'w';
        } else if (direction == 'a' && checkTile(checkBelow)) {
            monsterY--;
            direction = 's';
        } else if (direction == 'a') {
            monsterX++;
            direction = 'd';
        } else if (direction == 'w' && checkTile(checkLeft)) {
            monsterX--;
            direction = 'a';
        } else if (direction == 'w' && checkTile(checkRight)) {
            monsterX++;
            direction = 'd';
        } else if (direction == 'w') {
            monsterY--;
            direction = 's';
        } else if (direction == 's' && checkTile(checkRight)) {
            monsterX++;
            direction = 'd';
        } else if (direction == 's' && checkTile(checkLeft)) {
            monsterX--;
            direction = 'a';
        } else if (direction == 's') {
            monsterY++;
            direction = 'w';
        } else if (direction == 'd' && checkTile(checkBelow)) {
            monsterY--;
            direction = 's';
        } else if (direction == 'd' && checkTile(checkAbove)) {
            monsterY++;
            direction = 'w';
        } else if (direction == 'd') {
            monsterY--;
            direction = 'a';
        }
    }
}
