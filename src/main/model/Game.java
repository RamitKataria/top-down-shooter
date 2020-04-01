package model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import model.exceptions.GameOverException;
import model.exceptions.OutOfDomainException;
import model.gameobjects.*;
import persistence.Saveable;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

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
    public static final double AUTO_ENEMY_LENGTH = 30;
    public static final double BULLET_RADIUS = 5;
    private static final double REGULAR_ENEMY_SPEED = 2;
    public static final double PLAYER_LENGTH = 20;
    public static final double WALL_DIM_1 = 200;
    public static final double WALL_DIM_2 = 25;
    public static final double PLAYER_MAX_HP = 100;
    public static final double WALL_HP = 200;
    public static final double AUTO_ENEMY_HP = 70;
    public static final double REGULAR_ENEMY_HP = 25;
    public static final double BULLET_HP = 20;
    public static final int MAX_WALLS = 10;
    public static final int MAX_ENEMIES = 25;
    public static final double PLAYER_HEALTH_REGENERATION_RATE = 0.001;
    public static final int ENEMY_GENERATION_PERIOD = 240;
    public static final double REGULAR_ENEMY_FACTOR = 8;
    public static final int[] ARRAY_FOR_RANDOM_SIGN = {-1, 0, 1};
    public static final Random RND = new Random();

    private List<Wall> walls;
    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private Player player;
    private long milliTimeElapsed;

    @Expose(serialize = false, deserialize = false)
    private List<CollisionPair> collisionCheckedPairs;

    // EFFECTS: constructs a new game with a player and MAX_WALLS walls
    public Game() {
        player = new Player(WIDTH / 2.0, HEIGHT / 2.0, PLAYER_LENGTH, PLAYER_SPEED, PLAYER_MAX_HP);
        walls = new ArrayList<>();
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        milliTimeElapsed = 0;
        collisionCheckedPairs = new ArrayList<>();
        generateWalls();
    }

    // MODIFIES: this
    // EFFECTS: generates regular and auto-enemies randomly based on ENEMY_GENERATION_PERIOD and REGULAR_ENEMY_FACTOR
    private void generateEnemies() {
        int randomNumber = RND.nextInt(ENEMY_GENERATION_PERIOD);
        try {
            if (randomNumber < 1) {
                addAutoEnemy(randomNumber);
            } else if (randomNumber < REGULAR_ENEMY_FACTOR) {
                addRegularEnemy(randomNumber);
            }
        } catch (OutOfDomainException e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: add a regular enemy based on the given random number
    private void addRegularEnemy(int randomNumber) throws OutOfDomainException {
        if (randomNumber >= REGULAR_ENEMY_FACTOR) {
            throw new OutOfDomainException();
        }

        addEnemyAndRemoveOld(new Enemy((WIDTH / REGULAR_ENEMY_FACTOR) * randomNumber,
                (HEIGHT / REGULAR_ENEMY_FACTOR) * (REGULAR_ENEMY_FACTOR - randomNumber),
                PLAYER_LENGTH, randomVelocityForRegularEnemies(),
                randomVelocityForRegularEnemies(), REGULAR_ENEMY_HP));
    }

    // EFFECTS: return one of -REGULAR_ENEMY_SPEED, 0, or REGULAR_ENEMY_SPEED randomly
    private double randomVelocityForRegularEnemies() {
        return ARRAY_FOR_RANDOM_SIGN[RND.nextInt(3)] * REGULAR_ENEMY_SPEED;
    }

    // MODIFIES: this
    // EFFECTS: add a new auto-enemy based on the given number
    private void addAutoEnemy(int randomNumber) throws OutOfDomainException {
        if (randomNumber >= 4) {
            throw new OutOfDomainException();
        }
        addEnemyAndRemoveOld(new Enemy(WIDTH * RND.nextInt(2), HEIGHT * RND.nextInt(2),
                AUTO_ENEMY_SPEED, AUTO_ENEMY_LENGTH, AUTO_ENEMY_HP, player));
    }

    // MODIFIES: this
    // EFFECTS: if the given enemy doesn't intersect the player, add it to the game; and if this results in more than
    //          MAX_ENEMIES enemies, then remove the oldest one
    private void addEnemyAndRemoveOld(Enemy e) {
        if (!e.intersects(player)) {
            enemies.add(e);
            if (enemies.size() > MAX_ENEMIES) {
                enemies.remove(0);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: if there is more than 1 wall missing (with max = MAX_WALLS), randomly add 2 walls
    private void generateWalls() {
        while (walls.size() < MAX_WALLS - 1) {
            walls.add(new Wall(RND.nextInt(WIDTH), RND.nextInt(HEIGHT), WALL_DIM_2, WALL_DIM_1, WALL_HP));
            walls.add(new Wall(RND.nextInt(WIDTH), RND.nextInt(HEIGHT), WALL_DIM_1, WALL_DIM_2, WALL_HP));
        }
    }

    // MODIFIES: this
    // EFFECTS: update the game which includes updating the objects, removing the dead ones, checking for collisions,
    //          generating new enemies, adding delta time
    //          throw GameOverException if the player is dead
    public void update(int milliDeltaTime) throws GameOverException {
        updateMovingObjects(enemies);
        updateMovingObjects(bullets);
        removeDeadWalls();
        player.update();
        checkCollisions();
        collisionCheckedPairs.forEach(CollisionPair::executeCollision);
        generateEnemies();
        player.regenerateHP(PLAYER_HEALTH_REGENERATION_RATE);
        milliTimeElapsed += milliDeltaTime;
        if (player.isDead()) {
            throw new GameOverException();
        }
    }

    // MODIFIES: this
    // EFFECTS: remove all the walls that are dead and add 2 new walls if there is more than 1 wall missing
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

    // MODIFIES: this
    // EFFECTS: if not already there, add a collision pair of the two objects if they intersect
    public void checkCollision(GameObject first, GameObject second) {
        CollisionPair collisionPair = new CollisionPair(first, second);
        if (first.intersects(second) && !collisionCheckedPairs.contains(collisionPair)) {
            collisionCheckedPairs.add(collisionPair);
        }
    }

    // MODIFIES: this
    // EFFECTS: check for all relevant collisions
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
    // EFFECTS: adds a bullet to the game that goes in the direction of the given point coords
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

    // EFFECTS: return the time elapsed in milliseconds
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

    // EFFECTS: send this to the fileWriter to save
    @Override
    public void save(FileWriter fileWriter) {
        new Gson().toJson(this, fileWriter);
    }
}