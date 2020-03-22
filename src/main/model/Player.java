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
    HorizontalDirection horizontalDirection;
    VerticalDirection verticalDirection;

    // EFFECTS: constructs a player
    public Player(double posX, double posY, double width, double height, double speed) {
        super(posX, posY, width, height, 0, 0, maxHP);
        this.speed = speed;
    }

    public void update() {
        super.update();
        if (dx > 0) {
            horizontalDirection = HorizontalDirection.RIGHT;
        } else if (dx < 0) {
            horizontalDirection = HorizontalDirection.LEFT;
        }
        if (dy > 0) {
            verticalDirection = VerticalDirection.DOWN;
        } else if (dy < 0) {
            verticalDirection = VerticalDirection.UP;
        }
    }

    public HorizontalDirection getHorizontalDirection() {
        return horizontalDirection;
    }

    public VerticalDirection getVerticalDirection() {
        return verticalDirection;
    }

    public void setHorizontalDirection(HorizontalDirection horizontalDirection) {
        this.horizontalDirection = horizontalDirection;
        if (horizontalDirection == HorizontalDirection.RIGHT) {
            dx = speed;
        } else if (horizontalDirection == HorizontalDirection.LEFT) {
            dx = -speed;
        } else {
            dx = 0;
        }
    }

    public void setVerticalDirection(VerticalDirection verticalDirection) {
        this.verticalDirection = verticalDirection;
        if (verticalDirection == VerticalDirection.DOWN) {
            dy = speed;
        } else if (verticalDirection == VerticalDirection.UP) {
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
