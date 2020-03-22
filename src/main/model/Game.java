package model;

import com.google.gson.Gson;
import javafx.geometry.HorizontalDirection;
import javafx.geometry.VerticalDirection;
import javafx.scene.canvas.GraphicsContext;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/*
 * Represents the game
 */
public class Game {
    public static final File GAME_SAVE_FILE = new File("./data/game/gamesave.json");
    public static final File NEW_GAME_FILE = new File("./data/game/newgame.json");
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 680;
    public static int BULLET_SPEED = 5;

    private List<Wall> walls;
    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private Player player;
    private boolean isOver;
    private int level;

    // EFFECTS: constructs a new game with only the player
    public Game() {
        player = new Player(WIDTH / 2.0, HEIGHT / 2.0, 20, 20, 3);
        walls = new ArrayList<>();
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        isOver = true;
        level = 1;
    }

    public void newGame() {
        enemies.add(new Enemy(200, -180, 20, 20, 1, -1, 10));
        enemies.add(new Enemy(270, 100, 20, 20, -1, 1, 10));
        walls.add(new Wall(248, 252, 100, 200, 1000));
    }

    // MODIFIES: this
    // EFFECTS: updates all the moving objects
    public void update() {
        updateGameObjects(enemies);
        updateGameObjects(walls);
        updateGameObjects(bullets);
        player.update();
        manageCollisions();
    }

    private void manageCollisions() {
        List<Bullet> toRemove = new ArrayList<>();
        for (Bullet bullet : bullets) {
            boolean hit = false;
            hit = isHitEnemies(bullet, hit);
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
        double bulletPosX = player.getPosX();
        double bulletPosY = player.getPosY();
        double bulletDx = player.getDx();
        double bulletDy = player.getDy();
        if (player.getHorizontalDirection() == HorizontalDirection.RIGHT) {
            bulletPosX += player.getWidth();
            bulletDx += BULLET_SPEED;
        } else if (player.getHorizontalDirection() == HorizontalDirection.LEFT) {
            bulletPosX -= player.getWidth();
            bulletDx -= BULLET_SPEED;
        }
        if (player.getVerticalDirection() == VerticalDirection.DOWN) {
            bulletPosY += player.getHeight();
            bulletDy += BULLET_SPEED;
        } else if (player.getVerticalDirection() == VerticalDirection.UP) {
            bulletPosY -= player.getHeight();
            bulletDy -= BULLET_SPEED;
        }

        bullets.add(new Bullet(bulletPosX, bulletPosY, 5, 5, bulletDx, bulletDy, 50));
    }

    public Player getPlayer() {
        return player;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public boolean isOver() {
        return isOver;
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