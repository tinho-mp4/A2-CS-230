package com.example._cs250a2;

import java.util.ArrayList;

/**
 * The {@code Level} class represents levels in the game
 * @author idk
 * @version 1.0
 */
public class Level {

    private String name;
    private int[][] levelTiles;

    public Level(int[][] levelTiles) {
        this.levelTiles = levelTiles;
    }


    public static Tile checkTile(int x, int y){
        return new Path(x,y);
    }

    public static void nextLevel(){
    }

    public static Item getCurrentItem() {
        int playerX = Player.getX();
        int playerY = Player.getY();

        for (ArrayList<Item> row : LevelLoader.getItemGrid()) {
            for (Item item : row) {
                if (item.getX() == playerX && item.getY() == playerY) {
                    return item;
                }
            }
        }
        return null;
    }

    public static boolean isOnItem() {
        return getCurrentItem() != null;
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