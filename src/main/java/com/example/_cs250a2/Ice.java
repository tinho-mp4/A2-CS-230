package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * The {@code Ice} class represents an ice block in the game
 * @author idk
 * @version 1.0
 */
enum Corner {TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT, NONE}

public class Ice extends Tile {
    private static final Image ICE_IMAGE = new Image(Ice.class.getResourceAsStream("sprites/ice.png"));
    private static final Image ICE_TOP_LEFT_IMAGE = new Image(Ice.class.getResourceAsStream("sprites/iceTopLeft.png"));
    private static final Image ICE_TOP_RIGHT_IMAGE = new Image(Ice.class.getResourceAsStream("sprites/iceTopRight.png"));
    private static final Image ICE_BOTTOM_LEFT_IMAGE = new Image(Ice.class.getResourceAsStream("sprites/iceBottomLeft.png"));
    private static final Image ICE_BOTTOM_RIGHT_IMAGE = new Image(Ice.class.getResourceAsStream("sprites/iceBottomRight.png"));
    /**
     * Instantiation of the corner variable
     */
    private Corner blockedCorner;

    /**
     * Sets the variable for the corner of which a wall is placed
     * @param blockedCorner the blocked corner enum variable (can be null)
     */
    public Ice(int x, int y, int blockedCorner) {
        super("ice", x, y, false);
        this.setPushableBlock(true);
        switch (blockedCorner-'0') {
            case 0:
                this.blockedCorner = Corner.NONE;
                break;
            case 1:
                this.blockedCorner = Corner.TOP_LEFT;
                break;
            case 2:
                this.blockedCorner = Corner.TOP_RIGHT;
                break;
            case 3:
                this.blockedCorner = Corner.BOTTOM_LEFT;
                break;
            case 4:
                this.blockedCorner = Corner.BOTTOM_RIGHT;
                break;
        }
    }

    public static void event(int entityX, int entityY, int newEntityX, int newEntityY) {
        // log coords
        // System.out.println("entityX: " + entityX + " entityY: " + entityY + " newEntityX: " + newEntityX + " newEntityY: " + newEntityY);
        // delta will act as the direction in which the player is going
        // NOTE: deltaX -1 is left, deltaX 1 is right, deltaY -1 is up, deltaY 1 is down
        Entity currentEntity = LevelLoader.getEntityWithCoords(entityX, entityY);

        int deltaX = newEntityX - entityX;
        int deltaY = newEntityY - entityY;

        if (!LevelLoader.getTile(newEntityX, newEntityY).isSolid()) {
            // Corner checking
            if (LevelLoader.getTile(newEntityX, newEntityY).getName() == "ice") {
                if (LevelLoader.getTile(newEntityX+deltaX, newEntityY+deltaY).isSolid()) {
                    return;
                }
                updateEntity(currentEntity, newEntityX, newEntityY);

                Corner blockedCorner = ((Ice)LevelLoader.getTile(newEntityX, newEntityY)).getBlockedCorner();

                int targetX = newEntityX;
                int targetY = newEntityY;

                if (hitIceWall(entityX, entityY, newEntityX, newEntityY, blockedCorner)) {
                    // if the entity came from a path, player should stay on the path
                    if (LevelLoader.getTile(entityX, entityY).getName() != "ice") {
                        updateEntity(currentEntity, entityX, entityY);
                        return;
                    }
                    updateEntity(currentEntity, entityX - deltaX, entityY - deltaY);
                    event(entityX, entityY, entityX - deltaX, entityY - deltaY);
                    return;
                }

                switch (blockedCorner) {
                    case TOP_LEFT:
                        targetY += (deltaX == -1) ? 1 : 0;
                        targetX += (deltaY == -1) ? 1 : 0;
                        break;
                    case TOP_RIGHT:
                        targetY += (deltaX == 1) ? 1 : 0;
                        targetX -= (deltaY == -1) ? 1 : 0;
                        break;
                    case BOTTOM_LEFT:
                        targetY -= (deltaX == -1) ? 1 : 0;
                        targetX += (deltaY == 1) ? 1 : 0;
                        break;
                    case BOTTOM_RIGHT:
                        targetY -= (deltaX == 1) ? 1 : 0;
                        targetX -= (deltaY == 1) ? 1 : 0;
                        break;
                    default:
                        targetX += deltaX;
                        targetY += deltaY;
                        break;
                }

                event(newEntityX, newEntityY, targetX, targetY);
            } else {
                if (LevelLoader.getEntityWithCoords(newEntityX, newEntityY) instanceof Block) {
                    if (LevelLoader.getTile(newEntityX + deltaX, newEntityY + deltaY).isSolid()) { // checks if the block is hitting a wall
                        event(newEntityX- deltaX, newEntityY - deltaY, newEntityX - 2*deltaX, newEntityY - 2*deltaY);
                        return;
                    } else {
                        event(newEntityX, newEntityY, newEntityX + deltaX, newEntityY + deltaY);
                    }
                }
                updateEntity(currentEntity, newEntityX, newEntityY);
            }
        } else { // Go in the reverse direction
            if (LevelLoader.getTile(entityX - 2*deltaX, entityY - 2*deltaY).getName() == "ice") {
                event(entityX - deltaX, entityY - deltaY, entityX - 2*deltaX, entityY - 2*deltaY);
            } else {
                event(entityX, entityY, entityX - deltaX, entityY - deltaY);
            }
        }
    }

    public static void updateEntity(Entity entity, int newX, int newY) {
        if (entity instanceof Player player) {
            player.setX(newX);
            player.setY(newY);
            player.setPosition(newX, newY);
        } else if (entity instanceof Block block) {
            block.setX(newX);
            block.setY(newY);
        }
    }

    public static boolean hitIceWall(int entityX, int entityY, int newEntityX, int newEntityY, Corner blockedCorner) {
        int deltaX = newEntityX - entityX;
        int deltaY = newEntityY - entityY;

        // this code acts as a way of checking if the player is hitting a wall, if it does,
        switch (blockedCorner) {
            case TOP_LEFT:
                return (deltaX == 1 || deltaY == 1);
            case TOP_RIGHT:
                return (deltaX == -1 || deltaY == 1);
            case BOTTOM_LEFT:
                return (deltaX == 1 || deltaY == -1);
            case BOTTOM_RIGHT:
                return (deltaX == -1 || deltaY == -1);
            default:
                return false;
        }

    }
    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        switch (this.blockedCorner) {
            case TOP_LEFT:
                gc.drawImage(ICE_TOP_LEFT_IMAGE, x*size, y*size);
                break;
            case TOP_RIGHT:
                gc.drawImage(ICE_TOP_RIGHT_IMAGE, x*size, y*size);
                break;
            case BOTTOM_LEFT:
                gc.drawImage(ICE_BOTTOM_LEFT_IMAGE, x*size, y*size);
                break;
            case BOTTOM_RIGHT:
                gc.drawImage(ICE_BOTTOM_RIGHT_IMAGE, x*size, y*size);
                break;
            default:
                gc.drawImage(ICE_IMAGE, x*size, y*size);
                break;
        }
    }

    private Corner getBlockedCorner() {
        return this.blockedCorner;
    }

    public String getName() {
        return "ice";
    }
}
