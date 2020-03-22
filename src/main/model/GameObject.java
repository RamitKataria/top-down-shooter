package model;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public abstract class GameObject {
    protected double posX;
    protected double posY;
    protected double width;
    protected double height;
    protected double hp;

    // EFFECTS: constructs a game object
    public GameObject(double posX, double posY, double width, double height, double hp) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.hp = hp;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void hit(GameObject other) {
        hp -= other.hp;
    }

    public void update() {

    }

    public boolean isDead() {
        return hp <= 0;
    }

    // source: https://github.com/tutsplus/Introduction-to-JavaFX-for-Game-Development/blob/master/Sprite.java
    protected boolean intersects(GameObject other) {
        return this.getBounds().intersects(other.getBounds());
    }

    // source: https://github.com/tutsplus/Introduction-to-JavaFX-for-Game-Development/blob/master/Sprite.java
    protected Rectangle2D getBounds() {
        return new Rectangle2D(posX, posY, width, height);
    }

    protected abstract void render(GraphicsContext gc);
}