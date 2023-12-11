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
    private static final Image BUTTON_IMAGE
    = new Image(Objects.requireNonNull(Button.class.getResourceAsStream("sprites/button.png")));

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
    private boolean pressed;


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
        associatedTrap.inactive();
    }

    /**
     * Unpressed the button.
     */
    public void unpress() {
        pressed = false;
        associatedTrap.active();

    }

/*    public void checkIfPlayerOnButton() {

         if () {
            System.out.println("Player is on the button");
            associatedTrap.inactive();
         }

        else {
            System.out.println("Player is not on the button");
            associatedTrap.active();
        }
    }*/

    /**
     * Checks if an entity is on the button.
     * @param entityX The X position of the entity
     * @param entityY The Y position of the entity
     */
    public void checkIfEntityOnButton(final int entityX, final int entityY) {
        if (getX() == entityX && getY() == entityY) {
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


