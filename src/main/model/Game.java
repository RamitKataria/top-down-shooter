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

    private List<MovingObject> movingObjects;
    private List<GameObject> walls;
    private Player player;

    // EFFECTS: constructs a new game and resets everything to default values
    public Game() {
        movingObjects = new ArrayList<>();
        walls = new ArrayList<>();
        setUp();
    }

    // EFFECTS: constructs a game with given objects
    // NOTE: this constructor is intended to be used to load game from saved data
    public Game(List<MovingObject> movingObjects, List<GameObject> walls) {
        this.movingObjects = movingObjects;
        this.walls = walls;
    }

    // MODIFIES: this
    // EFFECTS: adds player, initial enemies and walls to the game
    private void setUp() {
        player = new Player(WIDTH / 2, HEIGHT / 2, PLAYER_SPEED, -PLAYER_SPEED);
        movingObjects.add(player);
        for (int i = 0; i < INITIAL_ENEMIES; i++) {
            movingObjects.add(new Enemy(i + 1, i - 1, -1, +1));
        }
        walls.add(new Wall(WIDTH / 2 - 2, HEIGHT / 2 + 2));
    }

    // MODIFIES: this
    // EFFECTS: updates all the moving objects
    public void update() {
        for (MovingObject movingObject : movingObjects) {
            movingObject.move();
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a bullet to the game with appropriate position and speed
    private void fireBullet() {
        movingObjects.add(new Bullet(player.getPosX(), player.getPosY(), player.getDx() * BULLET_SPEED,
                player.getDy() * BULLET_SPEED));
    }

    public Player getPlayer() {
        return player;
    }

    public List<MovingObject> getMovingObjects() {
        return movingObjects;
    }

    public List<GameObject> getWalls() {
        return walls;
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
        List<Object> toSave = new ArrayList<>();
        toSave.add(movingObjects);
        toSave.add(walls);
        Gson gson = new Gson();
        gson.toJson(toSave, fileWriter);
    }
}