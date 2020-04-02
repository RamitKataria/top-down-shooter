package model;

import static model.Game.HEIGHT;
import static model.Game.WIDTH;

//  Represents a generic moving object
public abstract class MovingObject extends GameObject {
    double dx;
    double dy;

    // EFFECTS: constructs a moving object
    MovingObject(double posX, double posY, double length, double dx, double dy, double hp) {
        super(posX, posY, length, length, hp);
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
    // EFFECTS: adds velocity to position and handles boundary
    public void update() {
        move();
        handleBoundary();
    }

    // MODIFIES: this
    // EFFECTS: move the object by its velocity
    void move() {
        posX += dx;
        posY += dy;
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
