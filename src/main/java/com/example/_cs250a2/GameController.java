package com.example._cs250a2;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;


public class GameController {

    private static final int CANVAS_WIDTH = 700;
    private static final int CANVAS_HEIGHT = 400;

    @FXML
    public Canvas canvas;

    // Loaded images

    // Create Player
    Player player = new Player(1, 1);

    // Timeline which will cause tick method to be called periodically.
    public Timeline tickTimeline;

    private Color bgColor = Color.LIGHTBLUE;
    private static Game instance;
    public LevelLoader levelLoader;

    public String levelName = "level3";

    private int timeLimit = 100;

    private Profile currentProfile;

    @FXML
    public Label selectedLevelLable;
    public Canvas levelCanvas;

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

    private final ObjectProperty<Level> currentLevelProperty = new SimpleObjectProperty<>();

    public void handleButtonClick(ActionEvent actionEvent) {
        System.out.println("This is a button");
    }

    public void processKeyEvent(KeyEvent event) {
        player.move(event);

        // Redraw game as the player may have moved.
        drawGame();

        // Consume the event. This means we mark it as dealt with. This stops other GUI nodes (buttons etc.) responding to it.
        event.consume();
    }

    /**
     * Draw the game on the canvas.
     */
    public void drawGame() {
        // Get the Graphic Context of the canvas. This is what we draw on.
        GraphicsContext gc = canvas.getGraphicsContext2D();
        // Clear canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Set the background to gray.
        gc.setFill(bgColor);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());


        this.levelLoader.drawLevel(gc);
        this.levelLoader.drawEntities(gc);
        this.levelLoader.drawItems(gc);


        // Draw player at current location
        player.draw(gc, player.getX(), player.getY(), 32);
        //Draw key at current location
        //key.draw(gc, key.getX(), key.getY(), 32);

    }

    /**
     * This method is called periodically by the tick timeline
     * and would for, example move, perform logic in the game,
     * this might cause the bad guys to move (by e.g., looping
     * over them all and calling their own tick method).
     */
    public void tick() {
        //update the timer every tick if its 0 end the game
        updateTimer();

        if (timeLimit <= 0) {
            GameOver.gameEndTime();
            tickTimeline.stop();
        }
        // Here we move the player right one cell and teleport
        // them back to the left side when they reach the right side.
        int playerX = player.getX();

        player.setX(playerX + 1);
        if (playerX > 11) {
            player.setX(0);
        }
        // We then redraw the whole canvas.
        drawGame();
    }

    /**
     * Create the GUI.
     *
     * @return The panel that contains the created GUI.
     */
    private Pane buildGUI() {
        // Create top-level panel that will hold all GUI nodes.
        BorderPane root = new BorderPane();

        // Create the canvas that we will draw on.
        // We store this as a gloabl variable so other methods can access it.
        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        root.setCenter(canvas);
        BorderPane.setAlignment(canvas, Pos.CENTER);

        javafx.scene.control.Button button = new Button("Click Me");
        button.setOnAction(event -> System.out.println("This is a button"));

        // Set the button to the right of the BorderPane
        root.setRight(button);


        // Finally, return the border pane we built up.
        return root;
    }

    //reduce the time by one or until it reaches 0`
    public void updateTimer() {
        timeLimit--;
        if (timeLimit <= 0) {
            GameOver.gameEndTime();
            tickTimeline.stop();
        }
        System.out.println("Time left: " + timeLimit);

        //sets the score for the current profile
        if (currentProfile != null) {
            currentProfile.setScoreForLevel(levelName, timeLimit);
        }

    }

    @FXML
    private void handleButtonClick() {
        System.out.println("This is a button");
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    @FXML
    public void initialize() {
        profiles.addAll(new Profile("Profile1"), new Profile("Profile2"), new Profile("Profile3"));
        profileChoiceBox.setItems(profiles);

        levels.addAll(new Level("level1"), new Level("level2"), new Level("level3"));
        levelChoiceBox.setItems(levels);

        selectNameButton.disableProperty().bind(profileChoiceBox.valueProperty().isNull());
        levelChoiceBox.disableProperty().bind(profileChoiceBox.valueProperty().isNull());
        selectLevelButton.disableProperty().bind(levelChoiceBox.valueProperty().isNull());
        selectNameButton.setOnAction(event -> handleSelectButton());
        deleteButton.setOnAction(event -> handleDeleteButton());
        createButton.setOnAction(event -> handleCreateButton());
        selectLevelButton.setOnAction(event -> handleSelectLevelButton());
        startButton.setOnAction(event -> handleStartButton());


        selectedProfileLabel.textProperty().bind(Bindings.createStringBinding(() ->
                        "Selected profile: \n" + (currentProfile != null ? currentProfile.getName() : ""),
                currentProfileProperty()));

        selectedLevelLable.textProperty().bind(Bindings.createStringBinding(() ->
                        "Selected level: \n" + (currentLevel != null ? currentLevel.getName() : ""),
                currentLevelProperty()));
    }

    private void handleStartButton() {
        try {
            if (currentLevel == null) {
                throw new IllegalArgumentException("No level selected.");
            }

            if (currentProfile == null) {
                throw new IllegalArgumentException("No profile selected.");
            }
            setCurrentLevel(currentLevel);
            levelName = currentLevel.getName();
            GraphicsContext gc = canvas.getGraphicsContext2D();

            levelLoader = new LevelLoader();
            levelLoader.loadLevel(gc, Game.class.getResourceAsStream("levels/" + levelName + ".txt"));
            System.out.println("Selected profile: " + currentProfile.getName());
            System.out.println("Selected level: " + currentLevel.getName());
            Timeline timerTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateTimer()));
            timerTimeline.setCycleCount(Animation.INDEFINITE);
            timerTimeline.play();
            levelLoader = new LevelLoader();
            levelLoader.loadLevel(gc, Game.class.getResourceAsStream("levels/" + levelName + ".txt"));
            player.draw(gc, player.getX(),player.getY() , 32);

            drawGame();
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleSelectButton() {
        currentProfile = profileChoiceBox.getValue();
        System.out.println("Selected profile: " + currentProfile.getName());
        currentProfileProperty.set(null);
        currentProfileProperty.set(currentProfile);
    }

    private void handleDeleteButton() {
        Profile selectedProfile = profileChoiceBox.getValue();
        profiles.remove(selectedProfile);
        System.out.println("Deleted profile: " + selectedProfile.getName());
    }

    private void handleCreateButton() {
        String newProfileName = createName.getText();
        Profile newProfile = new Profile(newProfileName);
        profiles.add(newProfile);
        System.out.println("Created profile: " + newProfile.getName());
    }

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
            currentLevelProperty.set(null);
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

    public ObjectProperty<Level> currentLevelProperty() {
        return currentLevelProperty;
    }

    public Level getCurrentLevel() {
        return currentLevelProperty.get();
    }

    public void setCurrentLevel(Level level) {
        currentLevelProperty.set(level);
    }
}


