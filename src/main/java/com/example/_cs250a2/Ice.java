package com.example._cs250a2;

enum Corner {TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT, NONE};
public class Ice {

    /**
     * Instantiation of the corner variable
     */
    private Corner blockedCorner;

    /**
     * Sets the variable for the corner of which a wall is placed
     * @param blockedCorner the blocked corner enum variable (can be null)
     */
    public Ice(Corner blockedCorner) {
        this.blockedCorner = blockedCorner;
    }

    public static void event(int playerX, int playerY, int newPlayerX, int newPlayerY) {
        // delta will act as the direction in which the player is going
        // NOTE: deltaX -1 is left, deltaX 1 is right, deltaY 1 is up, deltaY -1 is down, this might need changing
        int deltaX = newPlayerX - playerX;
        int deltaY = newPlayerY - playerY;

        if (LevelLoader.getTile(playerX, playerY).getName() == "ice") {
            if (!LevelLoader.getTile(newPlayerX, newPlayerY).isSolid()) {
                // Corner checking
                if (LevelLoader.getTile(newPlayerX, newPlayerY).getName() == "ice") {
                    Player.setX(newPlayerX);
                    Player.setY(newPlayerY);

                    Corner blockedCorner = LevelLoader.getTile(newPlayerX, newPlayerY).getBlockedCorner();

                    int targetX = newPlayerX;
                    int targetY = newPlayerY;

                    switch (blockedCorner) {
                        case TOP_LEFT:
                            targetY -= (deltaX == -1) ? 1 : 0;
                            targetX += (deltaY == 1) ? 1 : 0;
                            break;
                        case TOP_RIGHT:
                            targetY -= (deltaX == 1) ? 1 : 0;
                            targetX -= (deltaY == 1) ? 1 : 0;
                            break;
                        case BOTTOM_LEFT:
                            targetY += (deltaX == -1) ? 1 : 0;
                            targetX += (deltaY == -1) ? 1 : 0;
                            break;
                        case BOTTOM_RIGHT:
                            targetY += (deltaX == 1) ? 1 : 0;
                            targetX -= (deltaY == -1) ? 1 : 0;
                            break;
                        default:
                            targetX += deltaX;
                            targetY += deltaY;
                            break;
                    }

                    event(newPlayerX, newPlayerY, targetX, targetY);
                }
            } else if (LevelLoader.getTile(newPlayerX, newPlayerY).isSolid()) {
                Player.setX(playerX - deltaX);
                Player.setY(playerY - deltaY);
                event(playerX - deltaX, playerY - deltaY, playerX - 2*deltaX, playerY - 2*deltaY);
            }
        }
    }

    public String getName() {
        return "ice";
    }
}
