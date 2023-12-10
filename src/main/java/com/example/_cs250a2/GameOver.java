package com.example._cs250a2;

import javafx.stage.Stage;

public class GameOver {

    public static void playerDeathDrown() {
        System.out.println("You drowned!");
    }

    public static void playerDeathMonster() {
        System.out.println("You were eaten by a monster!");
    }

    public static void gameEndTime(){
        System.out.println("You ran out of time!");
        System.exit(0);
    }
}
