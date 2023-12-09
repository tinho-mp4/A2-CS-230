package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;

public abstract class Item {

    protected String name;
    protected int x;
    protected int y;

    public Item(String name, int x, int y, boolean b) {
        this.name = name;
        this.x = x;
        this.y = y;
    }
    public static String itemName;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Item(String name) {
        itemName = name;
    }

    public Item(int x, int y) {
    }

    public String getName() {
        return this.name;
    }


    public static String getItemName() {
        return itemName;
    }

    public abstract void draw(GraphicsContext gc, double x, double y, double size);

    public void addToInventory(Item item) {
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
