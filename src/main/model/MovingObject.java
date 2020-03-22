package model;

import static model.Game.HEIGHT;
import static model.Game.WIDTH;

//  Represents a generic game object
public abstract class MovingObject extends GameObject {
    protected double dx;
    protected double dy;

    // EFFECTS: constructs a moving object
    public MovingObject(double posX, double posY, double width, double height, double dx, double dy, double hp) {
        super(posX, posY, width, height, hp);
        this.dx = dx;
        this.dy = dy;
    }

    public double getDy() {
        return dy;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    // MODIFIES: this
    // EFFECTS: adds velocity to position
    public void update() {
        posX += dx;
        posY += dy;
        handleBoundary();

        posX = (posX + dx) % WIDTH;
        posY = (posY + dy) % HEIGHT;
    }

    // MODIFIES: this
    // EFFECTS: if this goes beyond the bounds, then set the position to come out of the opposite side
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
