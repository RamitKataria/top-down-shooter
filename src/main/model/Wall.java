package model;

/*
 * Represents a wall (blocks some of the game objects)
 */
public class Wall extends GameObject {

    // EFFECTS: constructs a wall
    public Wall(int posX, int posY) {
        super(posX, posY, "Wall  ");
    }
}
