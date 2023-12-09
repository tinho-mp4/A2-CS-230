package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Chip extends Item {
    private static final Image CHIP_IMAGE = new Image(Chip.class.getResourceAsStream("sprites/chip.png"));

    public Chip(int x, int y) {
        super("chip",x, y);
    }


    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(CHIP_IMAGE, x*size, y*size);
    }
}