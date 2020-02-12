package model;

/*
 * Represents an enemy of the player
 */
public class Enemy extends MovingObject {

    // EFFECTS: constructs an enemy
    public Enemy(int posX, int posY, int dx, int dy) {
        super(posX, posY, dx, dy, "Enemy ");
    }
}
