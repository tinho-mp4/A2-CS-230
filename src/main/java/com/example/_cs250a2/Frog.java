package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * The {@code Frog} class represents a Frog (monster) in the game.
 * @author Finn P
 * @version 1.0
 */
public class Frog extends Monster {

    private static final Image FROG_IMAGE = new Image(Key.class.getResourceAsStream("sprites/frog.png"));
    private static int speed;

    private final GameController gameController;
    private boolean blocked = false;

    /**
     * constructor for frog, checks location and direction as its called.
     * @param ticks speed.
     * @param startingDirection direction.
     * @param startingLocation location.
     * @param gameController gameController.
     */
    public Frog(int ticks, char startingDirection, int[] startingLocation, GameController gameController) {
        super(startingLocation[0], startingLocation[1], startingDirection);
        allowedTiles.remove("Trap");
        speed = ticks;
        direction = startingDirection;
        location = startingLocation;
        this.gameController = gameController;
        this.setX(location[0]);
        this.setY(location[1]);
        checkDirection(startingDirection);
        checkLocation(startingLocation);
        //saves the index that the monsters' x and y coordinates are stored
        //X is stored at 2 * the number of monsters since each monster stores two values and y is after that
        arrayLocationX = countMonsters*2;
        arrayLocationY = (countMonsters*2)+1;
        countMonsters++;
        monsterLocations.add(this.getX());
        monsterLocations.add(this.getY());
    }

    /**
     * returns the frogs' speed.
     * @return speed.
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * event.
     * @param x    The current x-coordinate.
     * @param y    The current y-coordinate.
     * @param newX The new x-coordinate after the event.
     * @param newY The new y-coordinate after the event.
     */
    public void event(int x, int y, int newX, int newY) {
        Tile tile = LevelLoader.getTile(x, y);
        if (tile instanceof Button) {
            ((Button) tile).checkIfEntityOnButton();
        }
    }

    //this move method will try and make the frog x equal to player x then do the same with y

    /**
     * moves the frog towards the player.
     */
    public void move() {
        event(this.getX(), this.getY(), this.getX(), this.getY());
        int[] currentTile = {this.getX(), this.getY()};
        int[] checkLeft = {this.getX() - 1, this.getY()};
        int[] checkRight = {this.getX() + 1, this.getY()};
        int[] checkBelow = {this.getX(), this.getY() - 1};
        int[] checkAbove = {this.getX(), this.getY() + 1};
        Player player = (Player) LevelLoader.getEntityByClass(Player.class);
        int playerX = player.getX();
        int playerY = player.getY();
        if (!blocked) {
            //if frog is right of the player.... wants to go left
            if (this.getX() > playerX && checkTile(checkLeft, currentTile)) {
                this.setX(this.getX() - 1);
                playerKill(gameController);
                locationUpdate(arrayLocationX, this.getX());
                direction = 'a';
                //if frog is left of the player.... wants to go right
            } else if (this.getX() < playerX && checkTile(checkRight, currentTile)) {
                this.setX(this.getX() + 1);
                playerKill(gameController);
                locationUpdate(arrayLocationX, this.getX());
                direction = 'd';
                //if frog is above player.... wants to go down
            } else if (this.getY() > playerY && checkTile(checkBelow, currentTile)) {
                this.setY(this.getY() - 1);
                playerKill(gameController);
                locationUpdate(arrayLocationY, this.getY());
                direction = 's';
                //if frog is below player.... wants to go up
            } else if (this.getY() < playerY && checkTile(checkAbove, currentTile)) {
                this.setY(this.getY() + 1);
                playerKill(gameController);
                locationUpdate(arrayLocationY, this.getY());
                direction = 'w';
                //checks if the frog is on the player (i think this is redundant however)
            } else if (this.getX() == playerX && this.getY() == playerY) {
                playerKill(gameController);
            } else {
                blocked = true;
            }
//if the frog is blocked
        } else {
            if (direction == 'a' && checkTile(checkAbove, currentTile)) {
                this.setY(this.getY() + 1);
                playerKill(gameController);
                locationUpdate(arrayLocationY, this.getY());
                direction = 'w';
                blocked = false;
            } else if (direction == 'a' && checkTile(checkBelow, currentTile)) {
                this.setY(this.getY() - 1);
                playerKill(gameController);
                locationUpdate(arrayLocationY, this.getY());
                direction = 's';
                blocked = false;
            } else if (direction == 'a') {
                this.setX(this.getX() + 1);
                playerKill(gameController);
                locationUpdate(arrayLocationX, this.getX());
                direction = 'd';
                blocked = false;
            } else if (direction == 'w' && checkTile(checkLeft, currentTile)) {
                this.setX(this.getX() - 1);
                playerKill(gameController);
                locationUpdate(arrayLocationX, this.getX());
                direction = 'a';
                blocked = false;
            } else if (direction == 'w' && checkTile(checkRight, currentTile)) {
                this.setX(this.getX() + 1);
                playerKill(gameController);
                locationUpdate(arrayLocationX, this.getX());
                direction = 'd';
                blocked = false;
            } else if (direction == 'w') {
                this.setY(this.getY() - 1);
                playerKill(gameController);
                locationUpdate(arrayLocationY, this.getY());
                direction = 's';
                blocked = false;
            } else if (direction == 's' && checkTile(checkRight, currentTile)) {
                this.setX(this.getX() + 1);
                playerKill(gameController);
                locationUpdate(arrayLocationX, this.getX());
                direction = 'd';
                blocked = false;
            } else if (direction == 's' && checkTile(checkLeft, currentTile)) {
                this.setX(this.getX() - 1);
                playerKill(gameController);
                locationUpdate(arrayLocationX, this.getX());
                direction = 'a';
                blocked = false;
            } else if (direction == 's') {
                this.setY(this.getY() + 1);
                playerKill(gameController);
                locationUpdate(arrayLocationY, this.getY());
                direction = 'w';
                blocked = false;
            } else if (direction == 'd' && checkTile(checkBelow, currentTile)) {
                this.setY(this.getY() - 1);
                playerKill(gameController);
                locationUpdate(arrayLocationY, this.getY());
                direction = 's';
                blocked = false;
            } else if (direction == 'd' && checkTile(checkAbove, currentTile)) {
                this.setY(this.getY() + 1);
                playerKill(gameController);
                locationUpdate(arrayLocationY, this.getY());
                direction = 'w';
                blocked = false;
            } else if (direction == 'd') {
                this.setY(this.getY() - 1);
                playerKill(gameController);
                locationUpdate(arrayLocationY, this.getY());
                direction = 'a';
                blocked = false;
            }
        }
    }

    /**
     * draws the frog.
     * @param gc   The GraphicsContext on which to draw the entity.
     * @param x    The x-coordinate on the canvas.
     * @param y    The y-coordinate on the canvas.
     * @param size The size to draw the entity.
     */
    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(FROG_IMAGE, x*size, y*size);
    }
}
