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
        gc.setFill(rgbToColor(92, 237, 237, objectToRender.getHpFraction()));
        gc.fillRect(objectToRender.getPosX(), objectToRender.getPosY(),
                objectToRender.getWidth(), objectToRender.getHeight());
    }

    private void renderEnemy(Enemy objectToRender) {
        if (objectToRender instanceof AutoEnemy) {
            gc.setFill(rgbToColor(196, 14, 14, objectToRender.getHpFraction()));
        } else {
            gc.setFill(rgbToColor(10, 140, 77, objectToRender.getHpFraction()));
        }
        gc.fillRect(objectToRender.getPosX(), objectToRender.getPosY(),
                objectToRender.getWidth(), objectToRender.getHeight());
    }

    private void renderWall(Wall objectToRender) {
        double posX = objectToRender.getPosX();
        double posY = objectToRender.getPosY();
        double width = objectToRender.getWidth();
        double height = objectToRender.getHeight();
        gc.setFill(new Color(0, 0, 0, objectToRender.getHpFraction()));
        gc.fillRect(posX, posY, width, height);
        gc.setFill(rgbToColor(125, 7, 29, 1));
        gc.fillRect(posX + width - 2, posY + height - 2, 4, 4);
    }

    private void renderBullet(Bullet objectToRender) {
        gc.setFill(rgbToColor(2, 44, 250, objectToRender.getHpFraction()));
        gc.fillOval(objectToRender.getPosX(), objectToRender.getPosY(),
                objectToRender.getWidth(), objectToRender.getHeight());
    }

    private Color rgbToColor(int r, int g, int b, double opacity) {
        return new Color(r / 255.0, g / 255.0, b / 255.0, opacity);
    }
}
