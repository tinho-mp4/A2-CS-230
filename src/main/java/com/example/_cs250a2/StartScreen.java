package com.example._cs250a2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class represents the start screen of the game.
 * It extends {@code Application} and sets up the primary stage for the game's start screen.
 */
public class StartScreen extends Application {

    /**
     * Starts the primary stage with the start screen of the game.
     *
     * @param primaryStage The primary stage for this application.
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Chip Challenge");

        try {
            FXMLLoader loader = new FXMLLoader();
            InputStream fxmlStream = getClass().getResourceAsStream("StartScreen.fxml");
            Parent root = loader.load(fxmlStream);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions appropriately.
        }
    }

    /**
     * Main method to launch the application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
