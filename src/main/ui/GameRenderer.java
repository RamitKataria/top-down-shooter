package ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import model.Game;

import java.util.Observable;
import java.util.Observer;

public class GameRenderer extends Canvas implements Observer {
    private GraphicsContext gc;
    private Game game;

    public GameRenderer(GraphicsContext gc, Game game) {
        this.gc = gc;
        this.game = game;
    }

    // MODIFIES: this
    // EFFECTS: draw the game on gc
    public void drawGame() {
        gc.clearRect(0, 0, 1080, 680);
        game.render(gc);
    }

    @Override
    public void update(Observable o, Object arg) {
        game = (Game) arg;
    }
}
