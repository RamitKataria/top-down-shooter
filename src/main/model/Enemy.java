package model;

import javafx.scene.canvas.GraphicsContext;

/*
 * Represents an enemy of the player
 */
public class Enemy extends MovingObject {

    // EFFECTS: constructs an enemy
    public Enemy(int posX, int posY, int dx, int dy) {
        super(posX, posY, dx, dy);
    }

    @Override
    protected void checkForCollision(GameObject other) {

    }

    @Override
    protected void render(GraphicsContext gc) {
        gc.fillRect(posX, posY, 20, 20);
    }
}
