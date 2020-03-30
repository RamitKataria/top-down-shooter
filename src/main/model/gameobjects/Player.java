package model.gameobjects;

import javafx.geometry.HorizontalDirection;
import javafx.geometry.VerticalDirection;

// Represents the player of the game
public class Player extends MovingObject {
    public static final double maxHP = 100;

    private double speed;
    HorizontalDirection horizontalFacingDirection;
    VerticalDirection verticalFacingDirection;
    HorizontalDirection horizontalMovingDirection;
    VerticalDirection verticalMovingDirection;

    // EFFECTS: constructs a player facing UP
    public Player(double posX, double posY, double width, double height, double speed) {
        super(posX, posY, width, height, 0, 0, maxHP);
        this.speed = speed;
        verticalFacingDirection = VerticalDirection.UP;
    }

    // MODIFIES: this
    // EFFECTS: in addition to moving, sets the appropriate facing direction
    public void update() {
        super.update();
        if (dx > 0) {
            horizontalFacingDirection = HorizontalDirection.RIGHT;
        } else if (dx < 0) {
            horizontalFacingDirection = HorizontalDirection.LEFT;
        }
        if (dy > 0) {
            verticalFacingDirection = VerticalDirection.DOWN;
        } else if (dy < 0) {
            verticalFacingDirection = VerticalDirection.UP;
        }
    }

    public HorizontalDirection getHorizontalFacingDirection() {
        return horizontalFacingDirection;
    }

    public VerticalDirection getVerticalFacingDirection() {
        return verticalFacingDirection;
    }

    // MODIFIES: this
    // EFFECTS: sets the horizontal moving direction and accordingly sets the facing direction
    public void setHorizontalMovingDirection(HorizontalDirection horizontalMovingDirection) {
        this.horizontalMovingDirection = horizontalMovingDirection;

        if (horizontalMovingDirection != null && verticalMovingDirection == null) {
            verticalFacingDirection = null;
            horizontalFacingDirection = horizontalMovingDirection;
        }

        updateHorizontalVelocity();
    }

    // MODIFIES: this
    // EFFECTS: updates the horizontal velocity based on moving direction
    private void updateHorizontalVelocity() {
        if (horizontalMovingDirection == HorizontalDirection.RIGHT) {
            dx = speed;
        } else if (horizontalMovingDirection == HorizontalDirection.LEFT) {
            dx = -speed;
        } else {
            dx = 0;
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the vertical moving direction and accordingly sets the facing direction
    public void setVerticalMovingDirection(VerticalDirection verticalMovingDirection) {
        this.verticalMovingDirection = verticalMovingDirection;

        if (verticalMovingDirection != null && horizontalMovingDirection == null) {
            horizontalFacingDirection = null;
            verticalFacingDirection = verticalMovingDirection;
        }

        setVerticalVelocity();
    }

    // MODIFIES: this
    // EFFECTS: updates the vertical velocity based on moving direction
    private void setVerticalVelocity() {
        if (verticalMovingDirection == VerticalDirection.DOWN) {
            dy = speed;
        } else if (verticalMovingDirection == VerticalDirection.UP) {
            dy = -speed;
        } else {
            dy = 0;
        }
    }
}
