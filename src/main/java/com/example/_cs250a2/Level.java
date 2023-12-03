package com.example._cs250a2;

public class Level {

    private String name;
    private int[][] levelTiles;

    public Level(int[][] levelTiles) {
        this.levelTiles = levelTiles;
    }

    public static Tile checkTile(int x, int y){
        Tile tile = new Tile("test",x,y, false);
        return tile;
    }



    public static void nextLevel(){
    }

    public static boolean isOnitem() {
        return false;
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
