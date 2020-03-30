package model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import javafx.geometry.HorizontalDirection;
import javafx.geometry.VerticalDirection;
import model.exceptions.GameOverException;
import model.gameobjects.*;
import persistence.Saveable;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/*
 * Represents the game
 */
public class Game implements Saveable {
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 680;
    public static int BULLET_SPEED = 7;

    private List<Wall> walls;
    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private Player player;
    private long timeElapsed;
    @Expose(serialize = false, deserialize = false)
    private List<CollisionPair> collisionCheckedPairs;

    // EFFECTS: constructs a new game with only the player
    public Game() {
        player = new Player(WIDTH / 2.0, HEIGHT / 2.0, 20, 20, 0);
        walls = new ArrayList<>();
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        timeElapsed = 0;
        collisionCheckedPairs = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: remove all previous game objects and add the new default ones
    public void initializeObjects() {
        player = new Player(WIDTH / 2.0, HEIGHT / 2.0, 20, 20, 2);
        enemies.add(new Enemy(200, 180, 20, 20, 1, -1, 50));
        enemies.add(new Enemy(270, 100, 20, 20, -1, 1, 50));
        enemies.add(new Enemy(500, 50, 20, 20, 0, 1, 50));
        walls.add(new Wall(248, 252, 100, 200, 1000));
    }

    // MODIFIES: this
    // EFFECTS: updates all the moving objects and returns true if game is over, otherwise false
    public void update(long deltaTime) throws GameOverException {
        updateMovingObjects(enemies);
        updateMovingObjects(bullets);
        player.update();
        checkCollisions();
        collisionCheckedPairs.forEach(CollisionPair::executeCollision);
        timeElapsed += deltaTime;
        if (player.isDead()) {
            throw new GameOverException();
        }
    }

    public void checkCollision(GameObject first, GameObject second) {
        CollisionPair collisionPair = new CollisionPair(first, second);
        if (first.intersects(second) && !collisionCheckedPairs.contains(collisionPair)) {
            collisionCheckedPairs.add(collisionPair);
        }
    }

    private void checkCollisions() {
        collisionCheckedPairs.clear();
        for (Bullet bullet : bullets) {
            enemies.forEach(enemy -> checkCollision(enemy, bullet));
            walls.forEach(wall -> checkCollision(wall, bullet));
            checkCollision(player, bullet);
        }

        for (Enemy enemy : enemies) {
            checkCollision(player, enemy);
        }
    }

    // MODIFIES: this
    // EFFECTS: update all the moving objects and remove the dead ones
    private void updateMovingObjects(List<? extends MovingObject> movingObjects) {
        List<MovingObject> toRemove = new ArrayList<>();
        for (MovingObject movingObject : movingObjects) {
            movingObject.update();
            if (movingObject.isDead()) {
                toRemove.add(movingObject);
            }
        }
        movingObjects.removeAll(toRemove);
    }

    // MODIFIES: this
    // EFFECTS: adds a bullet to the game with appropriate position and speed
    public void fireBullet() {
        double bulletPosX = player.getCentrePosX();
        double bulletPosY = player.getCentrePosY();
        double bulletDx = player.getDx();
        double bulletDy = player.getDy();
        if (player.getHorizontalFacingDirection() == HorizontalDirection.RIGHT) {
            bulletPosX += player.getWidth();
            bulletDx += BULLET_SPEED;
        } else if (player.getHorizontalFacingDirection() == HorizontalDirection.LEFT) {
            bulletPosX -= player.getWidth();
            bulletDx -= BULLET_SPEED;
        }
        if (player.getVerticalFacingDirection() == VerticalDirection.DOWN) {
            bulletPosY += player.getHeight();
            bulletDy += BULLET_SPEED;
        } else if (player.getVerticalFacingDirection() == VerticalDirection.UP) {
            bulletPosY -= player.getHeight();
            bulletDy -= BULLET_SPEED;
        }

        bullets.add(new Bullet(bulletPosX, bulletPosY, 5, bulletDx, bulletDy, 60));
    }

    public Player getPlayer() {
        return player;
    }

    public long getTimeElapsed() {
        return timeElapsed;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<CollisionPair> getCollisionCheckedPairs() {
        return collisionCheckedPairs;
    }

    @Override
    public void save(FileWriter fileWriter) {
        new Gson().toJson(this, fileWriter);
    }
}