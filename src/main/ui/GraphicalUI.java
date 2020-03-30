package ui;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HorizontalDirection;
import javafx.geometry.VerticalDirection;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Game;
import model.exceptions.GameOverException;
import model.gameobjects.Player;
import persistence.GameReader;
import persistence.Writer;
import ui.confirmbox.ConfirmBox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Observable;

// represents controller for GameLayout
public class GraphicalUI extends Observable {
    public static final File GAME_SAVE_FILE = new File("./data/GameSave.json");
    private Game game;
    private Game savedGame;
    private boolean isGamePaused;
    private long prevTime;
    private GameRenderer gameRenderer;

    private GraphicsContext gc;
    private Stage primaryStage;
    private Stage confirmStage;
    private ConfirmBox confirmBox;
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
    }

    // MODIFIES: this
    // EFFECTS: initial set up of windows and event handlers
    public void setUpUI(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        Scene scene = primaryStage.getScene();
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            handleClose();
        });
        scene.setOnKeyPressed(this::handleKeyDown);
        scene.setOnKeyReleased(this::handleKeyUp);
        setUpConfirmBox();

        showDialog();
    }

    // MODIFIES: this
    // EFFECTS: initial set up of confirm box
    private void setUpConfirmBox() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("confirmbox/ConfirmBox.fxml"));
        Parent root = loader.load();
        confirmStage = new Stage();
        confirmStage.initModality(Modality.APPLICATION_MODAL);
        confirmStage.setMinWidth(250);
        confirmStage.setTitle("Sure?");
        confirmStage.setScene(new Scene(root));
        confirmBox = loader.getController();
    }

    // MODIFIES: this
    // EFFECTS: pause the game the set the UI accordingly
    private void managePauseGame() {
        if (isGamePaused) {
            handleResumeButton();
        } else {
            isGamePaused = true;
            showDialog();
            statusLabel.setText("Game Paused");
        }
    }

    private void showDialog() {
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
        isGamePaused = false;
        setChanged();
        notifyObservers(game);
        startTimer();
    }

    // MODIFIES: this
    // EFFECTS: make a new game and set the UI accordingly
    public void handleNewGameButton() {
        game = new Game();
        game.initializeObjects();
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
    private void handleClose() {
        if (confirmBox.display(confirmStage)) {
            primaryStage.close();
        }
    }

    // MODIFIES: this
    // EFFECTS: call appropriate handlers whenever a key is pressed
    private void handleKeyDown(KeyEvent event) {
        if (game != null) {
            Player player = game.getPlayer();
            if (event.getCode().equals(KeyCode.DOWN) || event.getCode().equals(KeyCode.S)) {
                player.setVerticalMovingDirection(VerticalDirection.DOWN);
            } else if (event.getCode().equals(KeyCode.UP) || event.getCode().equals(KeyCode.W)) {
                player.setVerticalMovingDirection(VerticalDirection.UP);
            } else if (event.getCode().equals(KeyCode.RIGHT) || event.getCode().equals(KeyCode.D)) {
                player.setHorizontalMovingDirection(HorizontalDirection.RIGHT);
            } else if (event.getCode().equals(KeyCode.LEFT) || event.getCode().equals(KeyCode.A)) {
                player.setHorizontalMovingDirection(HorizontalDirection.LEFT);
            } else if (event.getCode().equals(KeyCode.SPACE)) {
                game.fireBullet();
            } else if (event.getCode().equals(KeyCode.BACK_SPACE)) {
                game.getBullets().clear();
            } else if (event.getCode().equals(KeyCode.ESCAPE)) {
                managePauseGame();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: call appropriate methods whenever a key is unpressed
    private void handleKeyUp(KeyEvent event) {
        if (game != null) {
            Player player = game.getPlayer();
            if (event.getCode().equals(KeyCode.DOWN) || event.getCode().equals(KeyCode.S)
                    || event.getCode().equals(KeyCode.UP) || event.getCode().equals(KeyCode.W)) {
                player.setVerticalMovingDirection(null);
            } else if (event.getCode().equals(KeyCode.RIGHT) || event.getCode().equals(KeyCode.D)
                    || event.getCode().equals(KeyCode.LEFT) || event.getCode().equals(KeyCode.A)) {
                player.setHorizontalMovingDirection(null);
            }
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
            prevTime = System.nanoTime();
            timer = new AnimationTimer() {
                public void handle(long currentNanoTime) {
                    if (!isGamePaused) {
                        updateTimeLabel();
                        try {
                            game.update(currentNanoTime - prevTime);
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
        timeLabel.setText("Time Elapsed: " + (game.getTimeElapsed() / 1000000000) + "s");
    }
}
