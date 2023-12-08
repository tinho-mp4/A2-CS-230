package com.example._cs250a2;

import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class GameController {

    private static final int CANVAS_WIDTH = 700;
    private static final int CANVAS_HEIGHT = 400;

    @FXML
    public Canvas canvas;

    // Loaded images

    // Create Player
    Player player = new Player(1,1);

    // Timeline which will cause tick method to be called periodically.
    public Timeline tickTimeline;

    private Color bgColor = Color.LIGHTBLUE;
    private static Game instance;
    public LevelLoader levelLoader;

    public String levelName = "level2";

    private int timeLimit = 100;

    private Profile currentProfile;

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

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
        player.draw(gc, player.getX(),player.getY() , 32);
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

        //sets the score for the current profile
        if(currentProfile != null) {
            currentProfile.setScoreForLevel(levelName, timeLimit);
        }

    }

    public void startGame(Profile profile) {
        this.currentProfile = profile;
        Platform.runLater(() -> {
            try {
                instance.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void initialize() {
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
}
