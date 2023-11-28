package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;

public class Block extends Tile {
    private int X;
    private int Y;
    private boolean pushableBlock;
    public Block(int X, int Y) {
        super("block", X, Y);
        pushableBlock = true;
    }

    /**
     * pushes the block onto a new postion
     * @param gc The graphics context used to draw the new block tile
     * @param newX The new X position of the block tile
     * @param newY The new Y position of the block tile
     * @param size The original size of the block tile
     */
    private void pushBlock(GraphicsContext gc, double newX, double newY, double size) {
        if (verifyNewPosition(newX, newY)) {
            //this.setX(newX); // This will function once Tile.java is finished
            //this.setY(newY);
        }
    }
// temporary method
    public void moveBlock(int X, int Y) {
        setX(X);
        setY(Y);
    }


    /**
     * Checks if a block can be moved onto a position by checking if the tile in the new position allows for a block
     * to be passed through it
     * @param newX The new X position of the tile to be checked
     * @param newY The new Y position of the tile to be checked
     * @return true if the block can be positioned onto the new position, false otherwise
     */
    private boolean verifyNewPosition(double newX, double newY) {
        // if (LevelLoader.getTile(newX, newY).canPushBlock()) { // Ben and Pele adding getTile and canPushBlock respectively
        //     return true;
        // }
        return false;
    }

    public void setX(int x) {
        X = x;
    }

    public void setY(int y) {
        Y = y;
    }
}
