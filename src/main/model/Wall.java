package model;

import javafx.scene.canvas.GraphicsContext;

/*
 * Represents a wall (blocks some of the game objects)
 */
public class Wall extends GameObject {
    private static final String objectType = "Wall";
    int width;
    int height;

    // EFFECTS: constructs a wall
    public Wall(int posX, int posY, int height, int width) {
        super(posX, posY, objectType);
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
    protected void draw(GraphicsContext gc) {
        gc.fillRect(posX, posY, width, height);
    }
}
