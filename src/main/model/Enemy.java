package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/*
 * Represents an enemy of the player
 */
public class Enemy extends MovingObject {

    // EFFECTS: constructs an enemy
    public Enemy(double posX, double posY, double width, double height, double dx, double dy, double hp) {
        super(posX, posY, width, height, dx, dy, hp);
    }

    // MODIFIES: gc
    // EFFECTS: fills gc with a red square with  width, height, and position of this
    @Override
    protected void render(GraphicsContext gc) {
        gc.setFill(new Color(196 / 255.0, 14 / 255.0, 14 / 255.0, 1));
        gc.fillRect(posX, posY, width, width);
    }
}
