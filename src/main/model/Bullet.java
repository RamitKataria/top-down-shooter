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

    public void update() {
        posX += dx;
        posY += dy;
        if (isOutOfBound()) {
            hp = 0;
        }
    }

    public void bounce() {
        dx *= -1;
        dy *= -1;
    }

    @Override
    protected void render(GraphicsContext gc) {
        gc.setFill(new Color(2 / 255.0, 44 / 255.0, 250 / 255.0, 1));
        gc.fillOval(posX, posY, width, width);
    }

    public boolean isOutOfBound() {
        return posX < 0 || posX > WIDTH || posY < 0 || posY > HEIGHT;
    }
}
