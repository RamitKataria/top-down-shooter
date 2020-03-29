package model;

import static model.Game.HEIGHT;
import static model.Game.WIDTH;

/*
 * Represents a bullet
 */
public class Bullet extends MovingObject {

    // EFFECTS: constructs a bullet
    public Bullet(double posX, double posY, double width, double height, double dx, double dy, double hp) {
        super(posX, posY, width, height, dx, dy, hp);
    }

    // MODIFIES: this
    // EFFECTS: move this and if this goes out of bounds, then kill this
    @Override
    public void update() {
        posX += dx;
        posY += dy;
        if (isOutOfBound()) {
            hp = 0;
        }
    }

    // MODIFIES: this
    // EFFECTS: set the velocity to opposite direction
    public void bounce() {
        dx *= -1;
        dy *= -1;
    }

    // EFFECTS: return true if this is out of bounds, otherwise false
    public boolean isOutOfBound() {
        return posX < 0 || posX > WIDTH || posY < 0 || posY > HEIGHT;
    }
}
