package com.example._cs250a2;

/**
 * The {@code Trap} class represents a trap object that can be activated or deactivated.
 * It allows you to set its active state and check whether it is currently active.
 *
 * @author Juned Miah
 */
public class Trap {
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
    public Trap() {
        isActive = true;
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
}

