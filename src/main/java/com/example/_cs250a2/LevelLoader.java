package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@code LevelLoader} loads the levels of game
 * @author
 * @version 1.0
 */
public class LevelLoader {

    private static final int FROG_SPEED = 2;
    private static final int PINK_BALL_SPEED = 1;
    private static final int BUG_SPEED = 1;

    /**
     * Stores a mapping of buttons in the game, where each button is identified by an integer key.
     */
    private static final Map<Integer, Button> BUTTONS = new HashMap<>();

    /**
     * Stores a mapping of traps in the game, with each trap associated with an integer key.
     */
    private static final Map<Integer, Trap> TRAPS = new HashMap<>();

    /**
     * Holds a list of all levels available in the game.
     */
    private final static ArrayList<Level> LEVELS = new ArrayList<>();

    /**
     * Represents the current level that the player is navigating through.
     */
    private static Level currentLevel;



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

    /**
     * Holds the count of entities in the current level.
     */
    private static int entityCount;

    /**
     * The starting position [x, y] of the player in the level.
     */
    static final int[] PLAYER_START_POSITION = new int[2]; // [x, y] position

    /**
     * The size of each tile or entity in the level.
     */
    private static final int SIZE = 32;


    /**
     * Represents the level grid.
     * This value is set during the level loading process.
     */
    private static ArrayList<ArrayList<Tile>> tileGrid = new ArrayList<>();
    private static ArrayList<Entity> entityList = new ArrayList<>();
    private static ArrayList<Item> itemList = new ArrayList<>();

    /**
     * Updates level information from a given level file.
     * Reads and sets various properties of the level including the name, time limit,
     * dimensions, and counts of entities.
     *
     * @param levelName The name of the level file (without extension) to be loaded.
     */
    public static void updateLevelInformation(String levelName) {
        Scanner scanner =
                new Scanner(Objects.requireNonNull(LevelLoader.class.getResourceAsStream("levels/"+levelName+".txt")));
        // Read level information
        levelName = scanner.nextLine().split(": ")[1];
        timeLimit = Integer.parseInt(scanner.nextLine().split("= ")[1]);
        String[] dimensions = Arrays.copyOfRange(scanner.nextLine().split(" "), 2, 4);
        int width = Integer.parseInt(dimensions[0]);
        int height = Integer.parseInt(dimensions[1]);
        LevelLoader.width = width;
        LevelLoader.height = height;
        entityCount = countFileLines(LevelLoader.class.getResourceAsStream("levels/"+levelName+".txt")) - 4 - height;
    }

