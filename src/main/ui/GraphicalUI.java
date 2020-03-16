package ui;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Game;
import model.Player;
import persistence.Reader;
import persistence.Writer;

import java.io.FileNotFoundException;
import java.io.IOException;

import static model.Game.GAME_SAVE_FILE;
import static model.Game.NEW_GAME_FILE;

public class GraphicalUI {
    Game game;
    Player player;

    GraphicsContext gc;
    Stage primaryStage;
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

    public void setUp(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.scene = primaryStage.getScene();
        primaryStage.setOnCloseRequest(event -> handleClose());
        scene.setOnKeyPressed(this::handleKey);
    }

    public void handleResumeButton() {
        try {
            game = Reader.readGame(GAME_SAVE_FILE);
        } catch (FileNotFoundException e) {
            //TODO
        }
        runGame();
    }

    public void handleNewGameButton() {
        try {
            game = Reader.readGame(NEW_GAME_FILE);
        } catch (FileNotFoundException e) {
            // TODO
        }
        runGame();
    }

    private void handleClose() {
        try {
            new Writer(GAME_SAVE_FILE).write(game);
        } catch (IOException e) {
            // TODO
        }
    }

    private void handleKey(KeyEvent event) {
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
    }

    private void runGame() {
        dialog.setVisible(false);
        player = game.getPlayer();
        addTimer();
    }

    private void addTimer() {
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
