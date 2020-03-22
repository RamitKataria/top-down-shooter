package model;

import com.google.gson.Gson;
import javafx.geometry.HorizontalDirection;
import javafx.geometry.VerticalDirection;
import javafx.scene.canvas.GraphicsContext;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * Represents the game
 */
public class Game {
    public static final File GAME_SAVE_FILE = new File("./data/game/gamesave.json");
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 680;
    public static int BULLET_SPEED = 4;
    public static final Random RND = new Random();

    private List<Wall> walls;
    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private Player player;
    private long timeRemaining;
    private boolean isOver;
    private boolean isPaused;

    // EFFECTS: constructs a new game with only the player
    public Game() {
        player = new Player(WIDTH / 2.0, HEIGHT / 2.0, 20, 20, 3);
        walls = new ArrayList<>();
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        isOver = false;
        isPaused = false;
        timeRemaining = 60000000000L;
    }

    public void newGame() {
        enemies.add(new Enemy(200, -180, 20, 20, 1, -1, 10));
        enemies.add(new Enemy(270, 100, 20, 20, -1, 1, 10));
        walls.add(new Wall(248, 252, 100, 200, 1000));
    }

    // MODIFIES: this
    // EFFECTS: updates all the moving objects
    public boolean update(long deltaTime) {
        if (!isPaused) {
            updateGameObjects(enemies);
            updateGameObjects(walls);
            updateGameObjects(bullets);
            player.update();
            if (RND.nextInt(1000) < 1) {
                generateEnemies();
            }
            manageCollisions();
            generateEnemies();
            timeRemaining -= deltaTime;
            if (player.isDead()) {
                isOver = true;
                return true;
            }
        }
        return false;
    }

    private void generateEnemies() {
        enemies.add(new Enemy(RND.nextDouble() * WIDTH, RND.nextDouble() * HEIGHT, 20, 20,
                RND.nextInt(2) - 1, RND.nextInt(2) - 1, 10));
    }

    private void manageCollisions() {
        List<Bullet> toRemove = new ArrayList<>();
        for (Bullet bullet : bullets) {
            boolean hit = isHitEnemies(bullet, false);
            hit = isHitWalls(bullet, hit);

            if (!hit && player.intersects(bullet)) {
                player.hit(bullet);
                hit = true;
            }
            if (hit) {
                toRemove.add(bullet);
            }
        }
        bullets.removeAll(toRemove);
    }

    private boolean isHitWalls(Bullet bullet, boolean hit) {
        if (!hit) {
            for (Wall wall : walls) {
                if (bullet.intersects(wall)) {
                    wall.hit(bullet);
                    break;
                }
            }
        }
        return hit;
    }

    private boolean isHitEnemies(Bullet bullet, boolean hit) {
        for (Enemy enemy : enemies) {
            if (bullet.intersects(enemy)) {
                enemy.hit(bullet);
                hit = true;
                break;
            }
        }
        return hit;
    }

    private void updateGameObjects(List<? extends GameObject> gameObjects) {
        List<GameObject> toRemove = new ArrayList<>();
        for (GameObject gameObject : gameObjects) {
            gameObject.update();
            if (gameObject.isDead()) {
                toRemove.add(gameObject);
            }
        }
        gameObjects.removeAll(toRemove);
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

        bullets.add(new Bullet(bulletPosX, bulletPosY, 5, 5, bulletDx, bulletDy, 50));
    }

    public Player getPlayer() {
        return player;
    }

    public long getTimeRemaining() {
        return timeRemaining;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public boolean isOver() {
        return isOver;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean value) {
        isPaused = value;
    }

    // EFFECTS: Send the current game data to fileWriter
    public void save(FileWriter fileWriter) {
        new Gson().toJson(this, fileWriter);
    }

    public void draw(GraphicsContext gc) {
        bullets.forEach(gameObject -> gameObject.render(gc));
        enemies.forEach(gameObject -> gameObject.render(gc));
        walls.forEach(gameObject -> gameObject.render(gc));
        player.render(gc);
    }
}