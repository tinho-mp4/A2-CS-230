package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * The {@code Dirt} class represents a dirt block in the game.
 * @author idk
 * @version 1.0
 */
public class Dirt extends Tile{
    private static final Image DIRT_IMAGE = new Image(Dirt.class.getResourceAsStream("sprites/dirt.png"));
    private boolean compacted;

    public Dirt(int x, int y) {
        super("dirt", x, y, false);
        setPushableBlock(false);
        this.compacted = false;
    }



    //Player SHOULD be able to walk on the dirt block, which thus will convert it to a path
    //However, monster SHOULD NOT be able to walk on the dirt block unless it has been converted

    public void compact(){
        if (!compacted) {
            this.compacted = true;
            setPushableBlock(true);
            LevelLoader.setTile(x, y, new Path(x, y));

        }
    }

    /**
     * draws the Dirt.
     * @param gc graphics context
     * @param x x coordinate
     * @param y y coordinate
     * @param size size
     */
    @Override
    public void draw(final GraphicsContext gc,
                     final double x,
                     final double y,
                     final double size) {
        gc.drawImage(DIRT_IMAGE, x * size, y * size);
    }
}
