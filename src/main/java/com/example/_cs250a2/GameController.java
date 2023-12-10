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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.File;
import java.util.List;

/**
 * The GameController class handles the game logic, user interface, and event handling.
 * It is responsible for managing profiles, levels, and the game state.
 * The class is linked with the corresponding FXML file for UI components.
 *
 * @author [Ben Foord]
 * @version 1.0
 */
public class GameController {


    //Constant for the maximum number of ticks
    private static final int MAXIMUMTICKS = 5;

    //canvas for the game
    @FXML
    public Canvas canvas;

    //button to show the high scores
    @FXML
    private Button showHighScoresButton;

    @FXML
    private TextArea highScoresTextArea;

    //player object
    Player player = new Player(1, 1);

    public Timeline tickTimeline;

    public int score = 0;
    private int tickCount = 0;

    public LevelLoader levelLoader;

    @FXML
    private Label timeRemainingLabel;

    private StringProperty timeRemainingProperty;

    public String levelName;

    private int timeLimit;

    private Profile currentProfile;

    @FXML
    public Label selectedLevelLable;

    @FXML
    private ChoiceBox<Profile> profileChoiceBox;

    @FXML
    private final ObjectProperty<Profile> currentProfileProperty = new SimpleObjectProperty<>();

    @FXML
    private Button selectNameButton;

    @FXML
    private Button startButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField createName;

    @FXML
    private Button createButton;

    private ObservableList<Profile> profiles = FXCollections.observableArrayList();

    private Level currentLevel;

    @FXML
    private ChoiceBox<Level> levelChoiceBox;

    private ObservableList<Level> levels = FXCollections.observableArrayList();

    @FXML
    private Button selectLevelButton;

    @FXML
    private Label selectedProfileLabel;

    @FXML
    private Label selectedLevelLabel;
    private HighScore highScore = new HighScore();


    private final ObjectProperty<Level> currentLevelProperty = new SimpleObjectProperty<>();


    /**
     * Processes the key event to move the player, redraws the game, and consumes the event.
     * The player's movement is determined by the provided KeyEvent.
     *
     * @param event The KeyEvent representing the key press event.
     */
    public void processKeyEvent(KeyEvent event) {
        player.move(event);

        // Redraw game as the player may have moved.
        drawGame();

        // Consume the event. This means we mark it as dealt with. This stops other GUI nodes (buttons etc.) responding to it.
        event.consume();
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



        this.levelLoader.drawLevel(gc);
        this.levelLoader.drawEntities(gc);
        this.levelLoader.drawItems(gc);


        // Draw player at current location
        player.draw(gc, player.getX(), player.getY(), 32);
        //Draw key at current location

    }

    /**
     * This method is called periodically by the tick timeline
     * and would for, example move, perform logic in the game,
     * this might cause the bad guys to move (by e.g., looping
     * over them all and calling their own tick method).
     */
    public void tick() {
        //update the timer every tick if its 0 end the game
        System.out.println("tick");
        if (tickCount % 2 == 0) {
            updateTimer();
        }
        //this is redundant if you call update timer
        //if (timeLimit <= 0) {
        //    GameOver.gameEndTime();
        //    tickTimeline.stop();
        //}

        Monster.tickMove(tickCount);
        tickCount++;
        if (tickCount >= MAXIMUMTICKS) {
            tickCount = 0;
        }
        drawGame();
    }

    /**
     * Updates the game timer, decrements the remaining time, and handles game over conditions.
     * If the time limit reaches or falls below zero, the game over sequence is initiated, and the tick timeline stops.
     * Additionally, it prints time, score, level information, and updates the time remaining label.
     */
    public void updateTimer() {
        timeLimit--;
        if (timeLimit <= 0) {
            GameOver.gameEndTime();
            tickTimeline.stop();
        }
        System.out.println("Time left: " + timeLimit);
        System.out.println("Score: " + score);
        System.out.println("Level: " + levelName);
        //print the profiles score for the current level
        if (currentProfile != null) {
            System.out.println("Score for " + levelName + ": " + currentProfile.getScoreForLevel(levelName));
        }

        //sets the score for the current profile
        if (currentProfile != null) {
            currentProfile.setScoreForLevel(levelName, timeLimit);
        }

        // Update time remaining label
        updateTimeRemaining(timeLimit);

    }

