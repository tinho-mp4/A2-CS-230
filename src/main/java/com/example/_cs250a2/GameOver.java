package com.example._cs250a2;

public class GameOver {

    public static void playerDeathDrown() {
        System.out.println("You drowned!");
        System.exit(0);
    }

    public static void playerDeathMonster() {
        System.out.println("You were eaten by a monster!");
        System.exit(0);
    }
}
