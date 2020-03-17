package model;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Represents the game
 */
public class Game {
    public static final File GAME_SAVE_FILE = new File("./data/game/gamesave.json");
    public static final File NEW_GAME_FILE = new File("./data/game/newgame.json");
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    public static int BULLET_SPEED = 10;

    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private List<Wall> walls;
    private Player player;
    private int playerVel = 1;
    private boolean isOver;

    // EFFECTS: constructs a new game with only the player
    public Game() {
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        walls = new ArrayList<>();
        player = new Player(WIDTH / 2, HEIGHT / 2, 0, 0);
        isOver = true;
    }


    // MODIFIES: this
    // EFFECTS: updates all the moving objects
    public void update() {
        List<List<MovingObject>> listsToUpdate = new ArrayList<>();
        listsToUpdate.add(enemies.stream().map((enemy -> (MovingObject) enemy)).collect(Collectors.toList()));
        listsToUpdate.add(bullets.stream().map((bullet -> (MovingObject) bullet)).collect(Collectors.toList()));
        for (List<MovingObject> lomo : listsToUpdate) {
            for (MovingObject mo : lomo) {
                mo.move();
            }
        }
        player.move();
    }

    // MODIFIES: this
    // EFFECTS: adds a bullet to the game with appropriate position and speed
    public void fireBullet() {
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

    public int getPlayerVel() {
        return playerVel;
    }

    public boolean isOver() {
        return isOver;
    }

    // EFFECTS: Send the current game data to fileWriter
    public void save(FileWriter fileWriter) {
        new Gson().toJson(this, fileWriter);
    }
}