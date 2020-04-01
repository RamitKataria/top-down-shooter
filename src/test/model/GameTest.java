package model;

import model.exceptions.GameOverException;
import model.gameobjects.Bullet;
import model.gameobjects.Enemy;
import model.gameobjects.Player;
import model.gameobjects.Wall;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static model.Game.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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
        walls = game.getWalls();
        bullets = game.getBullets();
    }

    @Test
    public void testConstructors() {
        game = new Game();
        assertEquals(WIDTH / 2.0, player.getPosX());
        assertEquals(HEIGHT / 2.0, player.getPosY());
        assertEquals(0, game.getEnemies().size());
        assertEquals(0, game.getBullets().size());
        assertEquals(0, game.getWalls().size());
    }

    @Test
    public void testCheckCollision() {
        Bullet b1 = new Bullet(1, 1, 1, 1, 1, 1);
        Bullet b2 = new Bullet(1, 1, 2, 2, 2, 2);
        game.checkCollision(b1, b2);
        game.checkCollision(b2, b1);
        assertEquals(1, game.getCollisionCheckedPairs().size());
    }

    @Test
    public void testUpdate() {
        game.fireBullet(0, 0);
        try {
            game.update(10000000);
        } catch (GameOverException e) {
            fail();
        }
        assertEquals(WIDTH / 2.0 + game.getPlayer().getDx(), player.getPosX());
        assertEquals(HEIGHT / 2.0, player.getPosY());

        assertEquals(550, bullets.get(0).getPosX());
        assertEquals(326 + player.getDy() * BULLET_SPEED, bullets.get(0).getPosY());

        assertEquals(202, enemies.get(0).getPosX());
        assertEquals(178, enemies.get(0).getPosY());
        assertEquals(268, enemies.get(1).getPosX());
        assertEquals(102, enemies.get(1).getPosY());

        assertEquals(248, game.getWalls().get(0).getPosX());
        assertEquals(252, game.getWalls().get(0).getPosY());
    }

    @Test
    public void testFireBullet() {
        game.fireBullet(0, 0);
        assertEquals(1, bullets.size());
        assertEquals(550, bullets.get(0).getPosX());
        assertEquals(330, bullets.get(0).getPosY());
    }

    @Test
    public void testPLayerSetVelocity() {
        player.setDx(5);
        player.setDy(-4);
        assertEquals(5, player.getDx());
        assertEquals(-4, player.getDy());
    }

    @Test
    public void testGameOver() {
        player.hit(new Bullet(10, 20, 25, 2, -2, 100));
        try {
            game.update(1);
            fail();
        } catch (GameOverException e) {
            // supposed to happen
        }
    }

    @Test
    public void testMultipleCycles() {
        for (int i = 0; i < 100; i++) {
            game.fireBullet(0, 0);
            try {
                game.update(10);
            } catch (GameOverException e) {
                fail();
            }
        }
        assertEquals(WIDTH / 2.0 + game.getPlayer().getDx(), player.getPosX());
        assertEquals(HEIGHT / 2.0, player.getPosY());

        assertEquals(550, bullets.get(0).getPosX());
        assertEquals(2, bullets.get(0).getPosY());

        assertEquals(400, enemies.get(0).getPosX());
        assertEquals(660, enemies.get(0).getPosY());
        assertEquals(70, enemies.get(1).getPosX());
        assertEquals(300, enemies.get(1).getPosY());

        assertEquals(248, game.getWalls().get(0).getPosX());
        assertEquals(252, game.getWalls().get(0).getPosY());
    }
}