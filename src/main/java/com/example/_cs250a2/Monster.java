package com.example._cs250a2;
import javafx.scene.canvas.GraphicsContext;

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

   //starting direction the monster is moving with single character (W,A,S,D)
   protected char direction;

   public Monster(int x, int y, char direction) {
      super(x, y);
      this.direction = direction;

   }

   //starting location given as an array of two integers (coordinates)


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
      }
      return safeTile;
   }

   protected void playerKill() {
      if (this.getX() == Player.getX() && this.getY() == Player.getY()) {
         GameOver.playerDeathMonster();
      }
   }

   public abstract void draw(GraphicsContext gc, double x, double y, double size);
}
