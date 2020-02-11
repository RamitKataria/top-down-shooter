package model;

/*
 * Represents a generic game object
 */
public abstract class MovingObject extends GameObject {
    protected int dx;
    protected int dy;

    // EFFECTS: constructs a moving object
    public MovingObject(int posX, int posY, int dx, int dy) {
        super(posX, posY);
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

    // EFFECTS: returns the name of the Object
    public abstract String getName();
}
