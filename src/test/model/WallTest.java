package model;

import model.gameobjects.Bullet;
import model.gameobjects.Wall;
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
    }
}
