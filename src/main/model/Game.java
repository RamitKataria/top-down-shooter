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

    private List<GameObject> gameObjects;
    private Player player;
    private int playerVel = 1;
    private boolean isOver;

    // EFFECTS: constructs a new game with only the player
    public Game() {
        gameObjects = new ArrayList<>();
        player = new Player(WIDTH / 2, HEIGHT / 2, 0, 0);
        gameObjects.add(player);
        isOver = true;

        // remove after testing
        gameObjects.add(new Enemy(200, -180, 1, -1));
        gameObjects.add(new Enemy(270, 100, -1, 1));
        gameObjects.add(new Wall(248, 252, 10, 20));
    }

    // MODIFIES: this
    // EFFECTS: updates all the moving objects
    public void update() {
        moveObjects();
        removeDeadObjects();
    }

    private void removeDeadObjects() {
        List<GameObject> objectsToRemove = new ArrayList<>();
        for (GameObject gameObject : gameObjects) {
            if (gameObject.getHealth() <= 0) {
                objectsToRemove.add(gameObject);
            }
        }
        gameObjects.removeAll(objectsToRemove);
    }

    private void moveObjects() {
        for (GameObject gameObject : gameObjects) {
            gameObject.move();
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a bullet to the game with appropriate position and speed
    public void fireBullet() {
        gameObjects.add(new Bullet(player.getPosX(), player.getPosY(), player.getDx() * BULLET_SPEED,
                player.getDy() * BULLET_SPEED));
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isOver() {
        return isOver;
    }

    // EFFECTS: Send the current game data to fileWriter
    public void save(FileWriter fileWriter) {
        new Gson().toJson(this, fileWriter);
    }

    public void draw(GraphicsContext gc) {
        for (GameObject gameObject : gameObjects) {
            gameObject.draw(gc);
        }
    }
}