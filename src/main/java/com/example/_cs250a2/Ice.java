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
                    switch (LevelLoader.getTile(newPlayerX, newPlayerY).getBlockedCorner()) {
                        Player.setX(newPlayerX);
                        Player.setY(newPlayerY);
                        case Corner.TOP_LEFT:
                            if (deltaX == -1) {
                                event(newPlayerX, newPlayerY, newPlayerX, newPlayerY - 1);
                            }
                            if (deltaY == 1) {
                                event(newPlayerX, newPlayerY, newPlayerX + 1, newPlayerY);
                                return;
                            }
                            break;
                        case Corner.TOP_RIGHT:
                            if (deltaX == 1) {
                                event(newPlayerX, newPlayerY, newPlayerX, newPlayerY - 1);
                            }
                            if (deltaY == 1) {
                                event(newPlayerX, newPlayerY, newPlayerX - 1, newPlayerY);
                                return;
                            }
                            break;
                        case Corner.BOTTOM_LEFT:
                            if (deltaX == -1) {
                                event(newPlayerX, newPlayerY, newPlayerX, newPlayerY + 1);
                            }
                            if (deltaY == -1) {
                                event(newPlayerX, newPlayerY, newPlayerX + 1, newPlayerY);
                                return;
                            }
                            break;
                        case Corner.BOTTOM_RIGHT:
                            if (deltaX == 1) {
                                event(newPlayerX, newPlayerY, newPlayerX, newPlayerY + 1);
                            }
                            if (deltaY == -1) {
                                event(newPlayerX, newPlayerY, newPlayerX - 1, newPlayerY);
                                return;
                            }
                            break;
                        case Corner.NONE:
                            event(newPlayerX, newPlayerY, newPlayerX + deltaX, newPlayerY + deltaY);
                            break;
                    }
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
