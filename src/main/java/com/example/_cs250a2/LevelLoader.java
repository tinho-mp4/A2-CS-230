package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LevelLoader {
    /**
     * Represents the time limit for completing the level.
     * This value is set during the level loading process.
     */

    private static int timeLimit;
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
    private static int entityCount;
    /**
     * Represents the name of the level.
     * This value is set during the level loading process.
     */
    private String levelName;

    /**
     * Represents the level grid.
     * This value is set during the level loading process.
     */
    private static ArrayList<ArrayList<Tile>> tileGrid = new ArrayList<>();
    private static ArrayList<ArrayList<Entity>> entityGrid = new ArrayList<>();
    private static ArrayList<ArrayList<Item>> itemGrid = new ArrayList<>();

    public LevelLoader() {
        Scanner scanner = new Scanner(getClass().getResourceAsStream("levels/level2.txt"));
        // Read level information
        String levelName = scanner.nextLine().split(": ")[1];
        int timeLimit = Integer.parseInt(scanner.nextLine().split("= ")[1]);
        String[] dimensions = Arrays.copyOfRange(scanner.nextLine().split(" "), 2, 4);
        int width = Integer.parseInt(dimensions[0]);
        int height = Integer.parseInt(dimensions[1]);
        this.width = width;
        this.height = height;
        this.entityCount = countFileLines(getClass().getResourceAsStream("levels/level2.txt")) - 4 - height;
    }

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
        int width = Integer.parseInt(dimensions[0]);
        int height = Integer.parseInt(dimensions[1]);
        scanner.nextLine(); // Skip empty line


        tileGrid = new ArrayList<>();
        ArrayList<ArrayList<String>> tileMatchesGrid = new ArrayList<>();
        Pattern pattern = Pattern.compile("([A-Z])([0-9])?");
        int i = 0;
        // Process tiles
        while(i < height) {
            String currentLine = scanner.nextLine(); // example: PSPS
            Matcher matcher = pattern.matcher(currentLine); // example: P, S, P, S
            ArrayList<String> matchesList = new ArrayList<>(); // example: [P, S, P, S]

            while (matcher.find()) {
                String match = matcher.group(1) + (matcher.group(2) != null ? matcher.group(2) : "");
                matchesList.add(match);
            }
            tileMatchesGrid.add(matchesList);
            i++;
        }
        ArrayList<ArrayList<String>> newTileMatchesGrid = flipStringsVertically(rotateStringsCounterClockwise(tileMatchesGrid));

        // Process other entities (monsters, player, items, etc.)
        ArrayList<ArrayList<String>> entityMatchesGrid = new ArrayList<>();
        Pattern newPattern = Pattern.compile("([A-Z])([0-9][0-9])?(A-Z)?");
        while (scanner.hasNext()) {
            String entityInfo = scanner.nextLine(); // example: Frog 1 1
            Matcher matcher = newPattern.matcher(entityInfo); // example: F, 1, 1
            ArrayList<String> matchesList = new ArrayList<>(); // example: [F, 1, 1]

            while (matcher.find()) {
                String match = matcher.group(1) + (matcher.group(2) != null ? matcher.group(2) : "");
                matchesList.add(match);
            }
            entityMatchesGrid.add(matchesList);
        }

        i = 0;
        while(i < width) {
            ArrayList<String> matchesLine = newTileMatchesGrid.get(i);
            // make this be able to be null

            String[] matchesArray = matchesLine.toArray(new String[0]); // example : [P, D, U, E]
            ArrayList<Tile> levelRow = new ArrayList<>();
            try {
                for (int j = 0; j < matchesArray.length; j++) {
                    levelRow.add(processTile(gc, matchesArray[j].toCharArray(), i, j));
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Tile Index out of bounds");
            }

            tileGrid.add(levelRow);

            i++;
        }

        for (int k = 0; k < getEntityCount(); k++) {
            ArrayList<String> entityLine = entityMatchesGrid.get(k);
            String[] entitiesMatchesArray = entityLine.toArray(new String[0]);
            ArrayList<Entity> entityRow = new ArrayList<>();
            ArrayList<Item> itemRow = new ArrayList<>();
            try {
                for (int j = 0; j < entitiesMatchesArray.length; j++) {
                    if (entitiesMatchesArray[j].toCharArray()[0] == 'C' || entitiesMatchesArray[j].toCharArray()[0] == 'K') {
                        itemRow.add(processItem(gc, entitiesMatchesArray[j].toCharArray()));
                    } else
                        entityRow.add(processEntity(gc, entitiesMatchesArray[j].toCharArray()));
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Index out of bounds");
            }
            if (entityRow.size() > 0) {
                entityGrid.add(entityRow);
            }
            if (itemRow.size() > 0) {
                itemGrid.add(itemRow);
            }
        }


        drawLevel(gc);
        drawEntities(gc);
        drawItems(gc);
    }

    public static ArrayList<ArrayList<String>> rotateStringsCounterClockwise(ArrayList<ArrayList<String>> strings) {
        int rows = strings.size();
        int cols = strings.get(0).size();

        ArrayList<ArrayList<String>> result = new ArrayList<>();
        for (int col = cols - 1; col >= 0; col--) {
            ArrayList<String> newRow = new ArrayList<>();
            for (int row = 0; row < rows; row++) {
                try {
                    newRow.add(strings.get(row).get(col));
                } catch (IndexOutOfBoundsException e) {
                    newRow.add("W");
                }
            }
            result.add(newRow);
        }
        return result;
    }

    public static ArrayList<ArrayList<String>> flipStringsVertically(ArrayList<ArrayList<String>> strings) {
        Collections.reverse(strings);
        return strings;
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
                    return new Button(x, y, tile[1]);
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
               case 'L':
                    return new LockedDoor(x, y, tile[1]);
                default:
                    // Handle unknown com.example._cs250a2.tile types or leave empty if not needed
                    return null;
        }

    }

    public static Entity processEntity(GraphicsContext gc, char[] entity) {
        switch (entity[0]){
            case 'F':
                return new Frog(5, 'w', new int[]{entity[1]-'0', entity[2]-'0'});
            case 'G':
                return new PinkBall(5, 'd', new int[]{entity[1]-'0', entity[2]-'0'});
            case 'Z':
                return new Bug(5, 'd', new int[]{entity[1]-'0', entity[2]-'0'}, false);
            default:
                return null;
        }
    }

    public static Item processItem(GraphicsContext gc, char[] item) {
            switch (item[0]){
            case 'C':
                return new Chip(item[1]-'0', item[2]-'0');
            case 'K':
                return new Key(item[1]-'0', item[2]-'0');
            default:
                return null;
        }
    }

    public static void drawLevel(GraphicsContext gc) {;
        for (ArrayList<Tile> row : getTileGrid()) {
            for (Tile tile : row) {
                tile.draw(gc, tile.getX(), tile.getY(), 32);
            }
        }
    }

    public static void drawEntities(GraphicsContext gc) {
        for (ArrayList<Entity> row : getentityGrid()) {
            for (Entity entity : row) {
                entity.draw(gc, entity.getX(), entity.getY(), 32);
            }
        }
    }

    public static void drawItems(GraphicsContext gc) {
        for (ArrayList<Item> row : getItemGrid()) {
            for (Item item : row) {
                item.draw(gc, item.getX(), item.getY(), 32);
            }
        }
    }

    private static int countFileLines(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        int count = 0;
        while (scanner.hasNextLine()) {
            count++;
            scanner.nextLine();
        }
        return count;
    }

    public static int getEntityCount() {
        return entityCount;
    }

    public static Tile getTile(int x, int y) {
        try {
            return tileGrid.get(x).get(y); // y might have to be reversed (height-y), since canvas y is flipped?
        } catch (IndexOutOfBoundsException e) {
            return new Wall(x, y);
        }
    }

    public static void setTile(int x, int y, Tile newTile) {
        try {
            tileGrid.get(x).set(y, newTile);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid tile position: " + x + ", " + y);
        }
    }

    public static int getHeight() {
        return height;
    }
    public static int getWidth() {
        return width;
    }


    public static ArrayList<ArrayList<Tile>> getTileGrid() {
        return tileGrid;
    }

    public static ArrayList<ArrayList<Entity>> getentityGrid() {
        return entityGrid;
    }

    public static ArrayList<ArrayList<Item>> getItemGrid() {
        return itemGrid;
    }
}
