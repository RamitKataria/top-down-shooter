package ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.Game;
import model.gameobjects.*;

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
    public void renderGame() {
        gc.clearRect(0, 0, 1080, 680);
        game.getWalls().forEach(this::renderWall);
        game.getEnemies().forEach(this::renderEnemy);
        game.getBullets().forEach(this::renderBullet);
        renderPlayer(game.getPlayer());
    }

    @Override
    public void update(Observable o, Object arg) {
        game = (Game) arg;
    }

    private void renderPlayer(Player objectToRender) {
        gc.setFill(new Color(92 / 255.0, 237 / 255.0, 237 / 255.0, objectToRender.getHpFraction()));
        gc.fillRect(objectToRender.getPosX(), objectToRender.getPosY(),
                objectToRender.getWidth(), objectToRender.getHeight());
    }

    private void renderEnemy(Enemy objectToRender) {
        if (objectToRender instanceof AutoEnemy) {
            gc.setFill(new Color(0, 0, 0, objectToRender.getHpFraction()));
        } else {
            gc.setFill(new Color(196 / 255.0, 14 / 255.0, 14 / 255.0, objectToRender.getHpFraction()));
        }
        gc.fillRect(objectToRender.getPosX(), objectToRender.getPosY(),
                objectToRender.getWidth(), objectToRender.getHeight());
    }

    private void renderWall(Wall objectToRender) {
        gc.setFill(new Color(0, 0, 0, objectToRender.getHpFraction()));
        gc.fillRect(objectToRender.getPosX(), objectToRender.getPosY(),
                objectToRender.getWidth(), objectToRender.getHeight());
    }

    private void renderBullet(Bullet objectToRender) {
        gc.setFill(new Color(2 / 255.0, 44 / 255.0, 250 / 255.0, objectToRender.getHpFraction()));
        gc.fillOval(objectToRender.getPosX(), objectToRender.getPosY(),
                objectToRender.getWidth(), objectToRender.getHeight());
    }
}
