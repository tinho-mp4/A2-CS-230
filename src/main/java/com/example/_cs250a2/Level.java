package com.example._cs250a2;

import java.util.ArrayList;

/**
 * The {@code Level} class represents levels in the game
 * @author idk
 * @version 1.0
 */
public class Level {

    private String name;
    private int timeLimit;
    private int width;
    private int height;
    private ArrayList<ArrayList<Tile>> tileGrid;
    private ArrayList<ArrayList<Item>> itemGrid;
    private ArrayList<ArrayList<Entity>> entityGrid;

    public Level(String name, int timeLimit, int width, int height, ArrayList<ArrayList<Tile>> tileGrid, ArrayList<ArrayList<Item>> itemGrid, ArrayList<ArrayList<Entity>> entityGrid) {
        this.name = name;
        this.timeLimit = timeLimit;
        this.width = width;
        this.height = height;
        this.tileGrid = tileGrid;
        this.itemGrid = itemGrid;
        this.entityGrid = entityGrid;
    }


    public static Tile checkTile(int x, int y){
        return new Path(x,y);
    }

    public static void nextLevel(){
    }

    public static Item getCurrentItem() {
        Player player = (Player) LevelLoader.getEntityByClass(Player.class);
        int playerX = player.getX();
        int playerY = player.getY();

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

    public int getTimeLimit() {
        return timeLimit;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ArrayList<ArrayList<Tile>> getTileGrid() {
        return tileGrid;
    }

    public ArrayList<ArrayList<Item>> getItemGrid() {
        return itemGrid;
    }

    public ArrayList<ArrayList<Entity>> getEntityGrid() {
        return entityGrid;
    }

    @Override
    public String toString() {
        return name;
    }
}