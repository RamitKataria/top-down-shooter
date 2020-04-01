package model;

/*
 * Represents a bullet
 */
public class Bullet extends MovingObject {

    // EFFECTS: constructs a bullet
    public Bullet(double posX, double posY, double radius, double dx, double dy, double hp) {
        super(posX, posY, radius, dx, dy, hp);
    }

    // MODIFIES: this
    // EFFECTS: move this and if this goes out of bounds, then kill this
    @Override
    public void update() {
        super.move();
        if (isOutOfBound()) {
            hp = 0;
        }
    }

    public void hit(GameObject other) {
        if (other instanceof Wall) {
            other.hit(this);
        } else {
            super.hit(other);
        }
    }

    // MODIFIES: this
    // EFFECTS: set the velocity to opposite direction
    public void bounce() {
        dx *= -1;
        dy *= -1;
    }
}
