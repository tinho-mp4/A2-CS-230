package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;

abstract class Tile {


    abstract void draw(GraphicsContext gc, double x, double y, double size);
}

class PathTile extends Tile {
    @Override
    void draw(GraphicsContext gc, double x, double y, double size) {
        gc.setFill(javafx.scene.paint.Color.GREEN);
        gc.fillRect(x, y, size, size);
    }
    String getText() {
        return "path";
    }
}

class DirtTile extends Tile {
    @Override
    void draw(GraphicsContext gc, double x, double y, double size) {
        gc.setFill(javafx.scene.paint.Color.BROWN);
        gc.fillRect(x, y, size, size);
    }
    String getText() {
        return "dirt";
    }
}

class WallTile extends Tile {
    @Override
    void draw(GraphicsContext gc, double x, double y, double size) {
        gc.setFill(javafx.scene.paint.Color.GRAY);
        gc.fillRect(x, y, size, size);
    }
    String getText() {
        return "wall";
    }
}

class ExitTile extends Tile {
    @Override
    void draw(GraphicsContext gc, double x, double y, double size) {
        gc.setFill(javafx.scene.paint.Color.YELLOW);
        gc.fillRect(x, y, size, size);
    }
    String getText() {
        return "exit";
    }
}

class ButtonTile extends Tile {

    @Override
    void draw(GraphicsContext gc, double x, double y, double size) {
        gc.setFill(javafx.scene.paint.Color.ORANGE);
        gc.fillRect(x, y, size, size);
        // Additional logic to use the button number as needed
    }
}

class TrapTile extends Tile {
    private int trapNumber;

    public TrapTile(int trapNumber) {
        this.trapNumber = trapNumber;
    }


    @Override
    void draw(GraphicsContext gc, double x, double y, double size) {
        gc.setFill(javafx.scene.paint.Color.RED);
        gc.fillRect(x, y, size, size);
        // Additional logic to use the number (n) associated with the trap
    }
}

class WaterTile extends Tile {
    @Override
    void draw(GraphicsContext gc, double x, double y, double size) {
        gc.setFill(javafx.scene.paint.Color.BLUE);
        gc.fillRect(x, y, size, size);
    }
}

class ChipSocketTile extends Tile {
    @Override
    void draw(GraphicsContext gc, double x, double y, double size) {
        gc.setFill(javafx.scene.paint.Color.LIGHTGRAY);
        gc.fillRect(x, y, size, size);
    }
}

class IceTile extends Tile {
    @Override
    void draw(GraphicsContext gc, double x, double y, double size) {
        gc.setFill(javafx.scene.paint.Color.LIGHTBLUE);
        gc.fillRect(x, y, size, size);
    }
}

class ComputerChipTile extends Tile {
    @Override
    void draw(GraphicsContext gc, double x, double y, double size) {
        gc.setFill(javafx.scene.paint.Color.SILVER);
        gc.fillRect(x, y, size, size);
    }
}

class LockedDoorTile extends Tile {
    @Override
    void draw(GraphicsContext gc, double x, double y, double size) {
        gc.setFill(javafx.scene.paint.Color.DARKGRAY);
        gc.fillRect(x, y, size, size);
    }
}

class KeyTile extends Tile {
    @Override
    void draw(GraphicsContext gc, double x, double y, double size) {
        gc.setFill(javafx.scene.paint.Color.YELLOW);
        gc.fillRect(x, y, size, size);
    }
}

class BlockTile extends Tile {
    @Override
    void draw(GraphicsContext gc, double x, double y, double size) {
        gc.setFill(javafx.scene.paint.Color.BURLYWOOD);
        gc.fillRect(x, y, size, size);
    }
}

class PlayerTile extends Tile {
    @Override
    void draw(GraphicsContext gc, double x, double y, double size) {
        gc.setFill(javafx.scene.paint.Color.PLUM);
        gc.fillRect(x, y, size, size);
    }
}