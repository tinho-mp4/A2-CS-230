package com.example._cs250a2;

import java.util.ArrayList;
import java.util.List;

public class Button {
    private static boolean isPressed;
    private static List<Trap> linkedTraps;

    public static void Button() {
        isPressed = false;
        linkedTraps = new ArrayList<>();
    }

    public static void event() {
    }

    public void linkTrap(Trap trap) {
        linkedTraps.add(trap);
    }

    public void setPressed(boolean isPressed) {
        Button.isPressed = isPressed;
        for (Trap trap : linkedTraps) {
            trap.setActive(!isPressed);
        }
    }

    public boolean isPressed() {
        return isPressed;
    }
}

