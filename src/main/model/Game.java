package model;

import com.google.gson.Gson;
import persistence.Saveable;

import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/*
 * Represents the game
 */
public class Game implements Saveable {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    public static final int INITIAL_ENEMIES = 2;
    public static int BULLET_SPEED = 10;
    public static int PLAYER_SPEED = 1;

    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private List<Wall> walls;
    private Player player;

    // EFFECTS: constructs a new game and resets everything to default values
    public Game() {
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        walls = new ArrayList<>();
        setUp();
    }

    // EFFECTS: constructs a game with given objects
    // NOTE: this constructor is intended to be used to load game from saved data
    public Game(List<Enemy> enemies, List<Bullet> bullets, List<Wall> walls) {
        this.enemies = enemies;
        this.bullets = bullets;
        this.walls = walls;
    }

    // MODIFIES: this
    // EFFECTS: adds player, initial enemies and walls to the game
    private void setUp() {
        player = new Player(WIDTH / 2, HEIGHT / 2, PLAYER_SPEED, 0);
        for (int i = 0; i < INITIAL_ENEMIES; i++) {
            enemies.add(new Enemy(i + 1, i - 1, -1, +1));
        }
        walls.add(new Wall(WIDTH / 2 - 2, HEIGHT / 2 + 2));
    }

    // MODIFIES: this
    // EFFECTS: updates all the moving objects
    public void update() {
        for (Enemy enemy : enemies) {
            enemy.move();
        }

        for (Bullet bullet : bullets) {
            bullet.move();
        }

        player.move();
    }

    // MODIFIES: this
    // EFFECTS: adds a bullet to the game with appropriate position and speed
    private void fireBullet() {
        bullets.add(new Bullet(player.getPosX(), player.getPosY(), player.getDx() * BULLET_SPEED,
                player.getDy() * BULLET_SPEED));
    }

    public Player getPlayer() {
        return player;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    // MODIFIES: this
    // EFFECTS: fires bullet if the space key is pressed
    // source: space invaders base
    public void handleKey(int keyCode) {
        if (keyCode == KeyEvent.VK_SPACE) {
            fireBullet();
        }
    }

    @Override
    public void save(FileWriter fileWriter) {
        new Gson().toJson(this, fileWriter);
    }
}