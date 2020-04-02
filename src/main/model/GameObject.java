package model;

import javafx.geometry.Rectangle2D;

import static model.Game.HEIGHT;
import static model.Game.WIDTH;

// represents a generic game object
public abstract class GameObject {
    double posX;
    double posY;
    double width;
    double height;
    final double maxHp;
    double hp;

    // EFFECTS: constructs a game object
    GameObject(double posX, double posY, double width, double height, double maxHp) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.maxHp = maxHp;
        this.hp = maxHp;
    }

    // EFFECTS: return the leftmost x value of the object
    void setPosX(double posX) {
        this.posX = posX;
    }

    // EFFECTS: return the bottommost y value of the object
    void setPosY(double posY) {
        this.posY = posY;
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

    public double getHp() {
        return hp;
    }

    // EFFECTS: return the result of current hitpoints / max hitpoints
    public double getHpFraction() {
        if (isDead()) {
            return 0;
        }
        return hp / maxHp;
    }

    // MODIFIES: this
    // EFFECTS: subtract the other's hp from this' hp and call this function on the other object
    public void hit(GameObject other) {
        double damageToOther = hp;
        hp -= other.getHp();
        other.hit(damageToOther);
    }

    // MODIFIES: this
    // EFFECTS: subtract damage from this' hp
    private void hit(double damage) {
        hp -= damage;
    }

    // EFFECTS: return true if this is out of bounds, otherwise false
    boolean isOutOfBound() {
        return posX < 0 || posX > WIDTH || posY < 0 || posY > HEIGHT;
    }


    // EFFECTS: returns the x value of the centre of object
    public double getCentrePosX() {
        return posX + width / 2.0;
    }

    // EFFECTS: returns the y value of the centre of object
    public double getCentrePosY() {
        return posY + height / 2.0;
    }

    // EFFECTS: returns true if object is dead, otherwise false
    public boolean isDead() {
        return hp <= 0;
    }

    // EFFECTS: returns true if this object intersects the other object
    // source: https://github.com/tutsplus/Introduction-to-JavaFX-for-Game-Development/blob/master/Sprite.java
    public boolean intersects(GameObject other) {
        return this.getBounds().intersects(other.getBounds());
    }

    // EFFECTS: returns the rectangle bounds of the object
    // source: https://github.com/tutsplus/Introduction-to-JavaFX-for-Game-Development/blob/master/Sprite.java
    private Rectangle2D getBounds() {
        return new Rectangle2D(posX, posY, width, height);
    }
}