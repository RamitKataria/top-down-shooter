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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Game;
import model.Player;
import model.exceptions.GameOverException;
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

    // this method is called by JavaFX after the @FXML fields are populated
    // MODIFIES: this
    // EFFECTS: create a game renderer for the game and initialize
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

    // MODIFIES: this
    // EFFECTS: fire a bullet in the direction of mouse click
    private void handlePointer(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && game != null) {
            Point2D posInCanvas = canvas.sceneToLocal(mouseEvent.getX(), mouseEvent.getY());
            game.fireBullet(posInCanvas.getX(), posInCanvas.getY());
        }
    }

    // MODIFIES: this
    // EFFECTS: (un)pause the game the set the UI accordingly
    private void managePauseGame() {
        if (isGamePaused) {
            handleResumeButton();
        } else {
            showDialog();
            statusLabel.setText("Game Paused");
        }
    }

    // MODIFIES: this
    // EFFECTS: pause the game and show the game stopped dialog
    private void showDialog() {
        isGamePaused = true;
        savedGame = getSavedGame();
        manageLoadGameButtonVisibility();
        manageSaveAndResumeGameButtonVisibility();
        dialog.setVisible(true);
        canvas.setEffect(new GaussianBlur(50));
    }

    // MODIFIES: this
    // EFFECTS: manage the visibility of the save game button and resume game button so that they appear if and only if
    //          there exists a running game
    private void manageSaveAndResumeGameButtonVisibility() {
        if (game == null) {
            saveGameButton.setVisible(false);
            resumeButton.setVisible(false);
        } else {
            saveGameButton.setVisible(true);
            resumeButton.setVisible(true);
        }
    }

    // MODIFIES: this
    // EFFECTS: manage the visibility of the load game button so that it appears if and only if there exists a running
    //          game
    private void manageLoadGameButtonVisibility() {
        if (savedGame == null) {
            loadGameButton.setVisible(false);
        } else {
            loadGameButton.setVisible(true);
        }
    }

    // MODIFIES: this
    // EFFECTS: hide the stopped game dialog and unpause the game
    private void hideDialog() {
        dialog.setVisible(false);
        canvas.setEffect(null);
        isGamePaused = false;
        prevTime = System.nanoTime();
    }

    // MODIFIES: this
    // EFFECTS: return game saved in the save file; if it doesn't exist, then return null
    private Game getSavedGame() {
        try {
            return GameReader.read(GAME_SAVE_FILE);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    // MODIFIES: this
    // EFFECTS: if game is over, show the close game confirmation; otherwise hide the dialog and unpause the game
    public void handleResumeButton() {
        if (game == null) {
            handleClose();
        } else {
            hideDialog();
        }
    }

    // MODIFIES: this
    // EFFECTS: start the saved game
    public void handleLoadGameButton() {
        game = savedGame;
        savedGame = null;
        hideDialog();
        startRunningGame();
    }

    // MODIFIES: this
    // EFFECTS: start rendering and updating the current game
    private void startRunningGame() {
        setChanged();
        notifyObservers(game);
        startTimer();
    }

    // MODIFIES: this
    // EFFECTS: make a new game and set the UI accordingly
    public void handleNewGameButton() {
        game = new Game();
        hideDialog();
        startRunningGame();
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
    // EFFECTS: close the main window with a confirmation
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
    // EFFECTS: change player movement whenever a movement key is pressed or released; pause the game whenever ESCAPE is
    //          pressed and remove all the bullets whenever backspace is pressed
    private void handleKey() {
        if (game != null) {
            Player player = game.getPlayer();
            manageVerticalMovement(player);
            manageHorizontalMovement(player);
            if (downKeys.contains(BACK_SPACE)) {
                game.getBullets().clear();
            }
        }
        if (downKeys.contains(ESCAPE)) {
            managePauseGame();
        }
    }

    // MODIFIES: this
    // EFFECTS: manage the horizontal movement of player
    private void manageHorizontalMovement(Player player) {
        if (downKeys.contains(D)) {
            if (downKeys.contains(A)) {
                player.setHorizontalDirection(null);
            } else {
                player.setHorizontalDirection(HorizontalDirection.RIGHT);
            }
        } else if (downKeys.contains(A)) {
            player.setHorizontalDirection(HorizontalDirection.LEFT);
        } else {
            player.setHorizontalDirection(null);
        }
    }

    // MODIFIES: this
    // EFFECTS: manage the vertical movement of player
    private void manageVerticalMovement(Player player) {
        if (downKeys.contains(S)) {
            if (downKeys.contains(W)) {
                player.setVerticalDirection(null);
            } else {
                player.setVerticalDirection(VerticalDirection.DOWN);
            }
        } else if (downKeys.contains(W)) {
            player.setVerticalDirection(VerticalDirection.UP);
        } else {
            player.setVerticalDirection(null);
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
    // EFFECTS: add the animation and update timer if it doesn't already exist; start it regardless
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
