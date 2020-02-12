package model;

public abstract class GameObject {
    protected int posX;
    protected int posY;
    protected String name;

    // EFFECTS: constructs a game object
    public GameObject(int posX, int posY, String name) {
        this.posX = posX;
        this.posY = posY;
        this.name = name;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public String getName() {
        return name;
    }
}