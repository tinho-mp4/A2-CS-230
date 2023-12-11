package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

/**
 * Represents a trap tile in the game.
 * A trap can be linked to a button and has states for active
 * or inactive (stuck or not stuck).
 * The class extends {@code Tile} and uses an image for its visual representation.
 * @author June
 * @version 1.0
 */
public class Trap extends Tile {
    private static Image TRAP_IMAGE =
            new Image(Objects.requireNonNull(Trap.class.getResourceAsStream("sprites/trapInactive.png")));
    private final int trapNum;
    private Button associatedButton;
    private boolean stuck = false;

    /**
     * Initializes a trap tile with a unique number, position, and initial state.
     * The trap is initially set as pushable.
     *
     * @param trapNum Unique identifier for the trap.
     * @param x       X-coordinate of the trap.
     * @param y       Y-coordinate of the trap.
     */
    public Trap(int trapNum, int x, int y) {
        super("trap", x, y, false);
        this.trapNum = trapNum;
        setPushableBlock(true);
    }

    /**
     * Links this trap to a specified button.
     *
     * @param button The button to be linked with the trap.
     */
    public void linkToButton(Button button) {
        this.associatedButton = button;
    }

    /**
     * Sets the trap to inactive state.
     */
    public void inactive() {
        System.out.println("Trap is inactive!");
        TRAP_IMAGE = new Image(Objects.requireNonNull(
                Trap.class.getResourceAsStream("sprites/trapInactive.png")));
        stuck = false;
    }

    /**
     * Sets the trap to active state.
     */
    public void active() {
        System.out.println("Trap is active!");
        TRAP_IMAGE = new Image(Objects.requireNonNull(
                Trap.class.getResourceAsStream("sprites/trapActive.png")));
        stuck = true;
    }

    /**
     * Checks if the trap is in the stuck (active) state.
     *
     * @return true if the trap is stuck, false otherwise.
     */
    public boolean isStuck() {
        return stuck;
    }

    /**
     * Draws the trap tile on the canvas.
     *
     * @param gc   GraphicsContext for drawing.
     * @param x    X-coordinate for drawing.
     * @param y    Y-coordinate for drawing.
     * @param size Size of the tile.
     */
    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(TRAP_IMAGE, x * size, y * size);
        System.out.println("Trap stuck? " + stuck);
    }
}
