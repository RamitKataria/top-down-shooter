package model.gameobjects;

/*
 * Represents an enemy of the player
 */
public class Enemy extends MovingObject {

    // EFFECTS: constructs an enemy
    public Enemy(double posX, double posY, double length, double dx, double dy, double hp) {
        super(posX, posY, length, dx, dy, hp);
    }
}
