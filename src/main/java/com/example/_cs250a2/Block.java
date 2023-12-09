package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Block extends Entity {
    private static final Image BLOCK_IMAGE = new Image(Block.class.getResourceAsStream("sprites/block.png"));

    public Block(int x, int y) {
        super(x, y);
    }

    /**
     * pushes the block onto a new postion
     * @param newX The new X position of the block tile
     * @param newY The new Y position of the block tile
     */
    private void pushBlock(int newX, int newY) {
        this.setX(newX);
        this.setY(newY);
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
    private boolean verifyNewPosition(int newX, int newY, int deltaX, int deltaY) {
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


    public void event(int playerX, int playerY, int newX, int newY) {
        int deltaX = newX - playerX;
        int deltaY = newY - playerY;

        if (verifyNewPosition(newX, newY, deltaX, deltaY)) {
            Block currentBlock = (Block) LevelLoader.getEntityWithCoords(newX, newY);
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

    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(BLOCK_IMAGE, x*size, y*size);
    }
}
