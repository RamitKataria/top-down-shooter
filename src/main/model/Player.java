package model;

import javafx.scene.canvas.GraphicsContext;

/*
 * Represents the player of the game
 */
public class Player extends MovingObject {

    // EFFECTS: constructs a player
    public Player(int posX, int posY, int dx, int dy) {
        super(posX, posY, dx, dy);
    }

    @Override
    protected void checkForCollision(GameObject other) {

    }

    @Override
    protected void render(GraphicsContext gc) {
        gc.fillOval(posX, posY, 20, 20);
    }
}
