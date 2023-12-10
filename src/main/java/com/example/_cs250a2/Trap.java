package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * The {@code Trap} class represents a trap in the game.
 * @author idk
 * @version 1.0
 */
public class Trap extends Tile {
    private static final Image TRAP_IMAGE = new Image(Trap.class.getResourceAsStream("sprites/trapInactive.png"));
    private final int trapNum;
    private Button associatedButton;
    private boolean stuck = true;
    public boolean isActive() {
        return stuck;
    }




    public Trap(int trapNum, int x, int y) {
        super("trap",x, y, false);
        this.trapNum = trapNum;
        setPushableBlock(true);
    }

    public void linkToButton(Button button) {
        this.associatedButton = button;
    }

    public boolean isAssociatedWithButton() {
        return associatedButton != null;
    }


    public Button getAssociatedButton() {
        return associatedButton;
    }

    public void setAssociatedButton(Button button) {
        this.associatedButton = button;
    }

    public int getTrapNum() {
        return trapNum;
    }



    public void inactive() {
        System.out.println("Trap is inactive!");
        stuck = false;
    }

    public void active() {
        System.out.println("Trap is active!");
        stuck = true;
    }

    public boolean isStuck() {
        return stuck;
    }



    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(TRAP_IMAGE, x*size, y*size);
    }
}
