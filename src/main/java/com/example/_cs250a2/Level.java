package com.example._cs250a2;

public class Level {

    private String name;
    private int[][] levelTiles;
    private static Item[][] itemLayer;

    public Level(int[][] levelTiles) {
        this.levelTiles = levelTiles;
        initializeItemLayer(levelTiles.length, levelTiles[0].length);
    }

    private static void initializeItemLayer(int rows, int cols) {
        itemLayer = new Item[rows][cols];
    }

    public static Tile checkTile(int x, int y){
        return new Path(x,y);
    }


    public static void nextLevel(){
    }

    public static boolean isOnItem(int playerX, int playerY) {
        if (itemLayer != null && playerX >= 0 && playerX < itemLayer.length && playerY >= 0 && playerY < itemLayer[playerX].length) {
            if (itemLayer[playerX][playerY] != null) {
                System.out.println("Player is on an item at (" + playerX + ", " + playerY + ")");
                return true;
            }
        }
        return false;
    }

    public static Item getItem(int x, int y) {
        if (itemLayer != null && x >= 0 && x < itemLayer.length && y >= 0 && y < itemLayer[x].length) {
            Item item = itemLayer[x][y];
            if (item != null) {
                System.out.println("Getting item at (" + x + ", " + y + "): " + item.getClass().getSimpleName());
            }
            return item;
        } else {
            return null;
        }
    }

    /**
     * Removes an item from the specified coordinates in the level.
     *
     * @param x The x-coordinate of the item to be removed.
     * @param y The y-coordinate of the item to be removed.
     */
    public static void removeItem(int x, int y) {
        if (x >= 0 && x < itemLayer.length && y >= 0 && y < itemLayer[x].length) {
            if (itemLayer[x][y] != null) {
                System.out.println("Removing item from (" + x + ", " + y + ")");
                itemLayer[x][y] = null;
            }
        }
    }


    public static boolean isOnMonster() {
        return false;
    }

    public static boolean isOnBlock() {
        return false;
    }

    public Level(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
