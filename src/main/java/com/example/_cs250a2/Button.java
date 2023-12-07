package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Button} class represents a button object that can be pressed and linked to traps.
 * It allows you to set its pressed state and link it to traps that can respond to its state.
 *
 * @author Juned Miah
 */
public class Button extends Tile{
    private static final Image BUTTON_IMAGE = new Image(Button.class.getResourceAsStream("sprites/button.png"));


    /**
     * The number paired with this button.
     */
    public int pairedNumber;

    /**
     * A flag indicating whether the button is currently pressed.
     */
    private static boolean isPressed;

    /**
     * A list of traps linked to this button.
     */
    private static List<Trap> linkedTraps;

    /**
     * Initializes a new instance of the {@code Button} class.
     * The button is initially not pressed, and no traps are linked to it.
     */
    public Button(int x, int y, int _pairedNumber) {
        super("button", x, y, false);
        isPressed = false;
        linkedTraps = new ArrayList<>();
        pairedNumber = _pairedNumber;
    }

    public static void event() {
    }

    /**
     * Links a trap to this button. When the button's state changes, linked traps will respond accordingly.
     *
     * @param trap The trap to be linked to this button.
     */
    public void linkTrap(Trap trap) {
        linkedTraps.add(trap);
    }

    /**
     * Sets the pressed state of the button and triggers linked traps accordingly.
     *
     * @param isPressed {@code true} if the button is pressed, {@code false} otherwise.
     */
    public void setPressed(boolean isPressed) {
        Button.isPressed = isPressed;
        for (Trap trap : linkedTraps) {
            trap.setActive(!isPressed);
        }
    }

    /**
     * Checks whether the button is currently pressed.
     *
     * @return {@code true} if the button is pressed, {@code false} otherwise.
     */
    public boolean isPressed() {
        return isPressed;
    }


    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(BUTTON_IMAGE, x*size, y*size);
    }
}