    /**
     * Sets the name of the current game level.
     *
     * @param levelName The name of the level to be set.
     */
    public void setLevelName(String levelName) {
        this.levelName = levelName;
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
                String levelName = fileName.substring(0, fileName.length() - 4);
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
        showHighScoresButton.setOnAction(event -> handleShowHighScoresButton());

        timeRemainingProperty = new SimpleStringProperty();
        timeRemainingLabel.textProperty().bind(timeRemainingProperty);


        selectedProfileLabel.textProperty().bind(currentProfileProperty.asString());

        selectedLevelLable.textProperty().bind(Bindings.createStringBinding(() ->
                        "Selected level: \n" + (currentLevel != null ? currentLevel.getName() : ""),
                currentLevelProperty()));
    }

    /**
     * Handles the start button event, including loading the level, updating the time limit, and starting the tick timeline.
     */
    private void handleStartButton() {
        List<Profile> loadedProfiles = ProfileFileManager.loadAllProfiles();
        ProfileFileManager.printAllProfiles(loadedProfiles);

        try {
            if (currentLevel == null) {
                throw new IllegalArgumentException("No level selected.");
            }

            if (currentProfile == null) {
                throw new IllegalArgumentException("No profile selected.");
            }

            if (levelLoader == null) {
                levelLoader = new LevelLoader();
            }

            setCurrentLevel(currentLevel);
            levelName = currentLevel.getName();
            levelLoader.updateLevelInformation(levelName);

            GraphicsContext gc = canvas.getGraphicsContext2D();

            // Clear the level before loading a new one
            levelLoader.clearLevel();
            levelLoader.loadLevel(gc, Game.class.getResourceAsStream("levels/" + levelName + ".txt"));




            setTimeLimit(levelLoader.getTimeLimit());

            currentProfile.setScoreForLevel(levelName, timeLimit);

            System.out.println("Selected profile: " + currentProfile.getName());
            System.out.println("Selected level: " + currentLevel.getName());
            Timeline tickTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> tick()));
            tickTimeline.setCycleCount(Animation.INDEFINITE);
            tickTimeline.play();

            drawGame();
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Handles the select button event, including setting the current profile
     */
    private void handleSelectButton() {
        currentProfile = profileChoiceBox.getValue();
        System.out.println("Selected profile: " + currentProfile.getName());
        currentProfileProperty.set(currentProfile);
    }

    /**
     * Handles the delete button event, including removing the selected profile from the list of profiles.
     */
    private void handleDeleteButton() {
        Profile selectedProfile = profileChoiceBox.getValue();
        profiles.remove(selectedProfile);
        System.out.println("Deleted profile: " + selectedProfile.getName());
    }

    /**
     * Handles the create button event, including creating a new profile and adding it to the list of profiles.
     */
    private void handleCreateButton() {
        String newProfileName = createName.getText();
        Profile newProfile = new Profile(newProfileName);
        profiles.add(newProfile);
        System.out.println("Created profile: " + newProfile.getName());

        ProfileFileManager.saveAllProfiles(profiles);
    }

    @FXML
    public void handleShowHighScoresButton() {

        System.out.println("Show high scores button clicked");

        highScore.addScore(levelName, currentProfile.getName(), timeLimit);

        for (Level level : levels) {
            List<ScoreEntry> highScoresList = highScore.getHighScores(level.getName());

            if (!highScoresList.isEmpty()) {
                System.out.println("High scores for " + level.getName() + ":");
                int rank = 1;
                for (ScoreEntry highScoreEntry : highScoresList) {
                    System.out.println(rank + ". " + highScoreEntry.getProfileName() + " - " + highScoreEntry.getScore());
                    rank++;
                }
            } else {
                System.out.println("No high scores for " + level.getName());
            }
        }
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
            int selectedLevelNumber = Integer.parseInt(selectedLevel.getName().substring(5));
            if (currentProfile == null || selectedLevelNumber > currentProfile.getLevelReached() + 1) {
                throw new IllegalArgumentException("You must complete the previous level first.");
            }
            currentLevel = selectedLevel;
            System.out.println("Selected level: " + currentLevel.getName());
            currentLevelProperty.set(currentLevel);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public ObjectProperty<Profile> currentProfileProperty() {

        return currentProfileProperty;
    }

    public Profile getCurrentProfile() {

        return currentProfileProperty.get();
    }

    public void setCurrentProfile(Profile profile) {

        currentProfileProperty.set(profile);
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
     * Returns the current level.
     *
     * @return The current level.
     */
    public ObjectProperty<Level> currentLevelProperty() {
        return currentLevelProperty;
    }

    /**
     * Returns the current level.
     *
     * @return The current level.
     */
    public Level getCurrentLevel() {
        return currentLevelProperty.get();
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


