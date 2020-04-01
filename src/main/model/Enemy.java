package model;

import com.google.gson.annotations.Expose;

import static java.lang.Math.sqrt;

/*
 * Represents an enemy of the player
 */
public class Enemy extends MovingObject {
    private boolean isAuto;
    private double speed;

    @Expose(serialize = false, deserialize = false)
    private Player player;

    // EFFECTS: constructs a regular enemy
    public Enemy(double posX, double posY, double length, double dx, double dy, double hp) {
        super(posX, posY, length, dx, dy, hp);
        this.isAuto = false;
    }

    // EFFECTS: constructs a smart enemy that automatically follows the player
    public Enemy(double posX, double posY, double speed, double length, double hp, Player player) {
        super(posX, posY, length, 0, 0, hp);
        this.speed = speed;
        this.player = player;
        this.isAuto = true;
    }

    // MODIFIES: this
    // EFFECTS: if this is an automatic enemy, then change its velocity to go towards player
    //          Regardless, move and handle boundaries
    public void update() {
        if (isAuto) {
            manageVelocity();
        }
        super.update();
    }

    // MODIFIES: this
    // EFFECTS: change the velocity so that this goes towards player
    private void manageVelocity() {
        double deltaX = (player.getPosX() - posX);
        double deltaY = (player.getPosY() - posY);
        double distance = sqrt(deltaX * deltaX + deltaY * deltaY);
        double factorX = deltaX / distance;
        double factorY = deltaY / distance;
        dx = factorX * speed;
        dy = factorY * speed;
    }

    public boolean isAuto() {
        return isAuto;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
