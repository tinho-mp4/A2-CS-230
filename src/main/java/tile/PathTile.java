package tile;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

class PathTile extends Tile {
    private final Image pathImage;

    public PathTile(Image pathImage) {
        this.pathImage = new Image(getClass().getResourceAsStream("/tiles/path.png"));

    }
    @Override
    void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(pathImage, x, y, size, size);
    }
    String getText() {
        return "path";
    }
}