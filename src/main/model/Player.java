package model;

import javafx.geometry.HorizontalDirection;
import javafx.geometry.VerticalDirection;

import static java.lang.Math.sqrt;
import static javafx.geometry.HorizontalDirection.LEFT;
import static javafx.geometry.HorizontalDirection.RIGHT;
import static javafx.geometry.VerticalDirection.DOWN;
import static javafx.geometry.VerticalDirection.UP;

// Represents the player of the game
public class Player extends MovingObject {
    private HorizontalDirection horizontalDirection;
    private VerticalDirection verticalDirection;
    private double speed;

    // EFFECTS: constructs a player
    public Player(double posX, double posY, double length, double speed, double maxHp) {
        super(posX, posY, length, 0, 0, maxHp);
        this.speed = speed;
        horizontalDirection = null;
        verticalDirection = null;
    }

    // MODIFIES: this
    // EFFECTS: set the horizontal direction and update the velocity accordingly
    public void setHorizontalDirection(HorizontalDirection horizontalDirection) {
        this.horizontalDirection = horizontalDirection;
        updateVelocity();
    }

    // MODIFIES: this
    // EFFECTS: set the vertical direction and update the velocity accordingly
    public void setVerticalDirection(VerticalDirection verticalDirection) {
        this.verticalDirection = verticalDirection;
        updateVelocity();
    }

    // MODIFIES: this
    // EFFECTS: increase hp proportional to the loss from maxHp
    public void regenerateHP(double rate) {
        if (hp < 99) {
            hp += (maxHp - hp) * rate;
        }
    }

    // MODIFIES: this
    // EFFECTS: set the horizontal velocity according to the way the player is currently facing
    private void updateVelocity() {
        double dimensionalSpeed;
        if (!(horizontalDirection == null || verticalDirection == null)) {
            dimensionalSpeed = speed / sqrt(2);
        } else {
            dimensionalSpeed = speed;
        }

        setHorizontalVelocity(dimensionalSpeed);
        setVerticalVelocity(dimensionalSpeed);
    }

    // MODIFIES: this
    // EFFECTS: set the vertical velocity according to the way the player is currently facing
    private void setVerticalVelocity(double dimensionalSpeed) {
        if (verticalDirection == DOWN) {
            dy = dimensionalSpeed;
        } else if (verticalDirection == UP) {
            dy = -dimensionalSpeed;
        } else {
            dy = 0;
        }
    }

    // MODIFIES: this
    // EFFECTS: set the horizontal velocity according to the way the player is currently facing
    private void setHorizontalVelocity(double dimensionalSpeed) {
        if (horizontalDirection == RIGHT) {
            dx = dimensionalSpeed;
        } else if (horizontalDirection == LEFT) {
            dx = -dimensionalSpeed;
        } else {
            dx = 0;
        }
    }

    // MODIFIES: this
    // EFFECTS: if the other is a wall, then move to its base, otherwise the regular hit method
    public void hit(GameObject other) {
        if (other instanceof Wall) {
            other.hit(this);
        } else {
            super.hit(other);
        }
    }
}
