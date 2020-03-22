package ui;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.geometry.HorizontalDirection;
import javafx.geometry.VerticalDirection;
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

import java.io.IOException;

import static model.Game.GAME_SAVE_FILE;

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
            game = new Game();
            game.newGame();
            /*
            try {
                game = Reader.readGame(NEW_GAME_FILE);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }*/
        }
        runGame();
    }

    public void handleNewGameButton() {
        game = new Game();
        game.newGame();
        /*try {
            game = Reader.readGame(NEW_GAME_FILE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
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
        if (event.getCode().equals(KeyCode.DOWN) || event.getCode().equals(KeyCode.S)) {
            player.setVerticalDirection(VerticalDirection.DOWN);
        } else if (event.getCode().equals(KeyCode.UP) || event.getCode().equals(KeyCode.W)) {
            player.setVerticalDirection(VerticalDirection.UP);
        } else if (event.getCode().equals(KeyCode.RIGHT) || event.getCode().equals(KeyCode.D)) {
            player.setHorizontalDirection(HorizontalDirection.RIGHT);
        } else if (event.getCode().equals(KeyCode.LEFT) || event.getCode().equals(KeyCode.A)) {
            player.setHorizontalDirection(HorizontalDirection.LEFT);
        } else if (event.getCode().equals(KeyCode.SPACE)) {
            game.fireBullet();
        } else if (event.getCode().equals(KeyCode.BACK_SPACE)) {
            game.getBullets().clear();
        }
    }

    private void handleKeyUp(KeyEvent event) {
        if ((event.getCode().equals(KeyCode.DOWN) || event.getCode().equals(KeyCode.S))
                || (event.getCode().equals(KeyCode.UP) || event.getCode().equals(KeyCode.W))) {
            player.setVerticalDirection(null);
        } else if ((event.getCode().equals(KeyCode.RIGHT) || event.getCode().equals(KeyCode.D))
                || (event.getCode().equals(KeyCode.LEFT) || event.getCode().equals(KeyCode.A))) {
            player.setHorizontalDirection(null);
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
