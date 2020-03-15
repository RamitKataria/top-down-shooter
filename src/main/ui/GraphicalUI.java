package ui;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import model.Game;
import model.Player;
import persistence.Reader;

import java.io.FileNotFoundException;

import static model.Game.GAME_SAVE_FILE;
import static model.Game.NEW_GAME_FILE;

public class GraphicalUI {
    Game game;
    Player player;

    GraphicsContext gc;
    Scene scene;

    @FXML
    private Canvas canvas;

    @FXML
    private VBox dialog;

    public GraphicalUI() {
    }

    @FXML
    private void initialize() {
        gc = canvas.getGraphicsContext2D();
    }

    private void setUp() {
        scene = canvas.getScene();
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.DOWN)) {
                player.setDx(0);
                player.setDy(1);
            } else if (event.getCode().equals(KeyCode.UP)) {
                player.setDx(0);
                player.setDy(-1);
            } else if (event.getCode().equals(KeyCode.RIGHT)) {
                player.setDx(1);
                player.setDy(0);
            } else if (event.getCode().equals(KeyCode.LEFT)) {
                player.setDx(-1);
                player.setDy(0);
            }
        });
        dialog.setVisible(false);
        player = game.getPlayer();
        runGame();
    }

    public void handleResumeButton() {
        try {
            game = Reader.readGame(GAME_SAVE_FILE);
        } catch (FileNotFoundException e) {
            //TODO
        }
        setUp();
    }

    public void handleNewGameButton() {
        try {
            game = Reader.readGame(NEW_GAME_FILE);
        } catch (FileNotFoundException e) {
            // TODO
        }
        setUp();
    }

    private void runGame() {
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                game.update();
                draw();
            }
        }.start();
    }

    private void draw() {
        gc.clearRect(0, 0, 1080, 680);
        gc.fillOval(player.getPosX(), player.getPosY(), 20, 20);
    }


}
