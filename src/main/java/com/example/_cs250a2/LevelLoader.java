package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;

import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@code LevelLoader} class handles loading of levels in the game
 * @author idk
 * @version 1.0
 */
public class LevelLoader {

    private static final Map<Integer, Button> buttons = new HashMap<>();
    private static final Map<Integer, Trap> traps = new HashMap<>();

    private static ArrayList<Level> levels = new ArrayList<>();
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
    private static final ArrayList<ArrayList<Entity>> entityGrid = new ArrayList<>();
    private static final ArrayList<ArrayList<Item>> itemGrid = new ArrayList<>();

    public LevelLoader() {

    }

    public void readAllLevels() {

    }

    public static void updateLevelInformation(String levelName) {
        Scanner scanner = new Scanner(Objects.requireNonNull(LevelLoader.class.getResourceAsStream("levels/"+levelName+".txt")));
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
    public static void loadLevel(GraphicsContext gc, InputStream inputStream, GameController gameController) {
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
        Pattern newPattern = Pattern.compile("([A-Z])([0-9]+)([A-Z])?");
        while (scanner.hasNext()) {
            String entityInfo = scanner.nextLine(); // example: Frog 1 1
            Matcher matcher = newPattern.matcher(entityInfo); // example: F, 1, 1
            ArrayList<String> matchesList = new ArrayList<>(); // example: [F, 1, 1]

            while (matcher.find()) {
                String match = matcher.group(1) + (matcher.group(2) != null ? matcher.group(2) : "") + (matcher.group(3) != null ? matcher.group(3) : "");
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
                        entityRow.add(processEntity(gc, entitiesMatchesArray[j].toCharArray(), gameController));
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

        currentLevel = new Level(levelName, timeLimit, width, height, tileGrid, itemGrid, entityGrid);
        levels.add(currentLevel);

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
     * Bug: Z
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
                int buttonNum = Integer.parseInt(String.valueOf(tile[1]));
                Button button = new Button(buttonNum, x, y);
                buttons.put(buttonNum, button);
                return button;

            case 'T':
                int trapNum = Integer.parseInt(String.valueOf(tile[1]));
                Trap trap = new Trap(trapNum, x, y);
                traps.put(trapNum, trap);
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

    public static Entity processEntity(GraphicsContext gc, char[] entity, GameController gameController) {
        switch (entity[0]){
            case 'F':
                return new Frog(5, 'w', new int[]{entity[1]-'0', entity[2]-'0'});
            case 'G':
                return new PinkBall(2, 'w', new int[]{entity[1]-'0', entity[2]-'0'});
            case 'Z':
                return new Bug(3, 'd', new int[]{entity[1]-'0', entity[2]-'0'}, false);
            case 'O':
                return new Block(entity[1]-'0', entity[2]-'0');
            case 'Q':
                return new Player(entity[1]-'0', entity[2]-'0', gameController);
            default:
                return null;
        }
    }

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
    public static void removeItem(Item item) {
        for(ArrayList<Item> row: itemGrid) {
            row.removeIf(i -> i == item);
        }
    }

    public static void drawLevel(GraphicsContext gc) {
        for (ArrayList<Tile> row : getTileGrid()) {
            for (Tile tile : row) {
                tile.draw(gc, tile.getX(), tile.getY(), 32);
            }
        }
    }

    public static void drawEntities(GraphicsContext gc) {
        System.out.println("drawing entites" + entityGrid);
        for (ArrayList<Entity> row : getEntityGrid()) {
            for (Entity entity : row) {
                if (entity != null) {
                    entity.draw(gc, entity.getX(), entity.getY(), 32);
                }
            }
        }
    }


    public static void drawItems(GraphicsContext gc) {
        for (ArrayList<Item> row : getItemGrid()) {
            for (Item item : row) {
                if (item != null) {
                    item.draw(gc, item.getX(), item.getY(), 32);
                }
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
            return tileGrid.get(x).get(y);
        } catch (IndexOutOfBoundsException e) {
            return new Wall(x, y);
        }
    }

    public static Entity getEntities(int x, int y) {
        try {
            return entityGrid.get(x).get(y);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public static Entity getEntityByClass(Class<?> cls) {
        for (ArrayList<Entity> row : entityGrid) {
            for (Entity entity : row) {
                if (entity != null) {
                    if (entity.getClass() == cls) {
                        return entity;
                    }
                }
            }
        }
        return null;
    }

    public static Entity getEntityWithCoords(int x, int y) {
        for (ArrayList<Entity> row : entityGrid) {
            for (Entity entity : row) {
                if (entity != null) {
                    if (entity.getX() == x && entity.getY() == y) {
                        return entity;
                    }
                }
            }
        }
        return null;
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

    public static int getTimeLimit() {
        return timeLimit;
    }

    private static void setTimeLimit(int limit) {
        timeLimit = limit;
    }

    public static void linkButtonsToTraps() {
        for (Map.Entry<Integer, Button> entry : buttons.entrySet()) {
            int buttonNum = entry.getKey();
            Button button = entry.getValue();
            Trap trap = traps.get(buttonNum);
            if (trap != null) {
                button.linkToTrap(trap);
                trap.linkToButton(button);
            }
        }
    }

    public static Map<Integer, Button> getButtons() {
        return buttons;
    }


    public static ArrayList<ArrayList<Tile>> getTileGrid() {
        return tileGrid;
    }

    public static ArrayList<ArrayList<Entity>> getEntityGrid() {
        return entityGrid;
    }

    public static ArrayList<ArrayList<Item>> getItemGrid() {
        return itemGrid;
    }

    public static void clearLevel() {
        tileGrid.clear();
        entityGrid.clear();
        itemGrid.clear();
    }
}
