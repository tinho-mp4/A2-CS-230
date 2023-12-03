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
     * @param tileGc The GraphicsContext used for drawing.
     * @param itemGc The GraphicsContext used for drawing items.
     * @param entityGc The GraphicsContext used for drawing objects that move.
     * @param inputStream The InputStream containing the level information.
     *                    add rest
     */
    static void readAndDraw(GraphicsContext tileGc, GraphicsContext itemGc, GraphicsContext entityGc, InputStream inputStream) {
        try (Scanner scanner = new Scanner(inputStream)) {
            // Check if there is another line available before reading
            if (scanner.hasNextLine()) {
                // Read level information
                //read the level name
                String levelName = scanner.nextLine();
                //read the Time limit
                int timeLimit = Integer.parseInt(scanner.nextLine());
                //read dimensions - array of two elements
                String[] dimensions = scanner.nextLine().split(" ");
                int height = Integer.parseInt(dimensions[0]);
                int width = Integer.parseInt(dimensions[1]);
                System.out.println(levelName);
                System.out.println(timeLimit);
                System.out.println(height);
                System.out.println(width);


                // Process tiles line by line
                for (int i = 0; i < height; i++) {
                    System.out.println(i);
                    // Check if there is another line available before reading
                    if (scanner.hasNextLine()) {
                        System.out.println("NewLine");
                        String line = scanner.nextLine();
                        processTileLine(tileGc, line, i);
                    } else {
                        // Handle the case where there are not enough lines
                        if (!scanner.hasNextLine()) {
                            throw new IllegalArgumentException("Not enough lines in the input");
                        }
                    }
                }

                //finished with level now all others
                while (scanner.hasNext()) {
                    String itemInfo = scanner.nextLine();

                    // Check if the line starts with "K" or "C"
                    if (itemInfo.startsWith("K") || itemInfo.startsWith("C")) {
                        processItemLine(itemGc, itemInfo);
                    } else {
                        processEntityLine(itemGc, itemInfo);
                    }
                }

            }
        }
    }

    /**
     *  Processes a line of com.example._cs250a2.tile information and draws the tiles on the specified GraphicsContext.
     *  Each character in the line represents a com.example._cs250a2.tile, and the tiles are drawn on the specified
     *  GraphicsContext at the corresponding positions.
     * @param tilegc The GraphicsContext used for drawing.
     * @param line The line containing com.example._cs250a2.tile information.
     * @param lineNumber The line number in the level grid.
     */
    private static void processTileLine(GraphicsContext tilegc, String line, int lineNumber) {
        double tileSize = 50; // Assuming default tile size is 50x50
        for (int i = 0; i < line.length(); i++) {
            char currentChar = line.charAt(i);
            char nextChar = (i < line.length() - 1) ? line.charAt(i + 1) : ' ';
            System.out.println(currentChar);

            // Check if the currentChar is 'B' or 'T' and the nextChar is a digit
            //need to fix need help
            if ((currentChar == 'B' || currentChar == 'T') && Character.isDigit(nextChar)) {
                int pairedNumber = Character.getNumericValue(nextChar);
                System.out.println("B found number is:");
                System.out.println(pairedNumber);
                drawTile(tilegc, i * tileSize, lineNumber * tileSize, tileSize, currentChar, pairedNumber);
                System.out.println("Skipping line");
                i++; // Skip the next character (the digit) since we've paired it with 'B' or 'T'
            } else {
                drawTile(tilegc, i * tileSize, lineNumber * tileSize, tileSize, currentChar, -1);
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
        Tile tile = null;
        //fix as tile is not absract
//        switch (tileType) {
//            case 'P':
//                tile = new PathTile();
//                break;
//            case 'D':
//                tile = new DirtTile();
//                break;
//            case 'U':
//                tile = new WallTile();
//                break;
//            case 'E':
//                tile = new ExitTile();
//                break;
//            case 'B':
//                tile = new ButtonTile(pairedNumber);
//                break;
//            case 'T':
//                tile = new TrapTile(pairedNumber);
//                break;
//            case 'W':
//                tile = new WaterTile();
//                break;
//            case 'S':
//                tile = new ChipSocketTile();
//                break;
//            case 'I':
//                tile = new IceTile();
//                break;
//            case 'C':
//                tile = new ComputerChipTile();
//                break;
//            case 'L':
//                tile = new LockedDoorTile();
//                break;
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
     * @param itemGc The GraphicsContext used for drawing or handling entities.
     * @param itemInfo The string containing information about the entity.
     */
    private static void processItemLine(GraphicsContext itemGc, String itemInfo) {
        // Split the itemInfo into parts using space as the delimiter
        String[] parts = itemInfo.split(" ");

        // Ensure that there are at least three parts (item, x, y)
        if (parts.length >= 3) {
            // Extract information
            char itemType = parts[0].charAt(0);
            int xCoordinate = Integer.parseInt(parts[1]);
            int yCoordinate = Integer.parseInt(parts[2]);

            // Process the item based on its type
            switch (itemType) {
                case 'K':
                    processKey(itemGc, xCoordinate, yCoordinate);
                    break;
                case 'C':
                    processComputerChip(itemGc, xCoordinate, yCoordinate);
                    break;
                // Add cases for other item types...
                default:
                    // Handle unknown item types or leave empty if not needed
                    break;
            }
        } else {
            // Handle the case where the input format is not as expected
            throw new IllegalArgumentException("Invalid item line: " + itemInfo);
        }
    }

    private static void processComputerChip(GraphicsContext itemGc, int x, int y) {
        System.out.println("making a computerchip");
        // Create an instance of the ComputerChipTile class
        ComputerChipTile computerChipTile = new ComputerChipTile();

        // Assuming tileSize is a variable representing the size of each tile
        double tileSize = 50;

        // Draw the ComputerChipTile at the specified coordinates
        computerChipTile.draw(itemGc, x * tileSize, y * tileSize, tileSize);
    }

    private static void processKey(GraphicsContext itemGc, int x, int y) {
        System.out.println("making a key");
        // Create an instance of the KeyTile class
        KeyTile keyTile = new KeyTile();

        // Assuming tileSize is a variable representing the size of each tile
        double tileSize = 50;

        // Draw the KeyTile at the specified coordinates
        keyTile.draw(itemGc, x * tileSize, y * tileSize, tileSize);
    }

    private static void processEntityLine(GraphicsContext entityGc, String itemInfo) {
        // Split the itemInfo into parts using space as the delimiter
        String[] parts = itemInfo.split(" ");

        // Ensure that there are at least three parts (item, x, y)
        if (parts.length >= 4) {
            // Extract information
            char itemType = parts[0].charAt(0);
            int xCoordinate = Integer.parseInt(parts[1]);
            int yCoordinate = Integer.parseInt(parts[2]);
            char direction = parts[3].charAt(0);

            // Process the item based on its type
            switch (itemType) {
                case 'F':
                    processFrog(entityGc, xCoordinate, yCoordinate, direction);
                    break;

                // Add cases for other item types...
                default:
                    // Handle unknown item types or leave empty if not needed
                    break;
            }
        } else {
            // Handle the case where the input format is not as expected
            throw new IllegalArgumentException("Invalid item line: " + itemInfo);
        }
    }

    private static void processFrog(GraphicsContext itemGc, int x, int y, char direction) {
        System.out.println("making a frog");
        System.out.println(x);
        System.out.println(y);
        System.out.println(direction);

    }


}
