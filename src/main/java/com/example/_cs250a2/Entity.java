package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;

/**
 * Represents an abstract framework for entities in the game.
 * Entities are dynamic objects that can interact with the game environment.
 */
public abstract class Entity {
    private boolean isStuck;
    private int x;
    private int y;
    protected int[] location = {x, y};

    /**
     * Constructs an Entity with specified coordinates.
     *
     * @param x The x-coordinate of the entity.
     * @param y The y-coordinate of the entity.
     */
    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
        this.location = new int[]{x, y};
        this.isStuck = false;
    }

    /**
     * Checks if the entity is stuck.
     *
     * @return True if the entity is stuck, false otherwise.
     */
    public boolean isStuck() {
        return isStuck;
    }

    /**
     * Sets the entity's stuck status.
     *
     * @param stuck The new stuck status.
     */
    public void setStuck(boolean stuck) {
        this.isStuck = stuck;
    }

    /**
     * Gets the x-coordinate of the entity.
     *
     * @return The x-coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the entity.
     *
     * @param x The new x-coordinate.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets the y-coordinate of the entity.
     *
     * @return The y-coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the entity.
     *
     * @param y The new y-coordinate.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Handles the entity's response to an event at a specific location.
     *
     * @param x    The current x-coordinate.
     * @param y    The current y-coordinate.
     * @param newX The new x-coordinate after the event.
     * @param newY The new y-coordinate after the event.
     */
    public abstract void event(int x, int y, int newX, int newY);

    /**
     * Draws the entity on the given GraphicsContext.
     *
     * @param gc   The GraphicsContext on which to draw the entity.
     * @param x    The x-coordinate on the canvas.
     * @param y    The y-coordinate on the canvas.
     * @param size The size to draw the entity.
     */
    public abstract void draw(GraphicsContext gc, double x, double y, double size);
}
