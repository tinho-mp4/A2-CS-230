package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String levelName = scanner.nextLine().split(": ")[1];
        int timeLimit = Integer.parseInt(scanner.nextLine().split("= ")[1]);
        String[] dimensions = Arrays.copyOfRange(scanner.nextLine().split(" "), 2, 4);
        int height = Integer.parseInt(dimensions[0]);
        int width = Integer.parseInt(dimensions[1]);
        scanner.nextLine(); // Skip empty line


        levelGrid = new ArrayList<>();
        // Process tiles
        for (int i = 0; i < height; i++) {
            String line = scanner.nextLine();
            Pattern pattern = Pattern.compile("([A-Z])([0-9])?");
            Matcher matcher = pattern.matcher(line);
            ArrayList<String> matchesList = new ArrayList<>();

            while (matcher.find()) {
                String match = matcher.group(1) + (matcher.group(2) != null ? matcher.group(2) : "");
                matchesList.add(match);
            }
            String[] matchesArray = matchesList.toArray(new String[0]);
            ArrayList<Tile> levelRow = new ArrayList<>();
            for (int j = 0; j < width; j++) {

                String currentTile = matchesArray[j];
                levelRow.add(processTile(gc, currentTile.toCharArray(), j, i));
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

    /** KEY
     * Path: P
     * Dirt: D
     * Wall: U
     * Exit: E
     * Button: B(n)
     * Trap: T
     * Water: W
     * Chip Socket: S
     * Ice: I(n)
     * Block: O
     * Locked Door: L - maybe n not sure yet
     * Frog: F
     * Pink Ball: G
     * Bug: E
     * Player:?
     * Computer Chip: C
     * Key: K - maybe n not sure yet
     */
    private static Tile processTile(GraphicsContext gc, char[] tile, int x, int y) {
        switch (tile[0]){
                case 'P':
                    return new Path(x, y);
                case 'D':
                    return new Dirt(x, y);
                case 'U':
                    return new Wall(x, y);
                case 'E':
                    return new Exit(x, y);
                case 'B':
                    return new Button(x, y);
                    // return new Button(x, y, tile[1]);
                case 'T':
                    return new Trap(x, y);
                case 'W':
                    return new Water(x, y);
                case 'S':
                    return new ChipSocket(0, x, y);
                case 'I':
                    return new Ice(x, y, tile[1]);
                case 'O':
                    return new Block(x, y);
//                case 'L':
//                    return new LockedDoor(x, y, tile[1]);
//                case 'F':
//                    return new Frog(x, y);
//                case 'G':
//                    return new PinkBall(x, y);
//                case 'E':
//                    return new Bug(x, y);
//                case 'Q':
//                    return new Player(x, y);
//                case 'C':
//                    return new Chip(x, y);
//                case 'K':
//                    return new Key(x, y, tile[1]);
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


    public static Tile getTile(int x, int y) {
        return levelGrid.get(x).get(y); // y might have to be reversed (height-y), since canvas y is flipped?
    }

    public static int getHeight() {
        return height;
    }
    public static int getWidth() {
        return width;
    }
}
