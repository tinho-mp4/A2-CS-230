package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents an abstract framework for monsters in the game. This class defines the common properties
 * and behaviors of all monsters, such as movement and interactions with game elements.
 *
 * @version 1.0
 * @author Finn P
 */
public abstract class Monster extends Entity {

   /**
    * The maximum number of turns a monster can take. Useful for movement logic to prevent excessive turning.
    */
   protected static final int MAXTURNS = 4;

   /**
    * Keeps track of the total number of monsters created. Used for managing their positions and interactions.
    */
   protected static int countMonsters = 0;

   /**
    * A list of all monsters present in the current level.
    */
   protected static ArrayList<Monster> monsterList = new ArrayList<>();

   /**
    * A list of coordinates representing the locations of all monsters. Updated with each movement.
    */
   protected static ArrayList<Integer> monsterLocations = new ArrayList<>();

   /**
    * A list of tile names that the monster is allowed to move onto.
    */
   protected ArrayList<String> allowedTiles = new ArrayList<>(Arrays.asList("path", "button", "trap"));

   /**
    * The speed of the monster, determined by the number of ticks between movements.
    */
   protected int speed;

   /**
    * The X-coordinate of the monster's position in the level grid.
    */
   protected int arrayLocationX;

   /**
    * The Y-coordinate of the monster's position in the level grid.
    */
   protected int arrayLocationY;

   /**
    * Counter for the number of moves made by the monster to prevent infinite loops in movement logic.
    */
   protected int moveCount = 0;

   /**
    * The initial direction of the monster, represented by a single character (W, A, S, D).
    */
   protected char direction;

   /**
    * Constructs a Monster with specified coordinates and direction.
    *
    * @param x         The initial X-coordinate of the monster.
    * @param y         The initial Y-coordinate of the monster.
    * @param direction The initial direction of the monster.
    */
   public Monster(int x, int y, char direction) {
      super(x, y);
      this.direction = direction;
      monsterList.add(this);
   }

   /**
    * Retrieves the speed of the monster. Speed is defined by the number of game ticks between each movement.
    *
    * @return The speed of the monster.
    */
   public int getSpeed() {
      return this.speed;
   }

   /**
    * Clears the list of all monsters. This method resets the monsterList to an empty state.
    */
   public static void clearMonsterList() {
      monsterList = new ArrayList<>();
   }

   /**
    * An abstract method that defines the movement behavior of a monster.
    * This method should be implemented by subclasses to specify how a monster moves.
    */
   public abstract void move();

   /**
    * Updates the monster's position based on the game tick count.
    * The monster moves if the current count is a multiple of its speed.
    *
    * @param count The current game tick count.
    */
   public void tickMove(int count) {
      if (count % this.getSpeed() == 0) {
         this.move();
      }
   }

   /**
    * Validates the provided location to ensure it is within the game space boundaries.
    *
    * @param location The location to check, represented as an array [x, y].
    * @throws IllegalArgumentException if the location is outside the game space.
    */
   protected void checkLocation(int[] location) {
      if (!(location[0] < LevelLoader.getWidth()
              && location[1] < LevelLoader.getHeight())
              && (location[0] >= 0 && location[1] >= 0)) {
         throw new IllegalArgumentException("the monster has to start within the coordinates of the game space");
      }
   }

   /**
    * Validates the starting direction of the monster.
    *
    * @param direction The initial direction of the monster (W, A, S, D).
    * @throws IllegalArgumentException if the direction is not one of the allowed values.
    */
   protected void checkDirection(char direction) {
      if (!(direction == 'w' || direction == 'a' || direction == 's' || direction == 'd')) {
         throw new IllegalArgumentException("starting direction must be a character w, a, s or d");
      }
   }

   /**
    * Checks if a monster's move to a new tile location is permissible.
    * The method checks tile bounds, other monsters' positions, and specific tile restrictions.
    *
    * @param tileLocation        The intended new tile location of the monster.
    * @param currentTileLocation The current tile location of the monster.
    * @return true if the move is permissible, false otherwise.
    */
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
      //now checks there isn't a block on the tile
      for (Entity entity : LevelLoader.getEntityList()) {
         if (entity instanceof Block && entity.getX() == tileLocation[0] && entity.getY() == tileLocation[1]) {
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

   /**
    * Checks for a collision with the player and triggers game over if a collision occurs.
    *
    * @param gameController The controller managing game logic and state.
    */
   protected void playerKill(GameController gameController) {
      Player player = (Player) LevelLoader.getEntityByClass(Player.class);
      if (this.getX() == player.getX() && this.getY() == player.getY()) {
         GameOver.playerDeathMonster();
         gameController.clearLevel();
      }
   }

   /**
    * Updates the monster's location in the monsterLocations list after it moves.
    *
    * @param index        The index of the monster in the monsterLocations list.
    * @param newPosition  The new position of the monster.
    */
   protected void locationUpdate(int index, int newPosition) {
      monsterLocations.set(index, newPosition);
   }

   /**
    * Resets all monsters by clearing the monster list and their locations.
    * This method is useful for resetting the game state.
    */
   public static void clear() {
      countMonsters = 0;
      monsterList = new ArrayList<>();
      monsterLocations = new ArrayList<>();

   }

   /**
    * Abstract method to draw the monster on the canvas.
    *
    * @param gc   The GraphicsContext on which to draw the monster.
    * @param x    The x-coordinate on the canvas where the monster should be drawn.
    * @param y    The y-coordinate on the canvas where the monster should be drawn.
    * @param size The size of the monster.
    */
   public abstract void draw(GraphicsContext gc, double x, double y, double size);
}
