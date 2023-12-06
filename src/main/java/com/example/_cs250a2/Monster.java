package com.example._cs250a2;
import java.util.ArrayList;
import java.util.Arrays;

//TODO write tile checker
//TODO test all methods when level working, check playerkill works later
/**
 * javadoc to go here
 */
public abstract class Monster extends Entity {

    //tile still needs to be created
    //Arraylist of tiles the monster cannot move onto
   protected ArrayList<String> allowedTiles = new ArrayList<>(Arrays.asList("Path","Button","Trap"));

   //speed decided based on how many ticks between moves
   protected int speed;

   //x and y coordinates of monster
   protected int monsterX;
   protected int monsterY;

   //starting direction the monster is moving with single character (W,A,S,D)
   protected char direction;

   //starting location given as an array of two integers (coordinates)
   protected int[] location = {monsterX, monsterY};

   protected void checkLocation(int[] location) {
      if (location[0] > LevelLoader.getWidth() || location[1] > LevelLoader.getHeight()) {
         throw new IllegalArgumentException("the monster has to start within the coordinates of the game space");
      }
   }

   protected void checkDirection(char direction) {
      if (!(direction == 'w' || direction == 'a' || direction == 's' || direction == 'd')) {
         throw new IllegalArgumentException("starting direction must be a character w, a, s or d");
      }
   }

   //method to check if monster move is legal
   //not done just returns true!!!
   protected boolean checkTile(int[] tile) {
      return true;
   }

   protected void playerKill() {
      if (monsterX == Player.getX() && monsterY == Player.getY()) {
         GameOver.playerDeathMonster();
      }
   }
}
