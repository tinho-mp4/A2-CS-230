package com.example._cs250a2;


/**
 * The {@code GameOver} class informs the player of how the game has ended.
 * @author Finn Pearson
 * @version 1.0
 */
public class GameOver {

    /**
     * informs the player that they have won the game.
     */
    public static void playerDeathDrown() {
        System.out.println("You drowned!");
    }

    /**
     * informs the player that they have lost the game.
     */
    public static void playerDeathMonster() {
        System.out.println("You were eaten by a monster!");
    }

    /**
     * informs the player that they have lost the game due to time.
     */
    public static void gameEndTime() {
        System.out.println("You ran out of time!");
    }
}
