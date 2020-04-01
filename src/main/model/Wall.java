package model;

/*
 * Represents a magical wall
 */
public class Wall extends GameObject {

    // EFFECTS: constructs a wall
    public Wall(double posX, double posY, double width, double height, double hp) {
        super(posX, posY, width, height, hp);
    }

    // EFFECTS: if other is a Bullet, reverse its direction; if it is a Player, then move it to base
    public void hit(GameObject other) {
        if (other instanceof Bullet) {
            hp -= other.getHp();
            ((Bullet) other).bounce();
        } else if (other instanceof Player) {
            other.setPosX(posX + width);
            other.setPosY(posY + height);
        }
    }
}
