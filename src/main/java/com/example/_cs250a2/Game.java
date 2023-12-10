package com.example._cs250a2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;


/**
 * The {@code Game} class represents the main game window.
 * It is responsible for initializing the
 * JavaFX application and displaying the game window.
 * It also handles key presses and calls the
 * appropriate methods in the {@code Player} class.
 * @author Liam O'Reilly
 * @version 1.1
 */
public class Game extends Application {
    // The dimensions of the window
    /**
     * The width of the game window.
     */
    private static final int WINDOW_WIDTH = 800;
    /**
     * The height of the game window.
     */
    private static final int WINDOW_HEIGHT = 500;



    /**
     * Initializes and starts the JavaFX application.
     * @param primaryStage The primary stage for the application.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    public void start(final Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Game.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        // Set up the controller
        GameController controller = loader.getController();

        // Register an event handler for key presses.
        // This causes the processKeyEvent method
        // to be called each time a key is pressed.
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event ->
                controller.processKeyEvent(event));

        // Register a tick method to be called periodically.
        // Make a new timeline with one keyframe that
        // triggers the tick method every half a second.

        // Display the scene on the stage
        primaryStage.setScene(scene);
        primaryStage.show();

        // load the level
        GraphicsContext gc = controller.getCanvas().getGraphicsContext2D();
    }

    /**
     * The main method for the application.
     * @param args The command line arguments.
     */
    public static void main(final String[] args) {
        launch(args);
    }

}
