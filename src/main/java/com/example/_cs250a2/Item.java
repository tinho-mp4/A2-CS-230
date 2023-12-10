package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;

/**
 * Represents an abstract framework for items in the game.
 * This class provides the basic properties and functionalities
 * that all game items share.
 *
 * @author Ryan Pietras
 */
public abstract class Item {

    protected String name;
    protected int x;
    protected int y;

    /**
     * Constructs an Item with a name and its position.
     *
     * @param name The name of the item.
     * @param x    The x-coordinate of the item.
     * @param y    The y-coordinate of the item.
     */
    public Item(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x-coordinate of this item.
     *
     * @return The x-coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of this item.
     *
     * @param x The new x-coordinate.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets the y-coordinate of this item.
     *
     * @return The y-coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of this item.
     *
     * @param y The new y-coordinate.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Gets the name of this item.
     *
     * @return The name of the item.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Abstract method to draw the item on the given GraphicsContext.
     *
     * @param gc   The GraphicsContext on which to draw the item.
     * @param x    The x-coordinate on the canvas where the item should be drawn.
     * @param y    The y-coordinate on the canvas where the item should be drawn.
     * @param size The size of the item.
     */
    public abstract void draw(GraphicsContext gc, double x, double y, double size);

    /**
     * Returns a string representation of the item.
     *
     * @return A string describing the item's properties.
     */
    @Override
    public String toString() {
        return "Item{"
                + "name='" + name + '\''
                + ", x=" + x
                + ", y=" + y
                + '}';
    }
}
