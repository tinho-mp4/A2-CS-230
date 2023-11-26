package tile;

import javafx.scene.canvas.GraphicsContext;

class ChipSocketTile extends Tile {
    @Override
    void draw(GraphicsContext gc, double x, double y, double size) {
        gc.setFill(javafx.scene.paint.Color.LIGHTGRAY);
        gc.fillRect(x, y, size, size);
    }
}