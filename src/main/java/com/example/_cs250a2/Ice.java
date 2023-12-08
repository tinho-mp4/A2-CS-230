package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

enum Corner {TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT, NONE};
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

    public static void event(int playerX, int playerY, int newPlayerX, int newPlayerY) {
        // log coords
        // System.out.println("playerX: " + playerX + " playerY: " + playerY + " newPlayerX: " + newPlayerX + " newPlayerY: " + newPlayerY);
        // delta will act as the direction in which the player is going
        // NOTE: deltaX -1 is left, deltaX 1 is right, deltaY 1 is up, deltaY -1 is down, this might need changing
        int deltaX = newPlayerX - playerX;
        int deltaY = newPlayerY - playerY;

        if (!LevelLoader.getTile(newPlayerX, newPlayerY).isSolid()) {
            // Corner checking
            if (LevelLoader.getTile(newPlayerX, newPlayerY).getName() == "ice") {
                Player.setX(newPlayerX);
                Player.setY(newPlayerY);

                Corner blockedCorner = ((Ice)LevelLoader.getTile(newPlayerX, newPlayerY)).getBlockedCorner();

                int targetX = newPlayerX;
                int targetY = newPlayerY;

                if (hitIceWall(playerX, playerY, newPlayerX, newPlayerY, blockedCorner)) {
                    // if the player came from a path, player should stay on the path
                    if (LevelLoader.getTile(playerX, playerY).getName() != "ice") {
                        Player.setX(playerX);
                        Player.setY(playerY);
                        return;
                    }
                    Player.setX(playerX - deltaX);
                    Player.setY(playerY - deltaY);
                    event(playerX, playerY, playerX - deltaX, playerY - deltaY);
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

                event(newPlayerX, newPlayerY, targetX, targetY);
            } else {
                Player.setX(newPlayerX);
                Player.setY(newPlayerY);
            }
        } else if (LevelLoader.getTile(newPlayerX, newPlayerY).isSolid()) { // Go in the reverse direction
            Player.setX(playerX - deltaX);
            Player.setY(playerY - deltaY);
            event(playerX, playerY, playerX - deltaX, playerY - deltaY);
        }
    }

    public static boolean hitIceWall(int playerX, int playerY, int newPlayerX, int newPlayerY, Corner blockedCorner) {
        int deltaX = newPlayerX - playerX;
        int deltaY = newPlayerY - playerY;

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
