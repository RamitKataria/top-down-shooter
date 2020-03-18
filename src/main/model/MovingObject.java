package model;

import static model.Game.HEIGHT;
import static model.Game.WIDTH;

/*
 * Represents a generic game object
 */
public abstract class MovingObject extends GameObject {
    protected int dx;
    protected int dy;

    // EFFECTS: constructs a moving object
    public MovingObject(int posX, int posY, int dx, int dy, String objectType) {
        super(posX, posY, objectType);
        this.dx = dx;
        this.dy = dy;
    }

    public int getDy() {
        return dy;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    // MODIFIES: this
    // EFFECTS: adds velocity to position
    public void move() {
        posX += dx;
        posY += dy;
        handleBoundary();

        posX = (posX + dx) % WIDTH;
        posY = (posY + dy) % HEIGHT;
    }

    private void handleBoundary() {
        if (posX < 0) {
            posX = WIDTH + posX;
        }
        if (posY < 0) {
            posY = HEIGHT + posY;
        }
        posX %= WIDTH;
        posY %= HEIGHT;
    }
}
