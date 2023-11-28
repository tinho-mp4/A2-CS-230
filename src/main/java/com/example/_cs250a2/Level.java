package com.example._cs250a2;

public class Level {
    private int[][] levelTiles;

    public Level(int[][] levelTiles) {
        this.levelTiles = levelTiles;
    }

    public static Tile checkTile(int x, int y){
        Tile tile = new Tile("test",x,y);
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
}