    /**
     * Reads level information to draw the level. The InputStream is expected to contain structured data representing
     * level layout, including tiles and other entities.
     * @param gc The GraphicsContext used for drawing.
     * @param inputStream The InputStream containing the level information.
     */
    public static void readLevel(GraphicsContext gc, InputStream inputStream, GameController gameController) {
        Scanner scanner = new Scanner(inputStream);
        // Read level information
        String levelName = scanner.nextLine().split(": ")[1];
        int timeLimit = Integer.parseInt(scanner.nextLine().split("= ")[1]);
        setTimeLimit(timeLimit);
        String[] dimensions = Arrays.copyOfRange(scanner.nextLine().split(" "), 2, 4);
        int width = Integer.parseInt(dimensions[0]);
        int height = Integer.parseInt(dimensions[1]);
        scanner.nextLine(); // Skip empty line


        tileGrid = new ArrayList<>();
        ArrayList<ArrayList<String>> tileMatchesGrid = new ArrayList<>();
        Pattern pattern = Pattern.compile("([A-Z])([0-9])?");
        int i = 0;
        // Process tiles
        while (i < height) {
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
        ArrayList<ArrayList<String>> newTileMatchesGrid =
                flipStringsVertically(rotateStringsCounterClockwise(tileMatchesGrid));

        // Process other entities (monsters, player, items, etc.)
        ArrayList<ArrayList<String>> entityMatchesGrid = new ArrayList<>();
        Pattern newPattern = Pattern.compile("([A-Z])([0-9]+)([A-Z])?");
        while (scanner.hasNext()) {
            String entityInfo = scanner.nextLine(); // example: Frog 1 1
            Matcher matcher = newPattern.matcher(entityInfo); // example: F, 1, 1
            ArrayList<String> matchesList = new ArrayList<>(); // example: [F, 1, 1]

            while (matcher.find()) {
                String match = matcher.group(1)
                        + (matcher.group(2) != null ? matcher.group(2) : "")
                        + (matcher.group(3) != null ? matcher.group(3) : "");
                matchesList.add(match);
            }
            entityMatchesGrid.add(matchesList);
        }

        i = 0;
        while (i < width) {
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
                    if (entitiesMatchesArray[j].toCharArray()[0] == 'C'
                            || entitiesMatchesArray[j].toCharArray()[0] == 'K') {
                        itemList.add(processItem(gc, entitiesMatchesArray[j].toCharArray()));
                    } else  {
                        entityList.add(processEntity(gc, entitiesMatchesArray[j].toCharArray(), gameController));
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Index out of bounds");
            }
        }

        currentLevel = new Level(levelName, timeLimit, width, height, tileGrid, itemList, entityList);
        LEVELS.add(currentLevel);

        drawLevel(gc, currentLevel);
    }


    public static void drawLevel(GraphicsContext gc, Level level) {
        tileGrid = level.getTileGrid();
        itemList = level.getItemList();
        entityList = level.getEntityList();
        drawTiles(gc);
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
     * KEY
     * Path: P
     * Dirt: D
     * Wall: U
     * Exit: E
     * Button: B(n) - n is the button number
     * Trap: T
     * Water: W
     * Chip Socket: S(n) - n is the number of chips required
     * Ice: I(n) - n is the ice type
     * Block: O
     * Locked Door: L
     * Frog: F
     * Pink Ball: G
     * Bug: Z
     * Player:?
     * Computer Chip: C
     * Key: K (R, G, B, Y) - R is red, G is green, B is blue, Y is yellow
     *
     * @param gc The GraphicsContext used for drawing.
     * @param tile The current tile processed.
     * @param x The X position of the tile.
     * @param y The Y position of the tile.
     * @return The tile that was processed.
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
                int buttonNum = Integer.parseInt(String.valueOf(tile[1]));
                Button button = new Button(buttonNum, x, y);
                BUTTONS.put(buttonNum, button);
                return button;

            case 'T':
                int trapNum = Integer.parseInt(String.valueOf(tile[1]));
                Trap trap = new Trap(trapNum, x, y);
                TRAPS.put(trapNum, trap);
                return trap;
            case 'W':
                return new Water(x, y);
            case 'S':
                return new ChipSocket(
                        Integer.parseInt(String.valueOf(tile[1])), x, y);
            case 'I':
                return new Ice(x, y, tile[1]);
            case 'L':
                return new LockedDoor(x, y, tile[1]);
            default:
                // Handle unknown com.example._cs250a2.tile types or leave empty if not needed
                return null;
        }

    }

    /**
     * Processes and organizes entities based on their type and coordinates.
     * It creates different entity objects based on the character codes and adds them to the game.
     *
     * @param gc The GraphicsContext for drawing entities.
     * @param entity Array of characters representing the entity.
     * @param gameController The GameController handling the game logic.
     * @return The created Entity object.
     */
    public static Entity processEntity(GraphicsContext gc, char[] entity, GameController gameController) {
        switch (entity[0]){
            case 'F':
                return new Frog(FROG_SPEED, 'w', new int[]{entity[1]-'0', entity[2]-'0'},
                        gameController);
            case 'G':
                return new PinkBall(PINK_BALL_SPEED, 'w', new int[]{entity[1]-'0', entity[2]-'0'},
                        gameController);
            case 'Z':
                return new Bug(BUG_SPEED, 'w', new int[]{entity[1]-'0', entity[2]-'0'},
                        true, gameController);
            case 'O':
                return new Block(entity[1]-'0', entity[2]-'0');
            case 'Q':
                int playerX = entity[1] - '0';
                int playerY = entity[2] - '0';
                PLAYER_START_POSITION[0] = playerX; // Store player's starting X position
                PLAYER_START_POSITION[1] = playerY; // Store player's starting Y position
                return new Player(playerX, playerY, gameController);
            default:
                return null;
        }
    }

    /**
     * Processes and returns an Item object based on the provided character array.
     *
     * @param gc The GraphicsContext for drawing items.
     * @param item Character array representing the item's properties.
     * @return The created Item object.
     */
    public static Item processItem(GraphicsContext gc, char[] item) {
        int x = item[1] - '0';
        int y = item[2] - '0';

        switch (item[0]){
            case 'C':
                return new Chip(x, y);
            case 'K':
                char colourCode = item[3];
                return new Key(x, y, colourCode);
            default:
                return null;
        }
    }
    /**
     * Removes the specified item from the current level's item list.
     *
     * @param item The item to be removed.
     */
    public static void removeItem(Item item) {
        itemList.remove(item);
    }

    /**
     * Draws the current level's tiles on the screen.
     *
     * @param gc The GraphicsContext used for drawing.
     */
    public static void drawTiles(GraphicsContext gc) {
        for (ArrayList<Tile> row : getTileGrid()) {
            for (Tile tile : row) {
                tile.draw(gc, tile.getX(), tile.getY(), SIZE);
            }
        }
    }

