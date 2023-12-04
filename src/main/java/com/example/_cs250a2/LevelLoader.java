package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class LevelLoader {
    /**
     * Represents the time limit for completing the level.
     * This value is set during the level loading process.
     */

    private int timeLimit;
    /**
     * Represents the height of the level grid.
     * This value is set during the level loading process.
     */
    private static int height;
    /**
     * Represents the width of the level grid.
     * This value is set during the level loading process.
     */
    private static int width;
    /**
     * Represents the name of the level.
     * This value is set during the level loading process.
     */
    private String levelName;

    /**
     * Represents the level grid.
     * This value is set during the level loading process.
     */
    private static ArrayList<ArrayList<Tile>> levelGrid = new ArrayList<>();

    /**
     * Reads level information to draw the level. The InputStream is expected to contain structured data representing
     * level layout, including tiles and other entities.
     * @param gc The GraphicsContext used for drawing.
     * @param inputStream The InputStream containing the level information.
     */
    public static void loadLevel(GraphicsContext gc, InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        // Read level information
        String levelName = scanner.nextLine();
        int timeLimit = Integer.parseInt(scanner.nextLine());
        String[] dimensions = scanner.nextLine().split(" ");
        int height = Integer.parseInt(dimensions[0]);
        int width = Integer.parseInt(dimensions[1]);
        scanner.nextLine(); // Skip empty line

        levelGrid = new ArrayList<>();
        // Process tiles
        for (int i = 0; i < height; i++) {
            String line = scanner.nextLine();
            String[] splitLine = line.split("([A-Z])(\\d*)");
            ArrayList<Tile> levelRow = new ArrayList<>();
            for (int j = 0; j < width; j++) {

                char currentChar = line.charAt(j);
                levelRow.add(processTile(gc, currentChar, j, i));
            }
            levelGrid.add(levelRow);
        }
        drawLevel(gc);
    }

    /**
     *  Processes a line of com.example._cs250a2.tile information and draws the tiles on the specified GraphicsContext.
     *  Each character in the line represents a com.example._cs250a2.tile, and the tiles are drawn on the specified
     *  GraphicsContext at the corresponding positions.
     * @param gc The GraphicsContext used for drawing.
     */
    private static Tile processTile(GraphicsContext gc, char c, int x, int y) {
        switch (c){
                case '#':
                    return new Wall(x, y);
                case '.':
                    return new Path(x, y);
                case ':':
                    return new Dirt(x, y);
                case '~':
                    return new Water(x, y);
                case 'E':
                    return new Exit(x, y);
                default:
                    // Handle unknown com.example._cs250a2.tile types or leave empty if not needed
                    return null;
        }

    }

    public static void drawLevel(GraphicsContext gc) {;
        for (ArrayList<Tile> row : levelGrid) {
            for (Tile tile : row) {
                tile.draw(gc, tile.getX(), tile.getY(), 35);
            }
        }
    }


    public static Tile getTile(int playerX, int playerY) {
        return null;
    }

    public static int getHeight() {
        return height;
    }
    public static int getWidth() {
        return width;
    }
}
