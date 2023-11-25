package tile;

import javafx.scene.canvas.GraphicsContext;

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