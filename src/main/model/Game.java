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
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 680;
    public static int BULLET_SPEED = 4;

    private List<Wall> walls;
    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private Player player;
    private long timeElapsed;
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
        timeElapsed = 0;
    }

    // MODIFIES: this
    // EFFECTS: remove all previous game objects and add the new default ones
    public void newGame() {
        enemies.clear();
        walls.clear();
        bullets.clear();
        player = new Player(WIDTH / 2.0, HEIGHT / 2.0, 20, 20, 3);
        enemies.add(new Enemy(200, 180, 20, 20, 1, -1, 50));
        enemies.add(new Enemy(270, 100, 20, 20, -1, 1, 50));
        enemies.add(new Enemy(500, 50, 20, 20, 0, 1, 50));
        walls.add(new Wall(248, 252, 100, 200, 1000));
    }

    // MODIFIES: this
    // EFFECTS: updates all the moving objects and returns true if game is over, otherwise false
    public boolean update(long deltaTime) {
        if (!isPaused) {
            updateMovingObjects(enemies);
            updateMovingObjects(bullets);
            player.update();
            manageCollisions();
            timeElapsed += deltaTime;
            if (player.isDead()) {
                isOver = true;
                return true;
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: manage collisions between bullets and between player and enemies
    private void manageCollisions() {
        bulletCollisions();
        enemyPlayerCollisions();
    }

    // MODIFIES: this
    // EFFECTS: hit the player with all the enemies that intersect it and remove them
    private void enemyPlayerCollisions() {
        List<Enemy> toRemove = new ArrayList<>();
        for (Enemy enemy : enemies) {
            if (player.intersects(enemy)) {
                toRemove.add(enemy);
                player.hit(enemy);
            }
        }
        enemies.removeAll(toRemove);
    }

    // MODIFIES: this
    // EFFECTS: hit all the objects with all the bullets that intersect them and remove those bullets
    private void bulletCollisions() {
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

    // MODIFIES: this, bullet
    // EFFECTS: hit the walls that intersect the bullet and return true if the bullet hit something
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

    // MODIFIES: this, bullet
    // EFFECTS: hit the enemies that intersect the bullet and return true if the bullet hit something
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

    public boolean isOver() {
        return isOver;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    // EFFECTS: Send the current game data to fileWriter
    public void save(FileWriter fileWriter) {
        new Gson().toJson(this, fileWriter);
    }

    // EFFECTS: draw all the objects on gc
    public void draw(GraphicsContext gc) {
        bullets.forEach(gameObject -> gameObject.render(gc));
        enemies.forEach(gameObject -> gameObject.render(gc));
        walls.forEach(gameObject -> gameObject.render(gc));
        player.render(gc);
    }
}