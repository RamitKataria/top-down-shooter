package model;

/*
 * Represents a wall (blocks some of the game objects)
 */
public class Wall extends GameObject {

    // EFFECTS: constructs a wall
    public Wall(double posX, double posY, double width, double height, double hp) {
        super(posX, posY, width, height, hp);
    }

    // MODIFIES: this
    // EFFECTS: In addition to normal damage done, bounce the other object if it is a bullet
    public void hit(GameObject other) {
        super.hit(other);
        if (other instanceof Bullet) {
            ((Bullet) other).bounce();
        }
    }


}
