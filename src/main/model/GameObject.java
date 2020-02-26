package model;

public abstract class GameObject {
    protected int posX;
    protected int posY;

    // EFFECTS: constructs a game object
    public GameObject(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}