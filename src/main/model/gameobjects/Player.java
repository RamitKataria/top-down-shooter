package model.gameobjects;

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

    // EFFECTS: constructs a player facing UP
    public Player(double posX, double posY, double length, double speed, double maxHp) {
        super(posX, posY, length, 0, 0, maxHp);
        this.speed = speed;
        horizontalDirection = null;
        verticalDirection = null;
    }

    // MODIFIES: this
    // EFFECTS:
    public void setHorizontalMovingDirection(HorizontalDirection horizontalDirection) {
        this.horizontalDirection = horizontalDirection;
        updateVelocity();
    }

    // MODIFIES: this
    // EFFECTS:
    public void setVerticalMovingDirection(VerticalDirection verticalDirection) {
        this.verticalDirection = verticalDirection;
        updateVelocity();
    }

    public void regenerateHP(double rate) {
        if (hp < 99) {
            hp += (maxHp - hp) * rate;
        }
    }

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

    private void setVerticalVelocity(double dimensionalSpeed) {
        if (verticalDirection == DOWN) {
            dy = dimensionalSpeed;
        } else if (verticalDirection == UP) {
            dy = -dimensionalSpeed;
        } else {
            dy = 0;
        }
    }

    private void setHorizontalVelocity(double dimensionalSpeed) {
        if (horizontalDirection == RIGHT) {
            dx = dimensionalSpeed;
        } else if (horizontalDirection == LEFT) {
            dx = -dimensionalSpeed;
        } else {
            dx = 0;
        }
    }

    public void hit(GameObject other) {
        if (other instanceof Wall) {
            other.hit(this);
        } else {
            super.hit(other);
        }
    }
}
