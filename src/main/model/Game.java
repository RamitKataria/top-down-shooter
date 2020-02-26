package model;

import com.google.gson.Gson;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/*
 * Represents the game
 */
public class Game {
    public static final File GAME_SAVE_FILE = new File("./data/gamesave.json");
    public static final File NEW_GAME_FILE = new File("./data/newgame.json");
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    public static int BULLET_SPEED = 10;
    public static int PLAYER_SPEED = 1;

    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private List<Wall> walls;
    private Player player;

    // EFFECTS: constructs a new game with only the player
    public Game() {
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        walls = new ArrayList<>();
        player = new Player(WIDTH / 2, HEIGHT / 2, 0, 0);
    }

    // MODIFIES: this
    // EFFECTS: updates all the moving objects
    public void update() {
        List<List> listsToUpdate = new ArrayList<>();
        listsToUpdate.add(enemies);
        listsToUpdate.add(bullets);
        for (List<MovingObject> lomo : listsToUpdate) {
            for (MovingObject mo : lomo) {
                mo.move();
            }
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

    // EFFECTS: Send the current game data to fileWriter
    public void save(FileWriter fileWriter) {
        new Gson().toJson(this, fileWriter);
    }
}