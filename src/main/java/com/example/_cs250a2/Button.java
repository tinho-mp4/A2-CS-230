package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

/**
 * The {@code Button} class represents a button in the game.
 * @author Pele
 * @version 1.0
 */
public class Button extends Tile {
    /**
     * The image of the button tile.
     */
    private static Image BUTTON_IMAGE
    = new Image(Objects.requireNonNull(Button.class.getResourceAsStream("sprites/buttonInactive.png")));

    /**
     * The number of the button.
     */
    private final int buttonNum;
    /**
     * The trap associated with the button.
     */
    private Trap associatedTrap;
    /**
     *  Whether the button is pressed or not.
     */
    private boolean pressed = false;


    /**
     * Creates a new button.
     * @param trapNum The number of the trap associated with the button
     * @param x The X position of the button
     * @param y The Y position of the button
     */
    public Button(final int trapNum, final  int x, final int y) {
        super("button", x, y, false);
        this.buttonNum = trapNum;
        setPushableBlock(true);
    }

    /**
     * Links the button to a trap.
     * @param trap The trap to be linked to the button
     */
    public void linkToTrap(final Trap trap) {
        this.associatedTrap = trap;
    }

    /**
     * Presses the button.
     */
    public void press() {
        pressed = true;
        associatedTrap.active();
        BUTTON_IMAGE = new Image(Objects.requireNonNull(
                Button.class.getResourceAsStream("sprites/buttonActive.png")));
    }

    /**
     * Unpressed the button.
     */
    public void unpress() {
        pressed = false;
        associatedTrap.inactive();
        BUTTON_IMAGE = new Image(Objects.requireNonNull(
                Button.class.getResourceAsStream("sprites/buttonInactive.png")));
    }

    /**
     * Checks if an entity is on the button.
     */
    public void checkIfEntityOnButton() {
        boolean entityOnButton = false;
        for (Entity entity : LevelLoader.getEntityList()) {
            if (entity.getX() == x && entity.getY() == y) {
                entityOnButton = true;
                break;
            }
        }
        if (entityOnButton) {
            press();
        } else {
            unpress();
        }
    }

    /**
     * Draws the button.
     * @param gc The graphics context
     * @param x The X position of the button
     * @param y The Y position of the button
     * @param size The size of the button
     */
    @Override
    public void draw(final GraphicsContext gc,
                     final double x,
                     final double y,
                     final double size) {
        gc.drawImage(BUTTON_IMAGE, x * size, y * size);
    }
}


