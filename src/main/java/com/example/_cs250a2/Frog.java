package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * javadoc to go here
 */
//TODO test when level available
public class Frog extends Monster {

    private static final Image FROG_IMAGE = new Image(Key.class.getResourceAsStream("sprites/frog.png"));
    public Frog (int speed, char startingDirection, int[] startingLocation) {
        super(startingLocation[0], startingLocation[1], startingDirection);
        allowedTiles.remove("Trap");
        this.speed = speed;
        direction = startingDirection;
        location = startingLocation;
        x = location[0];
        y = location[1];
        checkDirection(startingDirection);
        checkLocation(startingLocation);
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
            direction = 'a';
        } else if (x < playerX && checkTile(checkRight)) {
            x++;
            direction = 'd';
        } else if (y > playerY && checkTile(checkBelow)) {
            y--;
            direction = 's';
        } else if (y < playerY && checkTile(checkAbove)) {
            y++;
            direction = 'w';
        } else if (direction == 'a' && checkTile(checkAbove)) {
            y++;
            direction = 'w';
        } else if (direction == 'a' && checkTile(checkBelow)) {
            y--;
            direction = 's';
        } else if (direction == 'a') {
            x++;
            direction = 'd';
        } else if (direction == 'w' && checkTile(checkLeft)) {
            x--;
            direction = 'a';
        } else if (direction == 'w' && checkTile(checkRight)) {
            x++;
            direction = 'd';
        } else if (direction == 'w') {
            y--;
            direction = 's';
        } else if (direction == 's' && checkTile(checkRight)) {
            x++;
            direction = 'd';
        } else if (direction == 's' && checkTile(checkLeft)) {
            x--;
            direction = 'a';
        } else if (direction == 's') {
            y++;
            direction = 'w';
        } else if (direction == 'd' && checkTile(checkBelow)) {
            y--;
            direction = 's';
        } else if (direction == 'd' && checkTile(checkAbove)) {
            y++;
            direction = 'w';
        } else if (direction == 'd') {
            y--;
            direction = 'a';
        }
    }

    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(FROG_IMAGE, x*size, y*size);
    }
}
