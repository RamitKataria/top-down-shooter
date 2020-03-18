package model;

import javafx.scene.canvas.GraphicsContext;

public abstract class GameObject {
    protected int posX;
    protected int posY;
    protected int health;
    protected String objectType;

    // EFFECTS: constructs a game object
    public GameObject(int posX, int posY, String objectType) {
        this.posX = posX;
        this.posY = posY;
        this.objectType = objectType;
        this.health = 100;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getHealth() {
        return health;
    }

    public String getObjectType() {
        return objectType;
    }

    public void move() {

    }

    protected abstract void checkForCollision(GameObject other);

    protected abstract void draw(GraphicsContext gc);
}