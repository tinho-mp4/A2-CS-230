package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;

/**
 * An abstract base class for all tile-like elements in the game.
 * This class provides a common framework for tile properties and behaviors.
 * @author Pele Mayle
 * @version 1.0
 */
public abstract class Tile {
    /**
     * The name of the tile, identifying its type or unique characteristics.
     */
    protected String name;

    /**
     * The X-coordinate of the tile's position in the game world or grid.
     */
    protected int x;

    /**
     * The Y-coordinate of the tile's position in the game world or grid.
     */
    protected int y;

    /**
     * Indicates whether the tile is solid. A solid tile is not passable by the player or other entities.
     */
    protected boolean solid;

    /**
     * Indicates whether the tile is a pushable block. A pushable block can be moved by the player or other entities.
     */
    protected boolean pushableBlock;


    /**
     * Constructs a Tile with a specified name, position, and solidity.
     *
     * @param name  Name of the tile.
     * @param x     X-coordinate of the tile.
     * @param y     Y-coordinate of the tile.
     * @param solid Boolean indicating if the tile is solid (not passable).
     */
    public Tile(String name, int x, int y, boolean solid) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.solid = solid;
    }

    /**
     * Gets the X-coordinate of the tile.
     *
     * @return The X-coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the Y-coordinate of the tile.
     *
     * @return The Y-coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Checks if the tile is solid (not passable).
     *
     * @return true if the tile is solid, false otherwise.
     */
    public boolean isSolid() {
        return solid;
    }

    /**
     * Sets the solidity of the tile.
     *
     * @param solid Boolean indicating the new solid state.
     */
    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    /**
     * Checks if the tile is a pushable block.
     *
     * @return true if the tile is pushable, false otherwise.
     */
    public boolean isPushableBlock() {
        return pushableBlock;
    }

    /**
     * Sets the pushable state of the tile.
     *
     * @param pushableBlock Boolean indicating the new pushable state.
     */
    public void setPushableBlock(boolean pushableBlock) {
        this.pushableBlock = pushableBlock;
    }

    /**
     * Gets the name of the tile.
     *
     * @return The name of the tile.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Abstract method to draw the tile.
     * This method should be implemented by subclasses to define how the tile is drawn.
     *
     * @param gc   GraphicsContext for drawing the tile.
     * @param x    X-coordinate for drawing.
     * @param y    Y-coordinate for drawing.
     * @param size Size of the tile.
     */
    public abstract void draw(GraphicsContext gc, double x, double y, double size);
}
