package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;
import java.util.List;

import static model.Game.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class GameTest {
    private Game game;
    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private List<Wall> walls;
    private Player player;

    @BeforeEach
    public void runBeforeEach() {
        game = new Game();
        player = game.getPlayer();
        enemies = game.getEnemies();
    }

    @Test
    public void testConstructor() {
        assertEquals(Game.WIDTH / 2, player.getPosX());
        assertEquals(Game.HEIGHT / 2, player.getPosY());

        assertEquals(INITIAL_ENEMIES + 1, enemies.size());
        assertEquals(Game.WIDTH / 2, player.getPosX());
        assertEquals(Game.HEIGHT / 2, player.getPosY());
        assertEquals(player, enemies.remove(0));

        for (GameObject go : enemies) {
            assertFalse(enemies.contains(player));
        }
        for (int i = 0; i < INITIAL_ENEMIES; i++) {
            assertEquals(i + 1, enemies.get(i).posX);
            assertEquals(i - 1, enemies.get(i).posY);
        }

        assertEquals(1, game.getWalls().size());
        assertEquals(Game.WIDTH / 2 - 2, game.getWalls().get(0).getPosX());
        assertEquals(Game.WIDTH / 2 + 2, game.getWalls().get(0).getPosY());
    }

    @Test
    public void testUpdate() {
        game.handleKey(KeyEvent.VK_SPACE);
        game.update();
        assertEquals(Game.WIDTH / 2 + PLAYER_SPEED, player.getPosX());
        assertEquals(Game.HEIGHT / 2 - PLAYER_SPEED, player.getPosY());
        assertEquals(Game.WIDTH / 2 + player.getDx() * BULLET_SPEED,
                enemies.get(enemies.size() - 1).getPosX());
        assertEquals(Game.HEIGHT / 2 + player.getDy() * BULLET_SPEED,
                enemies.get(enemies.size() - 1).getPosY());

        for (int i = 0; i < INITIAL_ENEMIES; i++) {
            assertEquals(i, enemies.get(i + 1).posX);
            assertEquals(i, enemies.get(i + 1).posX);
        }

        assertEquals(1, game.getWalls().size());
        assertEquals(Game.WIDTH / 2 - 2, game.getWalls().get(0).getPosX());
        assertEquals(Game.WIDTH / 2 + 2, game.getWalls().get(0).getPosY());
    }

    @Test
    public void testKeyHandlerInvalid() {
        game.handleKey(KeyEvent.BUTTON1_DOWN_MASK);
        game.handleKey(KeyEvent.VK_S);
        testConstructor();
    }

    @Test
    public void testKeyHandlerFire() {
        game.handleKey(KeyEvent.VK_SPACE);
        assertEquals(INITIAL_ENEMIES + 2, enemies.size());
        assertEquals(Game.WIDTH / 2, enemies.get(enemies.size() - 1).getPosX());
        assertEquals(Game.HEIGHT / 2, enemies.get(enemies.size() - 1).getPosY());
    }

    @Test
    public void testSetVelocity() {
        player.setDx(5);
        player.setDy(-4);
        assertEquals(5, player.getDx());
        assertEquals(-4, player.getDy());
    }
}