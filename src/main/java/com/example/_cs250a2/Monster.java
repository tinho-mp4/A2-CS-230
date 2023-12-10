package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The {@code Monster} abstract class represents a framework for all monsters in the game
 * @author idk
 * @version 1.0
 */
public abstract class Monster extends Entity {

   //this keeps track of how many monsters are created so they can each use their position in the ArrayList
   protected static int countMonsters = 0;

   //arraylists with references to each kind of monster present in a level
   protected static ArrayList<PinkBall> PinkBallList = new ArrayList<>();
   protected static ArrayList<Bug> BugList = new ArrayList<>();
   protected static ArrayList<Frog> FrogList = new ArrayList<>();

   //this is an ArrayList for monsters to put their location in with LocationUpdate method (called when the monster moves)
   protected static ArrayList<Integer> monsterLocations = new ArrayList<>();
   //tile still needs to be created
   //Arraylist of tiles the monster cannot move onto
   protected ArrayList<String> allowedTiles = new ArrayList<>(Arrays.asList("path", "button", "trap"));

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

   public int getSpeed() {
      return this.speed;
   }

   public static ArrayList<PinkBall> getPinkBallList() {
      return PinkBallList;
   }
   public static ArrayList<Frog> getFrogList() {
      return FrogList;
   }
   public static ArrayList<Bug> getBugList() {
      return BugList;
   }

   public abstract void move();

   //called by the tick method to move monsters
   public void tickMove(int count) {
      if (count % this.getSpeed() == 0) {
         this.move();
      }
   }

   //these check the parameters when the constructor is called for a monster
   protected void checkLocation(int[] location) {
      if (!(location[0] < LevelLoader.getWidth() && location[1] < LevelLoader.getHeight()) && (location[0] >= 0 && location[1] >= 0)) {
         throw new IllegalArgumentException("the monster has to start within the coordinates of the game space");
      }
   }
   protected void checkDirection(char direction) {
      if (!(direction == 'w' || direction == 'a' || direction == 's' || direction == 'd')) {
         throw new IllegalArgumentException("starting direction must be a character w, a, s or d");
      }
   }

   //method to check if monster move is legal
   protected boolean checkTile(int[] tileLocation, int[] currentTileLocation) {
      boolean safeTile = false;
      boolean withinBounds = true;
      boolean stuckOnTrap = false;
      boolean canMove = false;
      String nextTile = LevelLoader.getTile(tileLocation[0], tileLocation[1]).getName();
      Tile currentTile = LevelLoader.getTile(currentTileLocation[0], currentTileLocation[1]);


      //checks if move is within the game space
      try {
         checkLocation(tileLocation);
      } catch (IllegalArgumentException e) {
         withinBounds = false;
      }

      //gets the name of the tile and compares it to the tiles monster can walk on

      for (String tile : allowedTiles) {
          if (nextTile.equals(tile)) {
              safeTile = true;
              break;
          }
      }
      //now checks that no other monster is on the tile
      for (int i = 0; i < countMonsters; i++) {
          if (monsterLocations.get(i * 2) == tileLocation[0]
                  && monsterLocations.get(i * 2 + 1) == tileLocation[1]) {
              safeTile = false;
              break;
          }
      }
      if (currentTile instanceof Trap) {
         Trap trap = (Trap) currentTile;
         if (trap.isStuck()) {
            stuckOnTrap = true;
         }
      }//checks if the monster is moving onto a button to press it
      if (safeTile && withinBounds && !stuckOnTrap) {
         canMove = true;
      }

      //finally presses / unpresses buttons once the checker knows if a move will happen
      if (nextTile.equals("button") && canMove) {
         Button button = (Button) LevelLoader.getTile(tileLocation[0], tileLocation[1]);
         button.press();
      }
      if (currentTile.getName().equals("button") && canMove) {
         Button button = (Button) currentTile;
         button.unpress();
      }
       return canMove;
   }

   protected void playerKill() {
      Player player = (Player) LevelLoader.getEntityByClass(Player.class);
      if (this.getX() == player.getX() && this.getY() == player.getY()) {
         GameOver.playerDeathMonster();
      }
   }

   //whenever a monster moves it needs to update its location in the arraylist
   protected void locationUpdate(int index, int newPosition) {
      monsterLocations.set(index, newPosition);
   }

   public abstract void draw(GraphicsContext gc, double x, double y, double size);
}
