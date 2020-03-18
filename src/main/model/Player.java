package model;

import javafx.scene.canvas.GraphicsContext;

/*
 * Represents the player of the game
 */
public class Player extends MovingObject {
    private static final String objectType = "Player";

    // EFFECTS: constructs a player
    public Player(int posX, int posY, int dx, int dy) {
        super(posX, posY, dx, dy, objectType);
    }

    @Override
    protected void checkForCollision(GameObject other) {

    }

    @Override
    protected void draw(GraphicsContext gc) {
        gc.fillOval(posX, posY, 20, 20);
    }
}
