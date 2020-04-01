package persistence;

import model.Game;
import model.gameobjects.Bullet;
import model.gameobjects.Enemy;
import model.gameobjects.Player;
import model.gameobjects.Wall;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class WriterTest {
    private static final File TEST_FILE = new File("./data/testing/testWriteFile.json");
    private Writer writer;
    Game game;
    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private List<Wall> walls;
    private Player player;

    @BeforeEach
    public void setUp() throws IOException {
        writer = new Writer(TEST_FILE);
    }

    @Test
    public void testWriteNewGame() {
        game = new Game();
        game.getEnemies().add(new Enemy(1, 1, 1, 1, 1, 1));
        try {
            writer.write(game);
            game = GameReader.read(TEST_FILE);

            enemies = game.getEnemies();
            bullets = game.getBullets();
            walls = game.getWalls();
            player = game.getPlayer();
            assertEquals(1, enemies.size());
            assertEquals(0, bullets.size());
            assertEquals(10, walls.size());

            assertEquals(540, player.getPosX());
            assertEquals(340, player.getPosY());
            assertEquals(0, player.getDx());
            assertEquals(0, player.getDy());

            assertEquals(10, walls.size());

        } catch (IOException e) {
            e.printStackTrace();
            fail("IOException should not have been thrown");
        }
    }

    @Test
    public void testWriteSampleFile() {
        try {
            writer.write(GameReader.read(new File("./data/testing/sampleGame.json")));
            game = GameReader.read(TEST_FILE);

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
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }
}