    /**
     * Draws the entities of the current level.
     *
     * @param gc The GraphicsContext used for drawing entities.
     */
    public static void drawEntities(GraphicsContext gc) {
        System.out.println("drawing entites" + entityList);
        for (Entity entity : getEntityList()) {
            if (entity != null) {
                entity.draw(gc, entity.getX(), entity.getY(), SIZE);
            }
        }
    }

    /**
     * Draws all items in the current level.
     *
     * @param gc The GraphicsContext used for drawing items.
     */
    public static void drawItems(GraphicsContext gc) {
        for (Item item : getItemList()) {
            if (item != null) {
                item.draw(gc, item.getX(), item.getY(), SIZE);
            }
        }
    }

    /**
     * Counts the number of lines in a file from an InputStream.
     *
     * @param inputStream The InputStream of the file to be counted.
     * @return The total number of lines in the file.
     */
    private static int countFileLines(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        int count = 0;
        while (scanner.hasNextLine()) {
            count++;
            scanner.nextLine();
        }
        return count;
    }

    /**
     * Retrieves the current count of entities in the level.
     *
     * @return The total number of entities.
     */
    public static int getEntityCount() {
        return entityCount;
    }

    /**
     * Returns the tile at the specified coordinates.
     * If the coordinates are out of bounds, returns a Wall tile.
     *
     * @param x The x-coordinate of the tile.
     * @param y The y-coordinate of the tile.
     * @return The Tile object at the given coordinates.
     */
    public static Tile getTile(int x, int y) {
        try {
            return tileGrid.get(x).get(y);
        } catch (IndexOutOfBoundsException e) {
            return new Wall(x, y);
        }
    }

    /**
     * Retrieves the first entity of the specified class from the current level.
     *
     * @param cls The class of the entity to retrieve.
     * @return The first entity of the specified class, or null if none found.
     */
    public static Entity getEntityByClass(Class<?> cls) {
        for (Entity entity : entityList) {
            if (entity != null) {
                if (entity.getClass() == cls) {
                    return entity;
                }
            }
        }
        return null;
    }


    /**
     * Retrieves the entity located at the specified coordinates.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return The Entity at the specified coordinates, or null if none found.
     */
    public static Entity getEntityWithCoords(int x, int y) {
        for (Entity entity : entityList) {
            if (entity != null) {
                if (entity.getX() == x && entity.getY() == y) {
                    return entity;
                }
            }
        }
        return null;
    }

    /**
     * Replaces the tile at the specified coordinates with a new tile.
     *
     * @param x The x-coordinate of the tile.
     * @param y The y-coordinate of the tile.
     * @param newTile The new Tile to place at the coordinates.
     */
    public static void setTile(int x, int y, Tile newTile) {
        try {
            tileGrid.get(x).set(y, newTile);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid tile position: " + x + ", " + y);
        }
    }

    /**
     * Gets the height of the current level.
     *
     * @return The height of the level.
     */
    public static int getHeight() {
        return height;
    }

    /**
     * Gets the width of the current level.
     *
     * @return The width of the level.
     */
    public static int getWidth() {
        return width;
    }

    /**
     * Gets the time limit of the current level.
     *
     * @return The time limit of the level.
     */
    public static int getTimeLimit() {
        return timeLimit;
    }

    /**
     * Sets the time limit of the current level.
     *
     * @param limit The new time limit.
     */
    private static void setTimeLimit(int limit) {
        timeLimit = limit;
    }

    /**
     * Links buttons to their corresponding traps in the level.
     * Each button is linked to a trap with the same number.
     */
    public static void linkButtonsToTraps() {
        for (Map.Entry<Integer, Button> entry : BUTTONS.entrySet()) {
            int buttonNum = entry.getKey();
            Button button = entry.getValue();
            Trap trap = TRAPS.get(buttonNum);
            if (trap != null) {
                button.linkToTrap(trap);
                trap.linkToButton(button);
            }
        }
    }

    /**
     * Gets the grid of tiles for the current level.
     *
     * @return A 2D ArrayList representing the grid of tiles.
     */
    public static ArrayList<ArrayList<Tile>> getTileGrid() {
        return tileGrid;
    }

    /**
     * Gets the list of entities in the current level.
     *
     * @return An ArrayList of Entity objects.
     */
    public static ArrayList<Entity> getEntityList() {
        return entityList;
    }

    /**
     * Gets the list of items in the current level.
     *
     * @return An ArrayList of Item objects.
     */
    public static ArrayList<Item> getItemList() {
        return itemList;
    }

    /**
     * Clears all tiles, entities, and items from the current level.
     */
    public static void clearLevel() {
        tileGrid.clear();
        entityList.clear();
        itemList.clear();
        Monster.clear();
    }
}
