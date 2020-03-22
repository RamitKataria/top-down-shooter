package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/*
 * Represents a wall (blocks some of the game objects)
 */
public class Wall extends GameObject {

    // EFFECTS: constructs a wall
    public Wall(double posX, double posY, double width, double height, double hp) {
        super(posX, posY, width, height, hp);
    }

    @Override
    protected void render(GraphicsContext gc) {
        gc.setFill(new Color(0, 0, 0, 1));
        gc.fillRect(posX, posY, width, height);
    }

    public void hit(GameObject other) {
        super.hit(other);
        if (other instanceof Bullet) {
            ((Bullet) other).bounce();
        }
    }


}
