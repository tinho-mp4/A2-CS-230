package com.example._cs250a2.tile;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Enumeration list for each corner of the wall on an ice block
 */
enum Corner {TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT};
class IceTile extends Tile {
    private static final Image ICE_IMAGE = new Image(IceTile.class.getResourceAsStream("/com/example/_cs250a2/Ice.png"));
    private static final Image ICE_TOP_LEFT_IMAGE = new Image(IceTile.class.getResourceAsStream("/com/example/_cs250a2/Ice.png"));
    private static final Image ICE_TOP_RIGHT_IMAGE = new Image(IceTile.class.getResourceAsStream("/com/example/_cs250a2/Ice.png"));
    private static final Image ICE_BOTTOM_LEFT_IMAGE = new Image(IceTile.class.getResourceAsStream("/com/example/_cs250a2/Ice.png"));
    private static final Image ICE_BOTTOM_RIGHT_IMAGE = new Image(IceTile.class.getResourceAsStream("/com/example/_cs250a2/Ice.png"));
    /**
     * Instantiation of the corner variable
     */
    private Corner blockedCorner;

    /**
     * Sets the variable for the corner of which a wall is placed
     * @param blockedCorner the blocked corner enum variable (can be null)
     */
    public IceTile(Corner blockedCorner) {
        this.blockedCorner = blockedCorner;
    }

    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        switch (this.blockedCorner) {
            case TOP_LEFT:
                gc.drawImage(ICE_TOP_LEFT_IMAGE, x, y, size,size);
                break;
            case TOP_RIGHT:
                gc.drawImage(ICE_TOP_RIGHT_IMAGE, x, y, size,size);
                break;
            case BOTTOM_LEFT:
                gc.drawImage(ICE_BOTTOM_LEFT_IMAGE, x, y, size,size);
                break;
            case BOTTOM_RIGHT:
                gc.drawImage(ICE_BOTTOM_RIGHT_IMAGE, x, y, size,size);
                break;
            default:
                gc.drawImage(ICE_IMAGE, x, y, size, size);
                break;
        }
    }
    String getText() {
        return "ice";
    }
}