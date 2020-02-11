package model;

/*
 * Represents the player of the game
 */
public class Player extends MovingObject {

    // EFFECTS: constructs a player
    public Player(int posX, int posY, int dx, int dy) {
        super(posX, posY, dx, dy);
    }

    // EFFECTS: returns the name of the Object
    @Override
    public String getName() {
        return "Player";
    }


}
