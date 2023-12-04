package com.example._cs250a2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class StartScreen extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("lol");

        try {
            FXMLLoader loader = new FXMLLoader();
            InputStream fxmlStream = getClass().getResourceAsStream("/com/example/_cs250a2/StartScreen.fxml");
            Parent root = loader.load(fxmlStream);

            // Get the controller from the FXMLLoader
            StartScreenController controller = loader.getController();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}

