package model;

import javafx.geometry.Rectangle2D;

// represents a generic game object
public abstract class GameObject {
    protected double posX;
    protected double posY;
    protected double width;
    protected double height;
    protected final double maxHp;
    protected double hp;

    // EFFECTS: constructs a game object
    public GameObject(double posX, double posY, double width, double height, double maxHp) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.maxHp = maxHp;
        this.hp = maxHp;
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

    // only for testing
    public double getHp() {
        return hp;
    }

    public double getHpFraction() {
        return hp / maxHp;
    }

    // MODIFIES: this
    // EFFECTS: subtract the other's hp from this' hp
    public void hit(GameObject other) {
        if (!other.equals(this)) {
            hp -= other.hp;
        }
    }

    // EFFECTS: returns the x value of the centre of object as opposed to the leftmost x value
    public double getCentrePosX() {
        return posX + width / 2.0;
    }

    // EFFECTS: returns the y value of the centre of object as opposed to the leftmost y value
    public double getCentrePosY() {
        return posY + height / 2.0;
    }

    // EFFECTS: returns true if object is dead, otherwise false
    public boolean isDead() {
        return hp <= 0;
    }

    // EFFECTS: returns true if this object intersects the other object
    // source: https://github.com/tutsplus/Introduction-to-JavaFX-for-Game-Development/blob/master/Sprite.java
    protected boolean intersects(GameObject other) {
        return this.getBounds().intersects(other.getBounds());
    }

    // EFFECTS: returns the rectangle bounds of the object
    // source: https://github.com/tutsplus/Introduction-to-JavaFX-for-Game-Development/blob/master/Sprite.java
    protected Rectangle2D getBounds() {
        return new Rectangle2D(posX, posY, width, height);
    }
}