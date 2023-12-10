package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

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
    = new Image(Button.class.getResourceAsStream("sprites/button.png"));

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

    public void linkToTrap(Trap trap) {
        this.associatedTrap = trap;
    }

    public void press() {
        pressed = true;
        associatedTrap.inactive();
    }

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

    public void checkIfEntityOnButton(int entityX, int entityY) {
        if (getX() == entityX && getY() == entityY) {
            press();
        } else {
            unpress();
        }
    }


    public boolean isPressed() {
        return pressed;
    }


    public int getButtonNum() {
        return buttonNum;
    }


    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(BUTTON_IMAGE, x * size, y * size);
    }
}


