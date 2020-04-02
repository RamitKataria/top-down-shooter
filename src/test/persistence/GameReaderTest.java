package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameReaderTest {
    private Game game;
    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private List<Wall> walls;
    private Player player;

    @Test
    public void testParseSampleFile() {
        try {
            game = GameReader.read(new File("./data/testing/sampleGame.json"));
            enemies = game.getEnemies();
            bullets = game.getBullets();
            walls = game.getWalls();
            player = game.getPlayer();

            assertEquals(1, enemies.size());
            assertEquals(1, bullets.size());
            assertEquals(0, walls.size());

            assertEquals(200, player.getPosX());
            assertEquals(-320, player.getPosY());
            assertEquals(4, player.getDx());
            assertEquals(5, player.getDy());

            assertEquals(-60, bullets.get(0).getPosX());
            assertEquals(210, bullets.get(0).getPosY());
            assertEquals(100, bullets.get(0).getDx());
            assertEquals(0, bullets.get(0).getDy());

            assertTrue(enemies.get(0).isAuto());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("IOException should not have been thrown");
        }
    }

    @Test
    public void testFileNotFound() {
        try {
            game = GameReader.read(new File("./data/dne/testFile.json"));
            fail("FileNotFoundException should have been thrown");
        } catch (FileNotFoundException e) {
            // nothing
        }
    }

    @Test
    public void testToCoverReaderClassName() {
        new GameReader();
    }
}
