package com.example._cs250a2;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import java.io.File;
import java.util.List;

/**
 * The {@code GameController} class handles the game logic,
 * user interface, and event handling.
 * It is responsible for managing profiles, levels, and the game state.
 * The class is linked with the corresponding FXML file for UI components.
 * @author Ben Foord
 * @version 1.0
 */
public class GameController {

    private static final int STRING_STARTING_INDEX = 0;
    private static final int STRING_ENDING_INDEX = 4;
    private static final int CHIP_VALUE = 15;
    private static final int PLAYER_SIZE = 32;
    private static final int LEVEL_NUMBER = 5;

    /**
     * The maximum number of ticks.
     */
    private static final int MAXIMUM_TICKS = 5;

    /**
     * The canvas for the game.
     */
    @FXML
    private Canvas canvas;

    /**
     * The button to show the high scores.
     */
    @FXML
    private Button showHighScoresButton;

    /**
     * The score for the game.
     */
    private int score;

    /**
     * The tick count for the game.
     */
    private int tickCount = 0;

    /**
     * The label for the time remaining.
     */
    @FXML
    private Label timeRemainingLabel;

    /**
     * The label for the score.
     */
    @FXML
    private Label scoreLabel;

    /**
     * The time remaining property.
     */
    private StringProperty timeRemainingProperty;

    /**
     * The current profile.
     */
    private Profile currentProfile;

    /**
     * The choice box for the profiles.
     */
    @FXML
    private ChoiceBox<Profile> profileChoiceBox;

    /**
     * The button to show the high scores.
     */
    @FXML
    private TableView<ScoreEntry> highScoresTable;


    /**
     * object property for the current profile. used to update the current profiles name
     */
    @FXML
    private final ObjectProperty<Profile> currentProfileProperty = new SimpleObjectProperty<>();

    /**
     * The button to select the profile.
     */
    @FXML
    private Button selectNameButton;

    /**
     * The button to start the game.
     */
    @FXML
    private Button startButton;

    /**
     * The button to delete the profile.
     */
    @FXML
    private Button deleteButton;

    /**
     * The text field to create a new profile.
     */
    @FXML
    private TextField createName;

    /**
     * The button to create a new profile.
     */
    @FXML
    private Button createButton;

    /**
     * The list of profiles in the profiles choice box.
     */
    private final ObservableList<Profile> profiles = FXCollections.observableArrayList();

    /**
     * The current level.
     */
    private Level currentLevel;

    /**
     * The name of the current level.
     */
    private String levelName;

    /**
     * The time limit for the game.
     */
    private int timeLimit;

    /**
     * The choice box for the levels.
     */
    @FXML
    private ChoiceBox<Level> levelChoiceBox;

    /**
     * The button to load the game.
     */
    @FXML
    private Button loadGameButton;

    /**
     * The button to select the level.
     */
    private final ObservableList<Level> levels = FXCollections.observableArrayList();

    /**
     *  The button to select the level.
     */
    @FXML
    private Button selectLevelButton;

    /**
     * The label for the selected profile.
     */
    @FXML
    private Label selectedProfileLabel;

    /**
     * The label for the selected level.
     */
    @FXML
    private Label selectedLevelLabel;
    /**
     * The high score object.
     */
    private final HighScoreManager highScore = new HighScoreManager();


    /**
     * The object property for the current level in the level choice box.
     */
    private final ObjectProperty<Level> currentLevelProperty = new SimpleObjectProperty<>();


    /**
     * Processes the key event to move the player, redraws the game, and consumes the event.
     * The player's movement is determined by the provided KeyEvent.
     *
     * @param event The KeyEvent representing the key press event.
     */
    public void processKeyEvent(KeyEvent event) {
        Player player = (Player) LevelLoader.getEntityByClass(Player.class);
        if (player != null) {
            player.move(event);
        }
        drawGame();
        event.consume();
    }

    /**
     * Returns the canvas for the game.
     *
     * @return The canvas for the game.
     */
    public Canvas getCanvas() {
        return canvas;
    }

    /**
     * Sets the canvas for the game.
     *
     * @param canvas The canvas to be set.
     */
    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    /**
     * Draws the game on the canvas, including the level, entities, items, player, and key.
     * Uses the GraphicsContext of the canvas for drawing.
     */
    public void drawGame() {
        // Get the Graphic Context of the canvas. This is what we draw on.
        GraphicsContext gc = canvas.getGraphicsContext2D();
        // Clear canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        LevelLoader.drawTiles(gc);
        LevelLoader.drawEntities(gc);
        LevelLoader.drawItems(gc);

        // Draw player at current location
        Player player = (Player) LevelLoader.getEntityByClass(Player.class);
        if (player != null){
        player.draw(gc, player.getX(), player.getY(), PLAYER_SIZE);
        }
        //Draw key at current location
    }

