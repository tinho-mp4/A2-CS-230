package tile;


import javafx.scene.canvas.GraphicsContext;

class ButtonTile extends Tile {

    @Override
    void draw(GraphicsContext gc, double x, double y, double size) {
        gc.setFill(javafx.scene.paint.Color.ORANGE);
        gc.fillRect(x, y, size, size);
        // Additional logic to use the button number as needed
    }
}