package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import javax.xml.transform.Source;

/**
 * The {@code Button} class represents a button in the game
 * @author idk
 * @version 1.0
 */
public class Button extends Tile {
    private static final Image BUTTON_IMAGE = new Image(Button.class.getResourceAsStream("sprites/button.png"));

    private final int buttonNum;
    private Trap associatedTrap;
    private boolean pressed;


    public Button(int trapNum, int x, int y) {
        super("button", x, y, false);
        this.buttonNum = trapNum;
    }

    public void linkToTrap(Trap trap) {
        this.associatedTrap = trap;
    }

    public void press() {;
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


