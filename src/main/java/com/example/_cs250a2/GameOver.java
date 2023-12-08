package com.example._cs250a2;

import javafx.stage.Stage;

public class GameOver {

    public static void playerDeathDrown() {
        System.out.println("You drowned!");
        System.exit(0);
    }

    public static void playerDeathMonster() {
        System.out.println("You were eaten by a monster!");
        System.exit(0);
    }

    public static void gameEndTime(){
        System.out.println("You ran out of time!");
        System.exit(0);
    }
}
