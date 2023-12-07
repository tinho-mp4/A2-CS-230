package com.example._cs250a2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Dirt extends Tile{
    private static final Image DIRT_IMAGE = new Image(Dirt.class.getResourceAsStream("sprites/dirt.png"));
    private boolean compacted;

    public Dirt(int x, int y) {
        super("dirt",x, y, false);
        setPushableBlock(false);
        this.compacted = false;
    }



    //Player SHOULD be able to walk on the dirt block, which thus will convert it to a path
    //However, monster SHOULD NOT be able to walk on the dirt block unless it has been converted

    public void compact(){
        if (!compacted) {
            this.compacted = true;
            setPushableBlock(true);
            LevelLoader.setTile(x, y, new Path(x, y));

        }
    }

    @Override
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(DIRT_IMAGE, x*size, y*size);
    }
}
