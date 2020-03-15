package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static model.Game.NEW_GAME_FILE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ReaderTest {
    private Game game;
    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private List<Wall> walls;
    private Player player;

    @Test
    public void testParseNewGameFile() {
        try {
            game = Reader.readGame(NEW_GAME_FILE);
            enemies = game.getEnemies();
            bullets = game.getBullets();
            walls = game.getWalls();
            player = game.getPlayer();

            assertEquals(2, enemies.size());
            assertEquals(0, bullets.size());
            assertEquals(1, walls.size());

            assertEquals(250, player.getPosX());
            assertEquals(250, player.getPosY());
            assertEquals(1, player.getDx());
            assertEquals(0, player.getDy());

            assertEquals(200, enemies.get(0).getPosX());
            assertEquals(-180, enemies.get(0).getPosY());
            assertEquals(1, enemies.get(0).getDx());
            assertEquals(-1, enemies.get(0).getDy());
            assertEquals(270, enemies.get(1).getPosX());
            assertEquals(100, enemies.get(1).getPosY());
            assertEquals(-1, enemies.get(1).getDx());
            assertEquals(1, enemies.get(1).getDy());

            assertEquals(248, game.getWalls().get(0).getPosX());
            assertEquals(252, game.getWalls().get(0).getPosY());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("IOException should not have been thrown");
        }
    }

    @Test
    public void testParseSampleFile() {
        try {
            game = Reader.readGame(new File("./data/testing/sampleGame.json"));
            enemies = game.getEnemies();
            bullets = game.getBullets();
            walls = game.getWalls();
            player = game.getPlayer();

            assertEquals(0, enemies.size());
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("IOException should not have been thrown");
        }
    }

    @Test
    public void testFileNotFound() {
        try {
            game = Reader.readGame(new File("./data/dne/testFile.json"));
            fail("FileNotFoundException should have been thrown");
        } catch (FileNotFoundException e) {
            // nothing
        }
    }

    @Test
    public void testToCoverReaderClassName() {
        new Reader();
    }
}
