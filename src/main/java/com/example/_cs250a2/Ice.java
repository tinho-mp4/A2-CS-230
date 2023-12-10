package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

/**
 * The {@code Corner} enum represents the corner of a wall that is blocked
 */
enum Corner {TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT, NONE}

/**
 * The {@code Ice} class represents an ice block in the game
 * @author Ryan Pietras
 * @version 1.0
 */
public class Ice extends Tile {
    /**
     * Image of the ice block
     */
    private static final Image ICE_IMAGE =
            new Image(Objects.requireNonNull(Ice.class.getResourceAsStream("sprites/ice.png")));
    /**
     * Images of the ice block with the top left corner blocked
     */
    private static final Image ICE_TOP_LEFT_IMAGE =
            new Image(Objects.requireNonNull(Ice.class.getResourceAsStream("sprites/iceTopLeft.png")));
    /**
     * Images of the ice block with the top right corner blocked
     */
    private static final Image ICE_TOP_RIGHT_IMAGE =
            new Image(Objects.requireNonNull(Ice.class.getResourceAsStream("sprites/iceTopRight.png")));
    /**
     * Images of the ice block with the bottom left corner blocked
     */
    private static final Image ICE_BOTTOM_LEFT_IMAGE =
            new Image(Objects.requireNonNull(Ice.class.getResourceAsStream("sprites/iceBottomLeft.png")));
    /**
     * Images of the ice block with the bottom right corner blocked
     */
    private static final Image ICE_BOTTOM_RIGHT_IMAGE =
            new Image(Objects.requireNonNull(Ice.class.getResourceAsStream("sprites/iceBottomRight.png")));
    /**
     * Numerical identifier for no corner
     */
    private static final int NO_CORNER_IDENTIFIER = 0;
    /**
     * Numerical identifier for the top left corner
     */
    private static final int TOP_LEFT_IDENTIFIER = 1;
    /**
     * Numerical identifier for the top right corner
     */
    private static final int TOP_RIGHT_IDENTIFIER = 2;
    /**
     * Numerical identifier for the bottom left corner
     */
    private static final int BOTTOM_LEFT_IDENTIFIER = 3;
    /**
     * Numerical identifier for the bottom right corner
     */
    private static final int BOTTOM_RIGHT_IDENTIFIER = 4;
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
            case NO_CORNER_IDENTIFIER:
                this.blockedCorner = Corner.NONE;
                break;
            case TOP_LEFT_IDENTIFIER:
                this.blockedCorner = Corner.TOP_LEFT;
                break;
            case TOP_RIGHT_IDENTIFIER:
                this.blockedCorner = Corner.TOP_RIGHT;
                break;
            case BOTTOM_LEFT_IDENTIFIER:
                this.blockedCorner = Corner.BOTTOM_LEFT;
                break;
            case BOTTOM_RIGHT_IDENTIFIER:
                this.blockedCorner = Corner.BOTTOM_RIGHT;
                break;
            default:
                break;
        }
    }

    /**
     * Handles the movement of a specific entity on an ice block
     * @param entityX The x coordinate of the entity
     * @param entityY The y coordinate of the entity
     * @param newEntityX The next x coordinate of the entity
     * @param newEntityY The next y coordinate of the entity
     * @param afterBlockReturn Whether the entity movement is after a block push back
     */
    public static void event(int entityX, int entityY, int newEntityX, int newEntityY, boolean afterBlockReturn) {
        // Load the entity at the current position
        Entity currentEntity = LevelLoader.getEntityWithCoords(entityX, entityY);

        // deltaX -1 is left, deltaX 1 is right, deltaY -1 is up, deltaY 1 is down
        // Calculate the change in x and y
        int deltaX = newEntityX - entityX;
        int deltaY = newEntityY - entityY;

        // Check if the next tile is solid
        if (!LevelLoader.getTile(newEntityX, newEntityY).isSolid()) {
            // Check if the next tile is an ice tile
            if (Objects.equals(LevelLoader.getTile(newEntityX, newEntityY).getName(), "ice")) {
                // Check if the next tile after the ice tile is solid
                if (LevelLoader.getTile(newEntityX+deltaX, newEntityY+deltaY).isSolid()) {
                    // Check if the tile before the current position is an ice tile
                    if (Objects.equals(LevelLoader.getTile(entityX - deltaX, entityY - deltaY).getName(), "ice")) {
                        // If it is, move the entity back to the tile before the ice tile
                        event(entityX - deltaX, entityY - deltaY,
                                entityX - 2*deltaX, entityY - 2*deltaY, false);
                        return;
                    } else {
                        // Check if the tile before the current position is not solid
                        if (!LevelLoader.getTile(entityX-deltaX, entityY - deltaY).isSolid()) {
                            event(entityX, entityY,
                                    entityX - deltaX, entityY - deltaY, false);
                        }
                        return;
                    }
                }
                // Update the entity's position
                updateEntity(currentEntity, newEntityX, newEntityY);

                // Get the corner of the ice block that is blocked
                Corner blockedCorner = ((Ice) LevelLoader.getTile(newEntityX, newEntityY)).getBlockedCorner();

                int targetX = newEntityX;
                int targetY = newEntityY;

                // Check if the entity is hitting a wall of an ice block
                if (hitIceWall(entityX, entityY, newEntityX, newEntityY, blockedCorner)) {
                    // If the next tile is an ice tile, move the entity to that position
                    if (!Objects.equals(LevelLoader.getTile(entityX, entityY).getName(), "ice")) {
                        updateEntity(currentEntity, entityX, entityY);
                        return;
                    }
                    // Move the entity back one tile
                    updateEntity(currentEntity, entityX - deltaX, entityY - deltaY);
                    event(entityX, entityY,
                            entityX - deltaX, entityY - deltaY, false);
                    return;
                }

                // Depending on the corner of the ice block and the direction the entity is going,
                // the entity will move in a specific direction.
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

                // Perform the event of the entity moving to the target position
                event(newEntityX, newEntityY, targetX, targetY, false);
            } else {
                // If the player is moving after a block push back, do not move the player
                if (afterBlockReturn && LevelLoader.getEntityWithCoords(newEntityX, newEntityY) instanceof Block) {
                    return;
                }
                // Check if the next tile is a block
                if (LevelLoader.getEntityWithCoords(newEntityX, newEntityY) instanceof Block) {
                    // Check if the tile after the block is solid
                    if (LevelLoader.getTile(newEntityX + deltaX, newEntityY + deltaY).isSolid()) {
                        // If it is, move the entity back to the tile before the block
                        event(newEntityX - deltaX, newEntityY - deltaY,
                                newEntityX - 2*deltaX, newEntityY - 2*deltaY, false);
                        return;
                    } else {
                        // If not, move the block to the next tile
                        event(newEntityX, newEntityY,
                                newEntityX + deltaX, newEntityY + deltaY, false);
                    }
                }
                // Update the entity's position
                updateEntity(currentEntity, newEntityX, newEntityY);
            }
        } else { // This is the case where the next tile is solid
            // If the next tile is an ice tile, move the entity back one tile
            if (Objects.equals(LevelLoader.getTile(entityX - 2 * deltaX, entityY - 2 * deltaY).getName(), "ice")) {
                event(entityX - deltaX, entityY - deltaY,
                        entityX - 2*deltaX, entityY - 2*deltaY, false);
            } else {
                // If the next tile isn't an ice tile, the entity stays in place
                event(entityX, entityY, entityX - deltaX,
                        entityY - deltaY, false);
            }
        }
    }

    /**
     * Updates the entity's position based on what type of entity it is
     * @param entity The entity object to be updated
     * @param newX The new x coordinate of the entity
     * @param newY The new y coordinate of the entity
     */
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

    /**
     * Checks if the entity is hitting a wall of an ice block
     * @param entityX The x coordinate of the entity
     * @param entityY The y coordinate of the entity
     * @param newEntityX The next x coordinate of the entity
     * @param newEntityY The next y coordinate of the entity
     * @param blockedCorner The corner of the ice block that is blocked
     * @return {@code true} if the entity is hitting a wall of an ice block, {@code false} otherwise
     */
    public static boolean hitIceWall(int entityX, int entityY, int newEntityX, int newEntityY, Corner blockedCorner) {
        int deltaX = newEntityX - entityX;
        int deltaY = newEntityY - entityY;

        return switch (blockedCorner) {
            // If the entity is moving right or down, it is hitting the left or top wall of the ice block
            case TOP_LEFT -> (deltaX == 1 || deltaY == 1);
            // If the entity is moving left or down, it is hitting the right or top wall of the ice block
            case TOP_RIGHT -> (deltaX == -1 || deltaY == 1);
            // If the entity is moving right or up, it is hitting the left or bottom wall of the ice block
            case BOTTOM_LEFT -> (deltaX == 1 || deltaY == -1);
            // If the entity is moving left or up, it is hitting the right or bottom wall of the ice block
            case BOTTOM_RIGHT -> (deltaX == -1 || deltaY == -1);
            // If the entity is not hitting a wall, return false
            default -> false;
        };

    }

    /**
     * Draws the ice block on the canvas with the appropriate corner blocked
     * @param gc GraphicsContext for drawing the tile
     * @param x The x coordinate for drawing
     * @param y The y coordinate for drawing
     * @param size The size of the tile in pixels
     */
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

    /**
     * Gets the corner of the ice block that is blocked
     * @return The corner of the ice block that is blocked
     */
    private Corner getBlockedCorner() {
        return this.blockedCorner;
    }

    /**
     * Gets the name of the tile
     * @return The name of the tile
     */
    public String getName() {
        return "ice";
    }
}
