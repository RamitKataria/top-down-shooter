package model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import javafx.geometry.HorizontalDirection;
import javafx.geometry.VerticalDirection;
import model.exceptions.GameOverException;
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
    List<CollisionPair> collisionCheckedPairs;

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
        checkCollisions();
        collisionCheckedPairs.forEach(CollisionPair::executeCollision);
        player.update();
        timeElapsed += deltaTime;
        if (player.isDead()) {
            throw new GameOverException();
        }
    }

    private void checkCollision(GameObject first, GameObject second, CollisionType type) {
        CollisionPair collisionPair = new CollisionPair(first, second, type);
        if (first.intersects(second) && !collisionCheckedPairs.contains(collisionPair)) {
            collisionCheckedPairs.add(collisionPair);
        }
    }

    private void checkCollisions() {
        for (Bullet bullet : bullets) {
            checkCollisionsWithEnemies(bullet);
            checkCollisionsWithWalls(bullet);
            checkCollisionWithPlayer(bullet);
        }

        for (Enemy enemy : enemies) {
            checkCollisionWithPlayer(enemy);
        }
    }

    private void checkCollisionWithPlayer(GameObject gameObject) {
        CollisionType type;
        if (gameObject.getClass() == Enemy.class) {
            type = CollisionType.PLAYER_ENEMY;
        } else {
            type = CollisionType.PLAYER_BULLET;
        }
        checkCollision(player, gameObject, type);
    }

    // MODIFIES: this, bullet
    // EFFECTS: hit the enemies that intersect the bullet and return true if the bullet hit something
    private void checkCollisionsWithEnemies(GameObject gameObject) {
        CollisionType type = CollisionType.ENEMY_BULLET;
        for (Enemy enemy : enemies) {
            checkCollision(enemy, gameObject, type);
        }
    }

    private void checkCollisionsWithWalls(GameObject gameObject) {
        CollisionType type = CollisionType.WALL_BULLET;
        for (Wall wall : walls) {
            checkCollision(wall, gameObject, type);
        }
    }

    // MODIFIES: this
    // EFFECTS: update all the moving objects and remove the dead ones
    private void updateMovingObjects(List<? extends MovingObject> movingObjects) {
        for (MovingObject movingObject : movingObjects) {
            movingObject.update();
        }
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

        bullets.add(new Bullet(bulletPosX, bulletPosY, 5, 5, bulletDx, bulletDy, 60));
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

    @Override
    public void save(FileWriter fileWriter) {
        new Gson().toJson(this, fileWriter);
    }
}