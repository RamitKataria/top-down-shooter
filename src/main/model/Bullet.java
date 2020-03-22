package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static model.Game.HEIGHT;
import static model.Game.WIDTH;

/*
 * Represents a bullet
 */
public class Bullet extends MovingObject {

    // EFFECTS: constructs a bullet
    public Bullet(double posX, double posY, double width, double height, double dx, double dy, double hp) {
        super(posX, posY, width, height, dx, dy, hp);
    }

    // MODIFIES: this
    // EFFECTS: move this and if this goes out of bounds, then kill this
    @Override
    public void update() {
        posX += dx;
        posY += dy;
        if (isOutOfBound()) {
            hp = 0;
        }
    }

    // MODIFIES: this
    // EFFECTS: set the velocity to opposite direction
    public void bounce() {
        dx *= -1;
        dy *= -1;
    }

    // MODIFIES: gc
    // EFFECTS: fill gc with a small dot at position of this
    @Override
    protected void render(GraphicsContext gc) {
        gc.setFill(new Color(2 / 255.0, 44 / 255.0, 250 / 255.0, 1));
        gc.fillOval(posX, posY, width, width);
    }

    // EFFECTS: return true if this is out of bounds, otherwise false
    public boolean isOutOfBound() {
        return posX < 0 || posX > WIDTH || posY < 0 || posY > HEIGHT;
    }
}
