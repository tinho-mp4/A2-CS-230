package com.example._cs250a2;

public class Trap {
    private boolean isActive;
    public Trap() {
        isActive = true;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean canMoveOff() {
        return !isActive;
    }
}
