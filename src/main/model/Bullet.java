package model;

/*
 * Represents a bullet
 */
public class Bullet extends MovingObject {

    // EFFECTS: constructs a bullet
    public Bullet(int posX, int posY, int dx, int dy) {
        super(posX, posY, dx, dy);
    }

    // EFFECTS: returns the name of the Object
    @Override
    public String getName() {
        return "Bullet";
    }
}
