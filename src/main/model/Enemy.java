package model;

/*
 * Represents an enemy of the player
 */
public class Enemy extends MovingObject {

    // EFFECTS: constructs an enemy
    public Enemy(double posX, double posY, double width, double height, double dx, double dy, double hp) {
        super(posX, posY, width, height, dx, dy, hp);
    }
}
