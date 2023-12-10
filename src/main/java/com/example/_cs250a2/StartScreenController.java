package com.example._cs250a2;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.InputStream;

/**
 * The {@code StartScreenController} class handles the starting screen of the game
 * @author idk
 * @version 1.0
 */

public class StartScreenController {

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

    private final ObservableList<Profile> profiles = FXCollections.observableArrayList();

    private Profile currentProfile;

    private Level currentLevel;

    @FXML
    private ChoiceBox<Level> levelChoiceBox;

    private final ObservableList<Level> levels = FXCollections.observableArrayList();

    @FXML
    private Button selectLevelButton;

    @FXML
    private Label selectedProfileLabel;

    @FXML
    private Label selectedLevelLabel;

    private final ObjectProperty<Level> currentLevelProperty = new SimpleObjectProperty<>();

    @FXML
    public void initialize() {
        double canvasWidth = 1000;
        double canvasHeight = 1000;
        levelCanvas.setWidth(canvasWidth);
        levelCanvas.setHeight(canvasHeight);
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

            // Pass the selected profile to the Game class
            System.out.println("jsut smile and wave");
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


