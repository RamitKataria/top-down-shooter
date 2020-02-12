package model;

/*
 * Represents a generic game object
 */
public abstract class MovingObject extends GameObject {
    protected int dx;
    protected int dy;

    // EFFECTS: constructs a moving object
    public MovingObject(int posX, int posY, int dx, int dy, String name) {
        super(posX, posY, name);
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
    }
}
