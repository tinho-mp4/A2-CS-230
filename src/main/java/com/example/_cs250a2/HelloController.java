package com.example._cs250a2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
/**
 * The {@code HelloController} class welcomes the user.
 * @author idk
 * @version 1.0
 */
public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}