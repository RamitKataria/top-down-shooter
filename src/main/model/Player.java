package model;

import javafx.geometry.HorizontalDirection;
import javafx.geometry.VerticalDirection;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/*
 * Represents the player of the game
 */
public class Player extends MovingObject {
    public static final double maxHP = 100;

    private double speed;
    HorizontalDirection horizontalFacingDirection;
    VerticalDirection verticalFacingDirection;
    HorizontalDirection horizontalMovingDirection;
    VerticalDirection verticalMovingDirection;

    // EFFECTS: constructs a player
    public Player(double posX, double posY, double width, double height, double speed) {
        super(posX, posY, width, height, 0, 0, maxHP);
        this.speed = speed;
        verticalFacingDirection = VerticalDirection.UP;
    }

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

    public void setHorizontalMovingDirection(HorizontalDirection horizontalMovingDirection) {
        this.horizontalMovingDirection = horizontalMovingDirection;

        if (horizontalMovingDirection != null && verticalMovingDirection == null) {
            verticalFacingDirection = null;
            horizontalFacingDirection = horizontalMovingDirection;
        }

        if (horizontalMovingDirection == HorizontalDirection.RIGHT) {
            dx = speed;
        } else if (horizontalMovingDirection == HorizontalDirection.LEFT) {
            dx = -speed;
        } else {
            dx = 0;
        }
    }

    public void setVerticalMovingDirection(VerticalDirection verticalMovingDirection) {
        this.verticalMovingDirection = verticalMovingDirection;

        if (verticalMovingDirection != null && horizontalMovingDirection == null) {
            horizontalFacingDirection = null;
            verticalFacingDirection = verticalMovingDirection;
        }

        if (verticalMovingDirection == VerticalDirection.DOWN) {
            dy = speed;
        } else if (verticalMovingDirection == VerticalDirection.UP) {
            dy = -speed;
        } else {
            dy = 0;
        }
    }

    @Override
    protected void render(GraphicsContext gc) {
        gc.setFill(new Color(92 / 255.0, 237 / 255.0, 237 / 255.0, 1));
        gc.fillRect(posX, posY, width, height);
    }
}
