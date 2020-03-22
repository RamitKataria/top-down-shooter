package model;

import javafx.scene.canvas.GraphicsContext;

public abstract class GameObject {
    protected int posX;
    protected int posY;
    protected int health;

    // EFFECTS: constructs a game object
    public GameObject(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        this.health = 100;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public abstract void move();

    public boolean isDead() {
        return health <= 0;
    }

    protected void checkForCollision(GameObject other) {

    }

    protected abstract void render(GraphicsContext gc);
}