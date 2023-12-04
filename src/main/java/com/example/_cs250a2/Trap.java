package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * The {@code Trap} class represents a trap object that can be activated or deactivated.
 * It allows you to set its active state and check whether it is currently active.
 *
 * @author Juned Miah
 */
public class Trap extends Tile{
    private static final Image TRAP_IMAGE = new Image(Trap.class.getResourceAsStream("trap.png"));

    /**
     * The number paired with this trap.
     */
    public int pairedNumber;

    /**
     * A flag indicating whether the trap is currently active.
     */
    private static boolean isActive;

    /**
     * Initializes a new instance of the {@code Trap} class.
     * The trap is initially active.
     */
    public Trap(int x, int y) {
        super("trap",x,y);
        isActive = true;
    }

    public static void event() {
    }

    /**
     * Sets the active state of the trap.
     *
     * @param isActive {@code true} if the trap is active, {@code false} if it is deactivated.
     */
    public void setActive(boolean isActive) {
        Trap.isActive = isActive;
    }

    /**
     * Checks whether the trap is currently active.
     *
     * @return {@code true} if the trap is active, {@code false} if it is deactivated.
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Checks whether something can move off the trap. It returns {@code true} if the trap is not active.
     *
     * @return {@code true} if something can move off the trap, {@code false} if it is active and blocking.
     */
    public boolean canMoveOff() {
        return !isActive;
    }

    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(TRAP_IMAGE, x*size, y*size);
    }
}

