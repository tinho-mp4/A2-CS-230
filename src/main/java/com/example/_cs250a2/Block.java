package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Block extends Tile {
    private static final Image BLOCK_IMAGE = new Image(Block.class.getResourceAsStream("block.png"));

    public Block(int x, int y) {
        super("block",x, y, true);
        this.setPushableBlock(true);
    }

    /**
     * pushes the block onto a new postion
     * @param gc The graphics context used to draw the new block tile
     * @param newX The new X position of the block tile
     * @param newY The new Y position of the block tile
     */
    private void pushBlock(GraphicsContext gc, int newX, int newY) {
        if (verifyNewPosition(newX, newY)) {
            this.setX(newX);
            this.setY(newY);
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

    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(BLOCK_IMAGE, x*size, y*size);
    }
}
