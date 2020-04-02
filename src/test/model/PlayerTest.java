package model;

import javafx.geometry.HorizontalDirection;
import javafx.geometry.VerticalDirection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.lang.Math.sqrt;
import static model.Game.HEIGHT;
import static model.Game.WIDTH;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {
    Player player;

    @BeforeEach
    public void setUp() {
        player = new Player(50, 100, 30, 2, 100);
    }

    @Test
    public void testConstructor() {
        assertEquals(50, player.getPosX());
        assertEquals(100, player.getPosY());
        assertEquals(30, player.getWidth());
        assertEquals(30, player.getHeight());
        assertEquals(0, player.getDx());
        assertEquals(0, player.getDy());
    }

    @Test
    public void testHpFraction() {
        player.hit(new Bullet(1, 1, 1, 1, 1, 20));
        assertEquals(0.8, player.getHpFraction());
        player.hit(new Bullet(1, 1, 1, 1, 1, 81));
        assertEquals(0, player.getHpFraction());
    }

    @Test
    public void testBoundary() {
        player = new Player(-5, 100, 30, 2, 100);
        player.update();
        assertEquals(WIDTH - 5, player.getPosX());
        assertEquals(100, player.getPosY());

        player = new Player(50, -3, 30, 2, 100);
        player.update();
        assertEquals(50, player.getPosX());
        assertEquals(HEIGHT - 3, player.getPosY());

        player = new Player(-2, -4, 30, 2, 100);
        player.update();
        assertEquals(WIDTH - 2, player.getPosX());
        assertEquals(HEIGHT - 4, player.getPosY());
    }

    @Test
    public void testRegenerateHP() {
        player.hit(new Bullet(1, 1, 1, 1, 1, 10));
        player.regenerateHP(0.1);
        assertEquals(91, player.getHp());
    }

    @Test
    public void testHorizontalDirection() {
        player.setHorizontalDirection(HorizontalDirection.RIGHT);
        assertEquals(2, player.getDx());

        player.setHorizontalDirection(HorizontalDirection.LEFT);
        assertEquals(-2, player.getDx());
    }

    @Test
    public void testVerticalDirection() {
        player.setVerticalDirection(VerticalDirection.DOWN);
        assertEquals(2, player.getDy());

        player.setVerticalDirection(VerticalDirection.UP);
        assertEquals(-2, player.getDy());
    }

    @Test
    public void testDiagonal() {
        player.setVerticalDirection(VerticalDirection.DOWN);
        player.setHorizontalDirection(HorizontalDirection.RIGHT);

        assertEquals(2 / sqrt(2), player.getDy());
        assertEquals(2 / sqrt(2), player.getDy());
    }
}
