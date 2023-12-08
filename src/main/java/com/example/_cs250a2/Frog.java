package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * javadoc to go here
 */
//TODO test when level available
public class Frog extends Monster {

    private static int speed;

    private static final Image FROG_IMAGE = new Image(Key.class.getResourceAsStream("sprites/frog.png"));
    public Frog (int ticks, char startingDirection, int[] startingLocation) {
        super(startingLocation[0], startingLocation[1], startingDirection);
        allowedTiles.remove("Trap");
        speed = ticks;
        direction = startingDirection;
        location = startingLocation;
        x = location[0];
        y = location[1];
        checkDirection(startingDirection);
        checkLocation(startingLocation);
        arrayLocationX = countMonsters;
        countMonsters++;
        arrayLocationY = countMonsters;
        countMonsters++;
    }


    //this move method will try and make the frog x equal to player x then do the same with y
    public void move() {
        int[] checkLeft = {x--, y};
        int[] checkRight = {x++, y};
        int[] checkBelow = {x, y--};
        int[] checkAbove = {x, y++};
        int playerX = Player.getX();
        int playerY = Player.getY();
        if (x > playerX && checkTile(checkLeft)) {
            x--;
            playerKill();
            locationUpdate(arrayLocationX, x);
            direction = 'a';
        } else if (x < playerX && checkTile(checkRight)) {
            x++;
            playerKill();
            locationUpdate(arrayLocationX, x);
            direction = 'd';
        } else if (y > playerY && checkTile(checkBelow)) {
            y--;
            playerKill();
            locationUpdate(arrayLocationY, y);
            direction = 's';
        } else if (y < playerY && checkTile(checkAbove)) {
            y++;
            playerKill();
            locationUpdate(arrayLocationY, y);
            direction = 'w';
        } else if (direction == 'a' && checkTile(checkAbove)) {
            y++;
            playerKill();
            locationUpdate(arrayLocationY, y);
            direction = 'w';
        } else if (direction == 'a' && checkTile(checkBelow)) {
            y--;
            playerKill();
            locationUpdate(arrayLocationY, y);
            direction = 's';
        } else if (direction == 'a') {
            x++;
            playerKill();
            locationUpdate(arrayLocationX, x);
            direction = 'd';
        } else if (direction == 'w' && checkTile(checkLeft)) {
            x--;
            playerKill();
            locationUpdate(arrayLocationX, x);
            direction = 'a';
        } else if (direction == 'w' && checkTile(checkRight)) {
            x++;
            playerKill();
            locationUpdate(arrayLocationX, x);
            direction = 'd';
        } else if (direction == 'w') {
            y--;
            playerKill();
            locationUpdate(arrayLocationY, y);
            direction = 's';
        } else if (direction == 's' && checkTile(checkRight)) {
            x++;
            playerKill();
            locationUpdate(arrayLocationX, x);
            direction = 'd';
        } else if (direction == 's' && checkTile(checkLeft)) {
            x--;
            playerKill();
            locationUpdate(arrayLocationX, x);
            direction = 'a';
        } else if (direction == 's') {
            y++;
            playerKill();
            locationUpdate(arrayLocationY, y);
            direction = 'w';
        } else if (direction == 'd' && checkTile(checkBelow)) {
            y--;
            playerKill();
            locationUpdate(arrayLocationY, y);
            direction = 's';
        } else if (direction == 'd' && checkTile(checkAbove)) {
            y++;
            playerKill();
            locationUpdate(arrayLocationY, y);
            direction = 'w';
        } else if (direction == 'd') {
            y--;
            playerKill();
            locationUpdate(arrayLocationY, y);
            direction = 'a';
        }
    }

    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(FROG_IMAGE, x*size, y*size);
    }
}
