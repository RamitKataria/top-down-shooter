package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.Reader;

import java.io.FileNotFoundException;
import java.util.List;

import static model.Game.BULLET_SPEED;
import static model.Game.NEW_GAME_FILE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GameTest {
    private Game game;
    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private List<Wall> walls;
    private Player player;

    @BeforeEach
    public void runBeforeEach() {
        try {
            game = Reader.readGame(NEW_GAME_FILE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        player = game.getPlayer();
        enemies = game.getEnemies();
        walls = game.getWalls();
        bullets = game.getBullets();
    }

    @Test
    public void testConstructors() {
        game = new Game();
        assertEquals(Game.WIDTH / 2, player.getPosX());
        assertEquals(Game.HEIGHT / 2, player.getPosY());
        assertEquals(0, game.getEnemies().size());
        assertEquals(0, game.getBullets().size());
        assertEquals(0, game.getWalls().size());

        Enemy testEnemy = new Enemy(10, 20, -2, 5);
        assertEquals(10, testEnemy.getPosX());
        assertEquals(20, testEnemy.getPosY());
        assertEquals(-2, testEnemy.getDx());
        assertEquals(5, testEnemy.getDy());

        Wall testWall = new Wall(25, 53, 10, 20);
        assertEquals(25, testWall.getPosX());
        assertEquals(53, testWall.getPosY());
    }

    @Test
    public void testUpdate() {
        game.fireBullet();
        game.update();
        assertEquals(Game.WIDTH / 2 + game.getPlayerVel(), player.getPosX());
        assertEquals(Game.HEIGHT / 2, player.getPosY());

        assertEquals(Game.WIDTH / 2 + player.getDx() * BULLET_SPEED, bullets.get(0).getPosX());
        assertEquals(Game.HEIGHT / 2 + player.getDy() * BULLET_SPEED, bullets.get(0).getPosY());

        assertEquals(201, enemies.get(0).getPosX());
        assertEquals(-181, enemies.get(0).getPosY());
        assertEquals(269, enemies.get(1).getPosX());
        assertEquals(101, enemies.get(1).getPosY());

        assertEquals(Game.WIDTH / 2 - 2, game.getWalls().get(0).getPosX());
        assertEquals(Game.WIDTH / 2 + 2, game.getWalls().get(0).getPosY());
    }

    @Test
    public void testFireBullet() {
        game.fireBullet();
        assertEquals(1, bullets.size());
        assertEquals(Game.WIDTH / 2, bullets.get(0).getPosX());
        assertEquals(Game.HEIGHT / 2, bullets.get(0).getPosY());
    }

    @Test
    public void testPLayerSetVelocity() {
        player.setDx(5);
        player.setDy(-4);
        assertEquals(5, player.getDx());
        assertEquals(-4, player.getDy());
    }
}