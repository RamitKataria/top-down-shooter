package model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import model.exceptions.GameOverException;
import model.gameobjects.*;
import persistence.Saveable;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static java.lang.Math.round;
import static java.lang.Math.sqrt;

/*
 * Represents the game
 */
public class Game implements Saveable {
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 680;
    public static final double BULLET_SPEED = 10;
    public static final double PLAYER_SPEED = 5;
    public static final double AUTO_ENEMY_SPEED = 4;
    public static final double BULLET_RADIUS = 5;
    public static final double PLAYER_LENGTH = 20;
    public static final double WALL_DIM_1 = 200;
    public static final double WALL_DIM_2 = 25;
    public static final double PLAYER_MAX_HP = 100;
    public static final double AUTO_ENEMY_HP = 70;
    public static final double REGULAR_ENEMY_HP = 30;
    public static final double BULLET_HP = 20;
    public static final int MAX_WALLS = 10;
    public static final double PLAYER_HEALTH_REGENERATION_RATE = 0.001;
    public static final int ENEMY_FREQUENCY = 60; // in FRAMES
    public static final Random RND = new Random();

    private List<Wall> walls;
    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private Player player;
    private long milliTimeElapsed;
    private double regularEnemySpeed;
    @Expose(serialize = false, deserialize = false)
    private List<CollisionPair> collisionCheckedPairs;

    // EFFECTS: constructs a new game with only the player
    public Game() {
        player = new Player(WIDTH / 2.0, HEIGHT / 2.0, PLAYER_LENGTH, PLAYER_SPEED, PLAYER_MAX_HP);
        walls = new ArrayList<>();
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        milliTimeElapsed = 0;
        regularEnemySpeed = 1;
        collisionCheckedPairs = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: remove all previous game objects and add the new default ones
    public void initializeWalls() {
        for (int i = 0; i < MAX_WALLS / 2; i++) {
            walls.add(new Wall(RND.nextInt(WIDTH), RND.nextInt(HEIGHT), WALL_DIM_2, WALL_DIM_1, 1000));
            walls.add(new Wall(RND.nextInt(WIDTH), RND.nextInt(HEIGHT), WALL_DIM_1, WALL_DIM_2, 1000));
        }
    }

    private void generateEnemies() {
        int randomNumber = RND.nextInt(ENEMY_FREQUENCY * 40);
        if (randomNumber < 4) {
            double posX;
            double posY;
            if (randomNumber == 0) {
                posX = 0;
                posY = 0;
            } else if (randomNumber == 1) {
                posX = WIDTH;
                posY = 0;
            } else if (randomNumber == 2) {
                posX = WIDTH;
                posY = HEIGHT;
            } else {
                posX = 0;
                posY = HEIGHT;
            }
            addEnemyIfSpaceAvailable(new AutoEnemy(posX, posY, AUTO_ENEMY_SPEED, AUTO_ENEMY_HP, player));
        } else if (randomNumber < 40) {
            double ds = round((randomNumber - 20) / 20.0) * regularEnemySpeed;
            addEnemyIfSpaceAvailable(new Enemy(27 * randomNumber, 27 * 40, PLAYER_LENGTH, ds, -ds, REGULAR_ENEMY_HP));
            regularEnemySpeed += 0.02;
        }
    }

    private void addEnemyIfSpaceAvailable(Enemy e) {
        if (!e.intersects(player)) {
            enemies.add(e);
        }
    }

    private void generateWalls() {
        while (walls.size() < MAX_WALLS - 1) {
            walls.add(new Wall(RND.nextInt(WIDTH), RND.nextInt(HEIGHT), WALL_DIM_2, WALL_DIM_1, 1000));
            walls.add(new Wall(RND.nextInt(WIDTH), RND.nextInt(HEIGHT), WALL_DIM_1, WALL_DIM_2, 1000));
        }
    }

    // MODIFIES: this
    // EFFECTS: updates all the moving objects and returns true if game is over, otherwise false
    public void update(int milliDeltaTime) throws GameOverException {
        updateMovingObjects(enemies);
        updateMovingObjects(bullets);
        removeDeadWalls();
        player.update();
        checkCollisions();
        collisionCheckedPairs.forEach(CollisionPair::executeCollision);
        generateEnemies();
        generateWalls();
        player.regenerateHP(PLAYER_HEALTH_REGENERATION_RATE);
        milliTimeElapsed += milliDeltaTime;
        if (player.isDead()) {
            throw new GameOverException();
        }
    }

    private void removeDeadWalls() {
        boolean removed = false;
        Iterator<Wall> wallIterator = walls.iterator();
        while (wallIterator.hasNext()) {
            if (wallIterator.next().isDead()) {
                wallIterator.remove();
                removed = true;
            }
        }
        if (removed) {
            generateWalls();
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

        for (Wall wall : walls) {
            checkCollision(player, wall);
        }
    }

    // MODIFIES: this
    // EFFECTS: update all the moving objects and remove the dead ones
    private void updateMovingObjects(List<? extends MovingObject> movingObjects) {
        Iterator<? extends MovingObject> iterator = movingObjects.iterator();
        while (iterator.hasNext()) {
            MovingObject next = iterator.next();
            next.update();
            if (next.isDead()) {
                iterator.remove();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a bullet to the game with appropriate position and speed
    public void fireBullet(double pointX, double pointY) {
        double deltaX = (pointX - player.getCentrePosX());
        double deltaY = (pointY - player.getCentrePosY());
        double distance = sqrt(deltaX * deltaX + deltaY * deltaY);
        double factorX = deltaX / distance;
        double factorY = deltaY / distance;
        double dx = factorX * BULLET_SPEED;
        double dy = factorY * BULLET_SPEED;
        double bulletPosX = player.getCentrePosX() + player.getWidth() * factorX;
        double bulletPosY = player.getCentrePosY() + player.getHeight() * factorY;
        bullets.add(new Bullet(bulletPosX, bulletPosY, BULLET_RADIUS, dx, dy, BULLET_HP));
    }

    public int getTimeElapsed() {
        return (int) (milliTimeElapsed / 1000);
    }

    public Player getPlayer() {
        return player;
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