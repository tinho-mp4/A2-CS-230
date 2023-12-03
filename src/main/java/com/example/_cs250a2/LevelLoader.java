package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import java.io.InputStream;
import java.util.Scanner;
import com.example._cs250a2.tile.*;

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
    private int height;
    /**
     * Represents the width of the level grid.
     * This value is set during the level loading process.
     */
    private int width;
    /**
     * Represents the name of the level.
     * This value is set during the level loading process.
     */
    private String levelName;


    /**
     * Reads level information to draw the level. The InputStream is expected to contain structured data representing
     * level layout, including tiles and other entities.
     * @param gc The GraphicsContext used for drawing.
     * @param inputStream The InputStream containing the level information.
     */
    public static void readAndDraw(GraphicsContext gc, InputStream inputStream) {
        try (Scanner scanner = new Scanner(inputStream)) {
            // Read level information
            String levelName = scanner.nextLine();
            int timeLimit = Integer.parseInt(scanner.nextLine());
            String[] dimensions = scanner.nextLine().split(" ");
            int height = Integer.parseInt(dimensions[0]);
            int width = Integer.parseInt(dimensions[1]);

            // Process tiles
            for (int i = 0; i < height; i++) {
                String line = scanner.nextLine();
                processTileLine(gc, line, i);
            }

            // Process other entities (monsters, player, items, etc.)
            while (scanner.hasNext()) {
                String entityInfo = scanner.nextLine();
                processEntity(gc, entityInfo);
            }
        }
    }

    /**
     *  Processes a line of com.example._cs250a2.tile information and draws the tiles on the specified GraphicsContext.
     *  Each character in the line represents a com.example._cs250a2.tile, and the tiles are drawn on the specified
     *  GraphicsContext at the corresponding positions.
     * @param gc The GraphicsContext used for drawing.
     * @param line The line containing com.example._cs250a2.tile information.
     * @param lineNumber The line number in the level grid.
     */
    private static void processTileLine(GraphicsContext gc, String line, int lineNumber) {
        double tileSize = 50; // Assuming default tile size is 50x50
        for (int i = 0; i < line.length(); i++) {
            char currentChar = line.charAt(i);
            char nextChar = (i < line.length() - 1) ? line.charAt(i + 1) : ' ';

            // Check if the currentChar is 'B' or 'T' and the nextChar is a digit
            if ((currentChar == 'B' || currentChar == 'T') && Character.isDigit(nextChar)) {
                int buttonNumber = Character.getNumericValue(nextChar);
                drawTile(gc, i * tileSize, lineNumber * tileSize, tileSize, currentChar, buttonNumber);
                i++; // Skip the next character (the digit) since its paired it with 'B' or 'T'
            } else {
                drawTile(gc, i * tileSize, lineNumber * tileSize, tileSize, currentChar, -1);
            }
        }

    }


    /**
     *  Draws a com.example._cs250a2.tile on the specified GraphicsContext at the given position with the specified size,
     *  based on the provided com.example._cs250a2.tile type and optional paired number.
     * @param gc The GraphicsContext used for drawing.
     * @param x The x-coordinate of the top-left corner of the com.example._cs250a2.tile.
     * @param y The y-coordinate of the top-left corner of the com.example._cs250a2.tile.
     * @param size size The size of the com.example._cs250a2.tile (assumed to be square).
     * @param tileType tileType The type of the com.example._cs250a2.tile (character representing the type).
     * @param pairedNumber pairedNumber The paired number associated with certain com.example._cs250a2.tile types (e.g., ButtonTile, TrapTile).
     */
    private static void drawTile(GraphicsContext gc, double x, double y, double size, char tileType, int pairedNumber) {
//        Tile tile = null;
//
//        switch (tileType) {
//            case 'D':
//                tile = new DirtTile();
//                break;
//            case 'U':
//                tile = new WallTile();
//                break;
//            //Add cases for other tile types...
//            default:
//                // Handle unknown tile types or leave empty if not needed
//                break;
//        }
//
//        if (tile != null) {
//            tile.draw(gc, x, y, size);
//        }
    }


    /**
     * Processes information about different entities and performs actions based on their types.
     * @param gc The GraphicsContext used for drawing or handling entities.
     * @param entityInfo The string containing information about the entity.
     */
    private static void processEntity(GraphicsContext gc, String entityInfo) {
        String[] parts = entityInfo.split(" ");
        char entityType = parts[0].charAt(0);

        switch (entityType) {
            /* add enties look like
            case 'E':
                processMonster(gc, parts);
                break;
             */

            // Add cases for other entity types...
            default:
                // Handle unknown com.example._cs250a2.tile types or leave empty if not needed
                break;
        }
    }


}
