package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WallTest {
    private Wall testWall;

    @BeforeEach
    public void setUp() {
        testWall = new Wall(25, 53, 10, 20, 5000);
    }

    @Test
    public void testConstructor() {
        assertEquals(25, testWall.getPosX());
        assertEquals(53, testWall.getPosY());
        assertEquals(10, testWall.getWidth());
        assertEquals(20, testWall.getHeight());
        assertEquals(5000, testWall.getHp());
    }

    @Test
    public void testHit() {
        Bullet b = new Bullet(10, 20, 50, 2, -2, 15);
        testWall.hit(b);
        assertEquals(5000 - 15, testWall.getHp());
        assertEquals(-2, b.getDx());
        assertEquals(2, b.getDy());

        Player p = new Player(1, 1, 1, 1, 1);
        testWall.hit(p);
        assertEquals(35, p.getPosX());
        assertEquals(73, p.getPosY());

        p = new Player(1, 1, 1, 1, 1);
        p.hit(testWall);
        assertEquals(35, p.getPosX());
        assertEquals(73, p.getPosY());
    }
}
