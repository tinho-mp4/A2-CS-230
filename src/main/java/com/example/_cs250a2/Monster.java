package com.example._cs250a2;
import java.util.ArrayList;

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

   //inheritable move method for monsters
   public void move() {

   }

   //method to check if monster move is legal
   //not done just returns true!!!
   protected boolean checkTile(int[] tile) {
      return true;
   }

   protected void playerKill() {
      if (monsterX == Player.getPlayerX() && monsterY == Player.getPlayerY()) {
         GameOver.playerDeathMonster();
      }
   }
}
