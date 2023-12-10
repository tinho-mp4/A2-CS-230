package com.example._cs250a2;

import java.util.ArrayList;

/**
 * Represents a level in the game, including its layout, items, and entities.
 */
public class Level {

    private final String name;
    private int timeLimit;
    private int width;
    private int height;
    private ArrayList<ArrayList<Tile>> tileGrid;
    private ArrayList<Item> itemList;
    private ArrayList<Entity> entityList;

    /**
     * Constructs a new Level with specified properties.
     *
     * @param name      The name of the level.
     * @param timeLimit The time limit for completing the level.
     * @param width     The width of the level grid.
     * @param height    The height of the level grid.
     * @param tileGrid  The grid of tiles composing the level.
     * @param itemList  The list of items present in the level.
     * @param entityList The list of entities present in the level.
     */
    public Level(String name, int timeLimit, int width, int height,
                 ArrayList<ArrayList<Tile>> tileGrid,
                 ArrayList<Item> itemList,
                 ArrayList<Entity> entityList) {
        this.name = name;
        this.timeLimit = timeLimit;
        this.width = width;
        this.height = height;
        this.tileGrid = tileGrid;
        this.itemList = itemList;
        this.entityList = entityList;
    }

    /**
     * Gets the time limit for the level.
     *
     * @return The time limit for the level.
     */
    public static Item getCurrentItem() {
        Player player = (Player) LevelLoader.getEntityByClass(Player.class);
        int playerX = player.getX();
        int playerY = player.getY();


        for (Item item : LevelLoader.getItemList()) {
            if (item.getX() == playerX && item.getY() == playerY) {
                return item;
            }
        }

        return null;
    }

    /**
     * Checks if the player is currently on an item.
     *
     * @return true if the player is on an item, false otherwise.
     */
    public static boolean isOnItem() {
        return getCurrentItem() != null;
    }

    /**
     * Checks if the player is currently on a monster.
     *
     */
    public Level(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the level.
     *
     * @return The name of the level.
     */
    public String getName() {
        return name;
    }


    /**
     * Gets the time limit for the level.
     *
     * @return The time limit for the level.
     */
    public ArrayList<ArrayList<Tile>> getTileGrid() {
        return tileGrid;
    }

    /**
     * Gets the time limit for the level.
     *
     * @return The time limit for the level.
     */
    public ArrayList<Item> getItemList() {
        return itemList;
    }

    /**
     * Gets the time limit for the level.
     *
     * @return The time limit for the level.
     */
    public ArrayList<Entity> getEntityList() {
        return entityList;
    }

    /**
     * Gets the time limit for the level.
     *
     * @return The time limit for the level.
     */
    @Override
    public String toString() {
        return name;
    }
}