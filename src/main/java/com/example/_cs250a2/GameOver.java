package com.example._cs250a2;

import javafx.stage.Stage;

/**
 * The {@code GameOver} class informs the player of how the game has ended.
 * @author idk
 * @version 1.0
 */
public class GameOver {

    public static void playerDeathDrown() {
        System.out.println("You drowned!");
    }

    public static void playerDeathMonster() {
        System.out.println("You were eaten by a monster!");
    }

    public static void gameEndTime(){
        System.out.println("You ran out of time!");
    }
}