    /**
     * The tick method is called to run code every second.
     * Mainly the timer and monsters' movement.
     */
    public void tick() {
        //update the timer every tick if its 0 end the game
        updateTimer();
        //move the monster every tick
        for (Entity entity : LevelLoader.getEntityList()) {

            if (entity instanceof Monster monster) {
                monster.tickMove(tickCount);
            }
        }
        tickCount++;
        if (tickCount >= MAXIMUM_TICKS) {
            tickCount = 0;
        }
        drawGame();
        updateScoreDisplay();
    }

    /**
     * Updates the game timer, decrements the remaining time, and handles game over conditions.
     * If the time limit reaches or falls below zero, the game over sequence is initiated, and the tick timeline stops.
     */
    public void updateTimer() {
        timeLimit--;
        if (timeLimit <= 0) {
            clearLevel();
            tickTimeline.stop();
        }

        //sets the score for the current profile
        if (currentProfile != null) {
            currentProfile.setScoreForLevel(levelName, calculateScore());
        }
        // Update time remaining label
        updateTimeRemaining(timeLimit);
    }

    /**
     * Calculates the score based on the time limit and chips in the inventory.
     *
     * @return The calculated score.
     */
    private int calculateScore() {
        int score = timeLimit;
        // Add 15 points for every chip in the inventory
        Player player = (Player) LevelLoader.getEntityByClass(Player.class);
        if (player != null) {
            score += CHIP_VALUE * player.getChips();
        }
        return score;
    }

    /**
     * Updates the score display.
     */
    private void updateScoreDisplay() {
        scoreLabel.setText("Score: " + calculateScore());
    }


