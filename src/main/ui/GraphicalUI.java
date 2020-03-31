package ui;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.geometry.HorizontalDirection;
import javafx.geometry.Point2D;
import javafx.geometry.VerticalDirection;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Game;
import model.exceptions.GameOverException;
import model.gameobjects.Player;
import persistence.GameReader;
import persistence.Writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Observable;
import java.util.Optional;
import java.util.Set;

import static javafx.scene.input.KeyCode.*;

// represents controller for GameLayout
public class GraphicalUI extends Observable {
    public static final File GAME_SAVE_FILE = new File("./data/GameSave.json");
    private Set<KeyCode> downKeys;
    private Game game;
    private Game savedGame;
    private boolean isGamePaused;
    private long prevTime;
    private GameRenderer gameRenderer;
    private Stage primaryStage;
    private AnimationTimer timer;

    @FXML
    private Canvas canvas;
    @FXML
    private VBox dialog;
    @FXML
    private Button resumeButton;
    @FXML
    private Button loadGameButton;
    @FXML
    private Button saveGameButton;
    @FXML
    private Label timeLabel;
    @FXML
    private Label statusLabel;

    // no-args constructor required by FXML
    public GraphicalUI() {
    }

    // this method is required by FXML
    // MODIFIES: this
    // EFFECTS: TODO
    @FXML
    private void initialize() {
        gameRenderer = new GameRenderer(canvas.getGraphicsContext2D(), game);
        addObserver(gameRenderer);
        downKeys = new HashSet<>();
    }

    // MODIFIES: this
    // EFFECTS: initial set up of windows and event handlers
    public void setUpUI(Stage primaryStage) {
        this.primaryStage = primaryStage;
        Scene scene = primaryStage.getScene();
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            handleClose();
        });
        scene.setOnKeyPressed(e -> {
            downKeys.add(e.getCode());
            handleKey();
        });
        scene.setOnKeyReleased(e -> {
            downKeys.remove(e.getCode());
            handleKey();
        });
        scene.setOnMouseClicked(this::handlePointer);
        showDialog();
    }

    private void handlePointer(MouseEvent mouseEvent) {
        if (game != null) {
            Point2D posInCanvas = canvas.sceneToLocal(mouseEvent.getX(), mouseEvent.getY());
            game.fireBullet(posInCanvas.getX(), posInCanvas.getY());
        }
    }

    // MODIFIES: this
    // EFFECTS: pause the game the set the UI accordingly
    private void managePauseGame() {
        if (isGamePaused) {
            handleResumeButton();
        } else {
            showDialog();
            statusLabel.setText("Game Paused");
        }
    }

    private void showDialog() {
        isGamePaused = true;
        savedGame = getSavedGame();
        manageLoadGameButtonVisibility();
        manageResumeButtonVisibility();
        manageSaveGameButtonVisibility();
        dialog.setVisible(true);
        canvas.setEffect(new GaussianBlur(50));
    }

    private void manageSaveGameButtonVisibility() {
        if (game == null) {
            saveGameButton.setVisible(false);
        } else {
            saveGameButton.setVisible(true);
        }
    }

    private void manageResumeButtonVisibility() {
        if (game == null) {
            resumeButton.setVisible(false);
        } else {
            resumeButton.setVisible(true);
        }
    }

    private void manageLoadGameButtonVisibility() {
        if (savedGame == null) {
            loadGameButton.setVisible(false);
        } else {
            loadGameButton.setVisible(true);
        }
    }

    private void hideDialog() {
        dialog.setVisible(false);
        canvas.setEffect(null);
        isGamePaused = false;
        prevTime = System.nanoTime();
    }

    private Game getSavedGame() {
        try {
            return GameReader.read(GAME_SAVE_FILE);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    // MODIFIES: this
    // EFFECTS: if a game is already running, unpause it, otherwise load game from saved game. If a saved game doesn't
    //          exist, start a new game
    public void handleResumeButton() {
        if (game == null) {
            resumeButton.setVisible(false);
            statusLabel.setText("Error: No game currently running");
            return;
        } else {
            runGame();
        }
        hideDialog();
    }

    public void handleLoadGameButton() {
        game = savedGame;
        savedGame = null;
        hideDialog();
        runGame();
    }

    private void runGame() {
        setChanged();
        notifyObservers(game);
        startTimer();
    }

    // MODIFIES: this
    // EFFECTS: make a new game and set the UI accordingly
    public void handleNewGameButton() {
        game = new Game();
        game.initializeWalls();
        hideDialog();
        runGame();
    }

    // MODIFIES: this
    // EFFECTS: save game and update UI accordingly
    public void handleSaveGameButton() {
        try {
            new Writer(GAME_SAVE_FILE).write(game);
            statusLabel.setText("Game Saved");
        } catch (IOException e) {
            statusLabel.setText("Game save failed!");
        }
    }

    // MODIFIES: this
    // EFFECTS: close the main window
    // source: https://code.makery.ch/blog/javafx-dialogs-official/
    private void handleClose() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirm Exit");
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            primaryStage.close();
        }
    }

    // MODIFIES: this
    // EFFECTS: call appropriate handlers whenever a key is pressed
    private void handleKey() {
        if (game != null) {
            Player player = game.getPlayer();
            manageVerticalMovement(player);
            manageHorizontalMovement(player);
        }
        if (downKeys.contains(ESCAPE)) {
            managePauseGame();
        }
    }

    private void manageHorizontalMovement(Player player) {
        if (downKeys.contains(D)) {
            if (downKeys.contains(A)) {
                player.setHorizontalMovingDirection(null);
            } else {
                player.setHorizontalMovingDirection(HorizontalDirection.RIGHT);
            }
        } else if (downKeys.contains(A)) {
            player.setHorizontalMovingDirection(HorizontalDirection.LEFT);
        } else {
            player.setHorizontalMovingDirection(null);
        }
    }

    private void manageVerticalMovement(Player player) {
        if (downKeys.contains(S)) {
            if (downKeys.contains(W)) {
                player.setVerticalMovingDirection(null);
            } else {
                player.setVerticalMovingDirection(VerticalDirection.DOWN);
            }
        } else if (downKeys.contains(W)) {
            player.setVerticalMovingDirection(VerticalDirection.UP);
        } else {
            player.setVerticalMovingDirection(null);
        }
    }

    // MODIFIES: this
    // EFFECTS: change UI after game is over
    private void handleGameOver() {
        timer.stop();
        game = null;
        setChanged();
        notifyObservers();
        statusLabel.setText("Game Over");
        showDialog();
    }

    // MODIFIES: this
    // EFFECTS: add the animation timer
    private void startTimer() {
        if (timer == null) {
            timer = new AnimationTimer() {
                public void handle(long currentNanoTime) {
                    if (!isGamePaused) {
                        updateTimeLabel();
                        try {
                            game.update((int) ((currentNanoTime - prevTime) / 1000000));
                            gameRenderer.renderGame();
                        } catch (GameOverException e) {
                            handleGameOver();
                        }
                        prevTime = currentNanoTime;
                    }
                }
            };
        }
        timer.start();
    }

    // MODIFIES: this
    // EFFECTS: update the time label to reflect current time elapsed
    private void updateTimeLabel() {
        timeLabel.setText("Time Elapsed: " + game.getTimeElapsed() + "s");
    }
}
