package com.example._cs250a2;
import java.util.ArrayList;

//TODO write tile checker, check playerkill works later, write checklocation method
//TODO test all methods when level working
/**
 * javadoc to go here
 */
public class Monster {

    //tile still needs to be created
    //Arraylist of tiles the monster cannot move onto
   protected ArrayList<Tile> blockingTiles = new ArrayList<Tile>();

   //speed decided based on how many ticks between moves
   protected int speed;

   //x and y coordinates of monster
   protected int monsterX;
   protected int monsterY;

   //starting direction the monster is moving with single character (W,A,S,D)
   protected char direction;

   //starting location given as an array of two integers (coordinates)
   protected int[] location = {monsterX, monsterY};

   protected void checkLocation() {
      throw new IllegalArgumentException("the monster has to start within the coordinates of the game space");
   }

   protected void checkDirection() {
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
      if (monsterX == Globals.getPlayerX() && monsterY == Globals.getPlayerY()) {
         GameOver.playerDeathMonster();
      }
   }
}
