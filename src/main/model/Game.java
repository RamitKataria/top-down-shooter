package model;

import com.google.gson.Gson;
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
    public static int BULLET_SPEED = 10;

    private List<Wall> walls;
    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private Player player;
    private int playerVel;
    private boolean isOver;
    private int level;

    // EFFECTS: constructs a new game with only the player
    public Game() {
        player = new Player(WIDTH / 2, HEIGHT / 2, 0, 0);
        walls = new ArrayList<>();
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        isOver = true;
        playerVel = 1;
        level = 1;
    }

    public void testingSetUp() {
        enemies.add(new Enemy(200, -180, 1, -1));
        enemies.add(new Enemy(270, 100, -1, 1));
        walls.add(new Wall(248, 252, 10, 20));
    }

    // MODIFIES: this
    // EFFECTS: updates all the moving objects
    public void update() {
        updateGameObjects(enemies);
        updateGameObjects(walls);
        updateGameObjects(bullets);
        player.move();

    }

    private void updateGameObjects(List<? extends GameObject> gameObjects) {
        List<GameObject> toRemove = new ArrayList<>();
        for (GameObject gameObject : gameObjects) {
            gameObject.move();
            if (gameObject.isDead()) {
                toRemove.add(gameObject);
            }
        }
        gameObjects.removeAll(toRemove);
    }

    // MODIFIES: this
    // EFFECTS: adds a bullet to the game with appropriate position and speed
    public void fireBullet() {
        bullets.add(new Bullet(player.getPosX(), player.getPosY(),
                player.getDx() * BULLET_SPEED, player.getDy() * BULLET_SPEED));
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