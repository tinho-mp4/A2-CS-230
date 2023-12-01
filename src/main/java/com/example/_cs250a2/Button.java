package com.example._cs250a2;

import java.util.ArrayList;
import java.util.List;

public class Button {
    private boolean isPressed;
    private final List<Trap> linkedTraps;

    public Button() {
        isPressed = false;
        linkedTraps = new ArrayList<>();
    }

    public static void event() {
    }

    public void linkTrap(Trap trap) {
        linkedTraps.add(trap);
    }

    public void setPressed(boolean isPressed) {
        this.isPressed = isPressed;
        for (Trap trap : linkedTraps) {
            trap.setActive(!isPressed);
        }
    }

    public boolean isPressed() {
        return isPressed;
    }
}

