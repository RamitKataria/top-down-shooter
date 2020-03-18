package model;

import javafx.scene.canvas.GraphicsContext;

import static model.Game.HEIGHT;
import static model.Game.WIDTH;

/*
 * Represents a bullet
 */
public class Bullet extends MovingObject {
    private static final String objectType = "Bullet";

    // EFFECTS: constructs a bullet
    public Bullet(int posX, int posY, int dx, int dy) {
        super(posX, posY, dx, dy, objectType);
    }

    public void move() {
        super.move();
    }

    @Override
    protected void checkForCollision(GameObject other) {
        if (isOutOfBound()) {
            health = 0;
        }
    }

    @Override
    protected void draw(GraphicsContext gc) {
        gc.fillOval(posX, posY, 5, 5);
    }

    public boolean isOutOfBound() {
        return posX < 0 || posX > WIDTH || posY < 0 || posY > HEIGHT;
    }
}
