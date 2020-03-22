package model;

import javafx.scene.canvas.GraphicsContext;

/*
 * Represents a wall (blocks some of the game objects)
 */
public class Wall extends GameObject {
    int width;
    int height;

    // EFFECTS: constructs a wall
    public Wall(int posX, int posY, int height, int width) {
        super(posX, posY);
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    @Override
    protected void checkForCollision(GameObject other) {

    }

    @Override
    protected void render(GraphicsContext gc) {
        gc.fillRect(posX, posY, width, height);
    }
}
