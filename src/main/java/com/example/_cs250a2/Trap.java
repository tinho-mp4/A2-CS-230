package com.example._cs250a2;

public class Trap {
    private static boolean isActive;
    public static void Trap() {
        isActive = true;
    }

    public static void event() {
    }

    public void setActive(boolean isActive) {
        Trap.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean canMoveOff() {
        return !isActive;
    }
}
