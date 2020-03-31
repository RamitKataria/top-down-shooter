package model.gameobjects;

/*
 * Represents a wall (blocks some of the game objects)
 */
public class Wall extends GameObject {

    // EFFECTS: constructs a wall
    public Wall(double posX, double posY, double width, double height, double hp) {
        super(posX, posY, width, height, hp);
    }

    public void hit(GameObject other) {
        if (other instanceof Bullet) {
            hp -= other.getHp();
            ((Bullet) other).bounce();
        } else if (other instanceof Player) {
            hp -= other.getHp();
            other.setPosX(posX + width);
            other.setPosY(posY + height);
        }
    }
}
