package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * The {@code Frog} class represents a Frog (monster) in the game.
 * @author idk
 * @version 1.0
 */
//TODO test more
    //seems to be working relatively well
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
        //saves the index that the monsters' x and y coordinates are stored
        //X is stored at 2 * the number of monsters since each monster stores two values and y is after that
        arrayLocationX = countMonsters*2;
        arrayLocationY = (countMonsters*2)+1;
        countMonsters++;
        monsterLocations.add(x);
        monsterLocations.add(y);
        FrogList.add(this);
    }

    public int getSpeed() {
        return speed;
    }

    public void event (int x, int y, int newX, int newY) {}

    //this move method will try and make the frog x equal to player x then do the same with y
    public void move() {
        int[] currentTile = {x, y};
        int[] checkLeft = {x-1, y};
        int[] checkRight = {x+1, y};
        int[] checkBelow = {x, y-1};
        int[] checkAbove = {x, y+1};
        Player player = (Player) LevelLoader.getEntityByClass(Player.class);
        int playerX = player.getX();
        int playerY = player.getY();
        if (x > playerX && checkTile(checkLeft, currentTile)) {
            x--;
            playerKill();
            locationUpdate(arrayLocationX, x);
            direction = 'a';
        } else if (x < playerX && checkTile(checkRight, currentTile)) {
            x++;
            playerKill();
            locationUpdate(arrayLocationX, x);
            direction = 'd';
        } else if (y > playerY && checkTile(checkBelow, currentTile)) {
            y--;
            playerKill();
            locationUpdate(arrayLocationY, y);
            direction = 's';
        } else if (y < playerY && checkTile(checkAbove, currentTile)) {
            y++;
            playerKill();
            locationUpdate(arrayLocationY, y);
            direction = 'w';
        } else if (direction == 'a' && checkTile(checkAbove, currentTile)) {
            y++;
            playerKill();
            locationUpdate(arrayLocationY, y);
            direction = 'w';
        } else if (direction == 'a' && checkTile(checkBelow, currentTile)) {
            y--;
            playerKill();
            locationUpdate(arrayLocationY, y);
            direction = 's';
        } else if (direction == 'a') {
            x++;
            playerKill();
            locationUpdate(arrayLocationX, x);
            direction = 'd';
        } else if (direction == 'w' && checkTile(checkLeft, currentTile)) {
            x--;
            playerKill();
            locationUpdate(arrayLocationX, x);
            direction = 'a';
        } else if (direction == 'w' && checkTile(checkRight, currentTile)) {
            x++;
            playerKill();
            locationUpdate(arrayLocationX, x);
            direction = 'd';
        } else if (direction == 'w') {
            y--;
            playerKill();
            locationUpdate(arrayLocationY, y);
            direction = 's';
        } else if (direction == 's' && checkTile(checkRight, currentTile)) {
            x++;
            playerKill();
            locationUpdate(arrayLocationX, x);
            direction = 'd';
        } else if (direction == 's' && checkTile(checkLeft, currentTile)) {
            x--;
            playerKill();
            locationUpdate(arrayLocationX, x);
            direction = 'a';
        } else if (direction == 's') {
            y++;
            playerKill();
            locationUpdate(arrayLocationY, y);
            direction = 'w';
        } else if (direction == 'd' && checkTile(checkBelow, currentTile)) {
            y--;
            playerKill();
            locationUpdate(arrayLocationY, y);
            direction = 's';
        } else if (direction == 'd' && checkTile(checkAbove, currentTile)) {
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
