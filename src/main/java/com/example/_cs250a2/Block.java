package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * The {@code Block} class represents a block tile in the game.
 * It is responsible for moving the block onto a new position,
 * and checking if the block can be moved onto a new position.
 */
public class Block extends Entity {
    /**
     * The image of the block tile.
     */
    private static final Image BLOCK_IMAGE
    = new Image(Block.class.getResourceAsStream("sprites/block.png"));

    /**
     * Creates a new block tile.
     * @param x The X position of the block tile
     * @param y The Y position of the block tile
     */
    public Block(final int x, final int y) {
        super(x, y);
    }

    /**
     * pushes the block onto a new postion.
     * @param newX The new X position of the block tile
     * @param newY The new Y position of the block tile
     */
    private void pushBlock(final int newX, final int newY) {
        this.setX(newX);
        this.setY(newY);
    }
// temporary method

    /**
     * Moves the block onto a new position.
     * @param x The new X position of the block
     * @param y The new Y position of the block
     */
    public void moveBlock(final int x, final int y) {
        setX(x);
        setY(y);
    }


    /**
     * Checks if a block can be moved onto a position by
     * checking if the tile in the new position allows for a block.
     * to be passed through it
     * @param newX The new X position of the tile to be checked
     * @param newY The new Y position of the tile to be checked
     * @param deltaX The change in X position of the block
     * @param deltaY The change in Y position of the block
     * @return true if the block can be positioned onto the new position,
     * false otherwise
     */
    private boolean verifyNewPosition(
            final int newX,
            final int newY,
            final int deltaX,
            final int deltaY) {
        Entity currentBlock = LevelLoader.getEntityWithCoords(newX, newY);
        int nextX = newX + deltaX;
        int nextY = newY + deltaY;
        if (currentBlock instanceof Block) {
            if (LevelLoader.getTile(nextX, nextY).isPushableBlock()) {
                return true;
            }
        }
        return false;
    }


    /**
     * Checks if the block can be moved onto a new position, and if so,
     * moves the block onto the new position.
     * @param playerX The current X position of the player
     * @param playerY The current Y position of the player
     * @param newX The new X position of the block
     * @param newY The new Y position of the block
     */
    public void event(
            final int playerX,
            final int playerY,
            final int newX,
            final int newY) {
        int deltaX = newX - playerX;
        int deltaY = newY - playerY;

        if (verifyNewPosition(newX, newY, deltaX, deltaY)) {
            Block currentBlock = (Block)
                    LevelLoader.getEntityWithCoords(newX, newY);
            switch (deltaX) {
                case 1:
                    currentBlock.pushBlock(newX + 1, newY);
                    break;
                case -1:
                    currentBlock.pushBlock(newX - 1, newY);
                    break;
                default:
                    switch (deltaY) {
                        case 1:
                            currentBlock.pushBlock(newX, newY + 1);
                            break;
                        case -1:
                            currentBlock.pushBlock(newX, newY - 1);
                            break;
                        default:
                            break;
                    }
                    break;
            }
            Player.setX(newX);
            Player.setY(newY);
        }


    }

    /** Draws the block onto the canvas.
     * @param gc The graphics context to draw the block onto
     * @param x The x position of the block
     * @param y The y position of the block
     * @param size The size of the block
     */
    @Override
    public void draw(
            final GraphicsContext gc,
            final double x,
            final double y,
            final double size) {
        gc.drawImage(BLOCK_IMAGE, x * size, y * size);
    }
}
