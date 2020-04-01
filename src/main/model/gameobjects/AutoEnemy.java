package model.gameobjects;

import static java.lang.Math.sqrt;

// represents an auto-enemy that is smart enough to go straight to player
public class AutoEnemy extends Enemy {
    public static final double AUTO_ENEMY_LENGTH = 30;
    private Player player;
    private double speed;

    // EFFECTS: construct an auto-enemy with default length
    public AutoEnemy(double posX, double posY, double speed, double maxHp, Player player) {
        super(posX, posY, AUTO_ENEMY_LENGTH, 0, 0, maxHp);
        this.speed = speed;
        this.player = player;
    }

    // MODIFIES:
    public void update() {
        manageVelocity();
        super.update();
    }

    private void manageVelocity() {
        double deltaX = (player.getPosX() - posX);
        double deltaY = (player.getPosY() - posY);
        double distance = sqrt(deltaX * deltaX + deltaY * deltaY);
        double factorX = deltaX / distance;
        double factorY = deltaY / distance;
        dx = factorX * speed;
        dy = factorY * speed;
    }

    /*protected void handleBoundary() {
        if (posX < 0) {
            posX = 0;
        } else if (posX > WIDTH) {
            posX = WIDTH;
        }
        if (posY < 0) {
            posY = 0;
        } else if (posY > HEIGHT) {
            posY = HEIGHT;
        }
    }*/
}
