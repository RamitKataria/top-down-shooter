package ui;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import java.util.Arrays;
import java.util.HashSet;

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
    @FXML
    private Button resumeButton;
    @FXML
    private Button newGameButton;
    @FXML
    private Button deleteGameButton;
    @FXML
    private Label timeLabel;

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
        scene.setOnKeyPressed(this::handleKeyDown);
        scene.setOnKeyReleased(this::handleKeyUp);

    }

    public void handleResumeButton() {
        try {
            game = Reader.readGame(GAME_SAVE_FILE);
            if (game == null) {
                throw new Exception();
            }
        } catch (Exception e) {
            try {
                game = Reader.readGame(NEW_GAME_FILE);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        runGame();
    }

    public void handleNewGameButton() {
        try {
            game = Reader.readGame(NEW_GAME_FILE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        runGame();
    }

    public void handleDeleteGameButton() {
        boolean success = GAME_SAVE_FILE.delete();
        deleteGameButton.setVisible(false);
        resumeButton.setVisible(false);
    }

    private void handleClose() {
        try {
            new Writer(GAME_SAVE_FILE).write(game);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleKeyDown(KeyEvent event) {
        if (event.getCode().equals(KeyCode.DOWN)) {
            player.setDy(1);
        } else if (event.getCode().equals(KeyCode.UP)) {
            player.setDy(-1);
        } else if (event.getCode().equals(KeyCode.RIGHT)) {
            player.setDx(1);
        } else if (event.getCode().equals(KeyCode.LEFT)) {
            player.setDx(-1);
        } else if (event.getCode().equals(KeyCode.SPACE)) {
            game.fireBullet();
        } else if (event.getCode().equals(KeyCode.BACK_SPACE)) {
            game.getBullets().clear();
        }
    }

    private void handleKeyUp(KeyEvent event) {
        HashSet<KeyCode> stopKeys = new HashSet<>(Arrays.asList(KeyCode.DOWN, KeyCode.UP, KeyCode.LEFT, KeyCode.RIGHT));
        if (stopKeys.contains(event.getCode())) {
            player.setDx(0);
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
                drawGame();
            }
        }.start();
    }

    private void drawGame() {
        gc.clearRect(0, 0, 1080, 680);
        game.draw(gc);
    }
}
