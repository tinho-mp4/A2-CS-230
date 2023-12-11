package com.example._cs250a2;

/**
 * Provides methods to display game over messages. This class is responsible for communicating to the player
 * how the game has ended, whether it be from drowning, being eaten by a monster, or running out of time.
 * It primarily outputs messages to the console indicating the cause of the game's end.
 *
 * @author Finn Pearson
 * @version 1.0
 */
public class GameOver {

    /**
     * Informs the player that they have drowned. This method is called when the player's character dies by drowning.
     */
    public static void playerDeathDrown() {
        System.out.println("You drowned!");
    }

    /**
     * Informs the player that they have been eaten by a monster. This method is invoked when the player's character
     * is killed by a monster in the game.
     */
    public static void playerDeathMonster() {
        System.out.println("You were eaten by a monster!");
    }

    /**
     * Informs the player that they have lost the game due to running out of time. This method is used when the game
     * ends because the player did not complete the level within the allocated time.
     */
    public static void gameEndTime() {
        System.out.println("You ran out of time!");
    }
}
