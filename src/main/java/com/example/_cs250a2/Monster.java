package com.example._cs250a2;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Arrays;

//TODO test all methods when level working, check playerkill works later
/**
 * javadoc to go here
 */
public abstract class Monster extends Entity {

   //this keeps track of how many monsters are created so they can each use their position in the ArrayList
   protected static int countMonsters = 0;

   //this is an ArrayList for monsters to put their location in with LocationUpdate method (called when the monster moves)
   protected static ArrayList<Integer> monsterLocations = new ArrayList<>();
    //tile still needs to be created
    //Arraylist of tiles the monster cannot move onto
   protected ArrayList<String> allowedTiles = new ArrayList<>(Arrays.asList("Path","Button","Trap"));

   //speed decided based on how many ticks between moves
   protected int speed;
   protected int arrayLocationX;
   protected int arrayLocationY;

   //starting direction the monster is moving with single character (W,A,S,D)
   protected char direction;



   public Monster(int x, int y, char direction) {
      super(x, y);
      this.direction = direction;

   }

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
   //still needs to check for blocks and other monsters
   protected boolean checkTile(int[] tileLocation) {
      boolean safeTile = false;
      String nextTile = LevelLoader.getTile(tileLocation[0], tileLocation[1]).getName();
      for (String tile : allowedTiles){
         if (nextTile.equals(tile)) {
            safeTile = true;
         }
      } //now checks that no other monster is on the tile
      for (int i = 0; i < countMonsters; i++) {
         if (i == arrayLocationX || i == arrayLocationY) {
            //do nothing
         //i%2==0 will give the x position from monsterlocations i%2==1 would give y
         } else if (i % 2 == 0 && tileLocation[0] == monsterLocations.get(i) && tileLocation[1] == monsterLocations.get(i++)){
            safeTile = false;
         } else; // does nothing since the y coordinates have been checked already, goes to next X to start check again
      }
      return safeTile;
   }

   protected void playerKill() {
      if (this.getX() == Player.getX() && this.getY() == Player.getY()) {
         GameOver.playerDeathMonster();
      }
   }

   //whenever a monster moves it needs to update its location in the arraylist
   protected void locationUpdate() {

   }
   public abstract void draw(GraphicsContext gc, double x, double y, double size);
}