    /**
     * Sets the time limit for the game.
     *
     * @param timeLimit The time limit to be set for the game, representing the remaining time.
     */
    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    /**
     * Initializes the game controller, including loading profiles and levels, and binding UI components.
     */
    @FXML
    public void initialize() {
        List<Profile> loadedProfiles = ProfileFileManager.loadAllProfiles();
        profiles.addAll(loadedProfiles);

        profileChoiceBox.setItems(profiles);

        // scan the levels directory and add all the levels to the levels list
        File directory = new File("src/main/resources/com/example/_cs250a2/levels");
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                String fileName = file.getName();
                String levelName = fileName.substring(STRING_STARTING_INDEX, fileName.length() - STRING_ENDING_INDEX);
                levels.add(new Level(levelName));
            }
        }

        //levels.addAll(new Level("level1"), new Level("level2"), new Level("level3"));
        levelChoiceBox.setItems(levels);

        selectNameButton.disableProperty().bind(profileChoiceBox.valueProperty().isNull());
        levelChoiceBox.disableProperty().bind(profileChoiceBox.valueProperty().isNull());
        selectLevelButton.disableProperty().bind(levelChoiceBox.valueProperty().isNull());
        selectNameButton.setOnAction(event -> handleSelectButton());
        deleteButton.setOnAction(event -> handleDeleteButton());
        createButton.setOnAction(event -> handleCreateButton());
        selectLevelButton.setOnAction(event -> handleSelectLevelButton());
        startButton.setOnAction(event -> handleStartButton());
        loadGameButton.setOnAction(event -> handleLoadGameButton());


        timeRemainingProperty = new SimpleStringProperty();
        timeRemainingLabel.textProperty().bind(timeRemainingProperty);


        selectedProfileLabel.textProperty().bind(currentProfileProperty.asString());

        selectedLevelLabel.textProperty().bind(Bindings.createStringBinding(() ->
                        "Selected level: \n" + (currentLevel != null ? currentLevel.getName() : ""),
                currentLevelProperty()));
    }
    private final Timeline tickTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> tick()));

    /**
     * Handles the start button event,
     * including loading the level,
     * updating the time limit,
     * and starting the tick timeline.
     */
    private void handleStartButton() {

        clearLevel();

        score = calculateScore();


        try {
            if (currentLevel == null) {
                throw new IllegalArgumentException("No level selected.");
            }

            if (currentProfile == null) {
                throw new IllegalArgumentException("No profile selected.");
            }


            tickTimeline.setCycleCount(Animation.INDEFINITE);

            setCurrentLevel(currentLevel);
            levelName = currentLevel.getName();
            LevelLoader.updateLevelInformation(levelName);

            GraphicsContext gc = canvas.getGraphicsContext2D();


            // Clear the level before loading a new one
            LevelLoader.clearLevel();
            LevelLoader.readLevel(gc, Game.class.getResourceAsStream("levels/" + levelName + ".txt"), this);
            LevelLoader.linkButtonsToTraps(); // linking buttons to traps
            tickTimeline.play();

            setTimeLimit(LevelLoader.getTimeLimit());

            currentProfile.setScoreForLevel(levelName, timeLimit);


            drawGame();
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    /**
     * Clears the current level, stops timers, and clears the canvas.
     */
    public void clearLevel() {
        // Stop the tick timeline
        tickTimeline.stop();
        // Clear the canvas
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        // Clear other level-related resources
        timeLimit = 0;
        LevelLoader.clearLevel();
        // Clear the inventory
        Player player = (Player) LevelLoader.getEntityByClass(Player.class);
        if (player != null) {
            player.clearInventory();
        }
        resetPlayerPosition();
        Monster.clearMonsterList();
    }

    /**
     * Resets the player position to the start position.
     */
    private void resetPlayerPosition() {
        Player player = (Player) LevelLoader.getEntityByClass(Player.class);
        if (player != null) {
            int startX  = LevelLoader.PLAYER_START_POSITION[0];
            int startY = LevelLoader.PLAYER_START_POSITION[1];
            player.setPosition(startX, startY); // Reset player position to start
            drawGame(); // Redraw the game to reflect the new player position
        }
    }

    /**
     * Handles the load game button event, including clearing the level.
     */
    private void handleLoadGameButton() {
        clearLevel();
    }

    /**
     * Handles the select button event, including setting the current profile
     */
    private void handleSelectButton() {
        currentProfile = profileChoiceBox.getValue();
        currentProfileProperty.set(currentProfile);
    }

    /**
     * Handles the delete button event, including removing the selected profile from the list of profiles.
     */
    private void handleDeleteButton() {
        Profile selectedProfile = profileChoiceBox.getValue();
        profiles.remove(selectedProfile);
    }

    /**
     * Handles the create button event, including creating a new profile and adding it to the list of profiles.
     */
    private void handleCreateButton() {
        String newProfileName = createName.getText();
        Profile newProfile = new Profile(newProfileName);
        profiles.add(newProfile);

        ProfileFileManager.saveAllProfiles(profiles);
    }

    /**
     * Handles the show high scores event, including adding the current score to the high scores
     * and saving them to file.
     */
    public void showHighScores() {

        // Assuming highScore is an instance of HighScoreManager
        highScore.addScore(levelName, currentProfile.getName(), currentProfile.getScoreForLevel(levelName));

        // Assuming highScoresTable is your TableView
        highScoresTable.getColumns().clear(); // Clear existing columns

        // Create columns
        TableColumn<ScoreEntry, String> playerNameColumn = new TableColumn<>("Player");
        playerNameColumn.setCellValueFactory(cellData -> cellData.getValue().profileNameProperty());

        TableColumn<ScoreEntry, Integer> scoreColumn = new TableColumn<>("Score");
        scoreColumn.setCellValueFactory(cellData -> cellData.getValue().scoreProperty().asObject());

        // Add columns to the table
        highScoresTable.getColumns().addAll(playerNameColumn, scoreColumn);

        // Load and display data
        for (Level level : levels) {
            List<ScoreEntry> highScoresList = highScore.getHighScores(level.getName());

            if (!highScoresList.isEmpty()) {
                int rank = 1;
                for (ScoreEntry highScoreEntry : highScoresList) {
                    rank++;
                }
                currentProfile.nextLevel();


                // Save the high scores for the current level
                highScore.saveHighScores(highScoresList, level.getName());

                // Set the items in the TableView
                highScoresTable.setItems(FXCollections.observableArrayList(highScoresList));

            } else {
                System.out.println("No high scores for " + level.getName());
            }
        }
        clearLevel();
    }

    /**
     * Handles the select level button event, including setting the current level.
     */
    private void handleSelectLevelButton() {
        try {
            Level selectedLevel = levelChoiceBox.getValue();
            if (selectedLevel == null) {
                throw new IllegalArgumentException("No level selected.");
            }
            int selectedLevelNumber = Integer.parseInt(selectedLevel.getName().substring(LEVEL_NUMBER));
            if (currentProfile == null || selectedLevelNumber > currentProfile.getLevelReached() + 1) {
                throw new IllegalArgumentException("You must complete the previous level first.");
            }
            currentLevel = selectedLevel;
            setCurrentLevel(currentLevel);
            currentLevelProperty.set(currentLevel);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Updates the time remaining label with the provided time remaining.
     *
     * @param timeRemaining The time remaining to be set.
     */
    public void updateTimeRemaining(int timeRemaining) {
        timeRemainingProperty.set("Time Remaining: \n" + timeRemaining);
    }

    /**
     * Returns the current level as a JavaFX ObjectProperty. This property is used for binding
     * and listening to changes in the current level.
     *
     * @return The current level as an ObjectProperty.
     */
    public ObjectProperty<Level> currentLevelProperty() {
        return currentLevelProperty;
    }

    /**
     * Sets the current level.
     *
     * @param level The level to be set.
     */
    public void setCurrentLevel(Level level) {
        currentLevelProperty.set(level);
    }

}
