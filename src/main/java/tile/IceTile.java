package tile;

import javafx.scene.canvas.GraphicsContext;

class IceTile extends Tile {
    @Override
    void draw(GraphicsContext gc, double x, double y, double size) {
        gc.setFill(javafx.scene.paint.Color.LIGHTBLUE);
        gc.fillRect(x, y, size, size);
    }
}