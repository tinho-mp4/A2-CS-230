package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

/**
 * Represents a dirt block in the game. Players can convert dirt blocks to paths by walking over them,
 * but monsters cannot unless the dirt has been converted.
 * @author Pele Mayle
 * @version 1.0
 */
public class Dirt extends Tile {
    private static final Image DIRT_IMAGE
            = new Image(Objects.requireNonNull(Dirt.class.getResourceAsStream("sprites/dirt.png")));
    private boolean compacted;

    /**
     * Constructs a Dirt object with specified coordinates.
     *
     * @param x The x-coordinate of the dirt block.
     * @param y The y-coordinate of the dirt block.
     */
    public Dirt(int x, int y) {
        super("dirt", x, y, false);
        setPushableBlock(false);
        this.compacted = false;
    }

    /**
     * Compacts the dirt block, turning it into a path.
     * Sets the block as pushable and updates its position in the level.
     */
    public void compact() {
        if (!compacted) {
            this.compacted = true;
            setPushableBlock(true);
            LevelLoader.setTile(x, y, new Path(x, y));
        }
    }

    /**
     * Draws the dirt block on the provided GraphicsContext.
     *
     * @param gc   The GraphicsContext to draw on.
     * @param x    The x-coordinate on the canvas.
     * @param y    The y-coordinate on the canvas.
     * @param size The size to draw the image.
     */
    @Override
    public void draw(final GraphicsContext gc, final double x, final double y, final double size) {
        gc.drawImage(DIRT_IMAGE, x * size, y * size);
    }
}
