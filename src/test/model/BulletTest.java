package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.Game.WIDTH;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BulletTest {
    Bullet testBullet;

    @BeforeEach
    public void setUpTests() {
        testBullet = new Bullet(10, 20, 25, 2, -2, 15);
    }

    @Test
    public void testConstructor() {
        assertEquals(10, testBullet.getPosX());
        assertEquals(20, testBullet.getPosY());
        assertEquals(25, testBullet.getWidth());
        assertEquals(25, testBullet.getHeight());
        assertEquals(2, testBullet.getDx());
        assertEquals(-2, testBullet.getDy());
        assertEquals(15, testBullet.getHp());
    }

    @Test
    public void testUpdate() {
        testBullet.update();
        assertEquals(12, testBullet.getPosX());
        assertEquals(18, testBullet.getPosY());
        assertEquals(15, testBullet.getHp());
        testBullet.setPosX(WIDTH - 1);
        testBullet.setPosY(5);
        testBullet.update();
        assertEquals(WIDTH + 1, testBullet.getPosX());
        assertEquals(3, testBullet.getPosY());
        assertEquals(0, testBullet.getHp());
    }

    @Test
    public void testBounce() {
        testBullet.bounce();
        assertEquals(-2, testBullet.getDx());
        assertEquals(2, testBullet.getDy());
    }

    @Test
    public void testHit() {
        Player testPlayer = new Player(5, 5, 5, 5, 5);
        testBullet.hit(testPlayer);
        assertEquals(10, testBullet.getHp());
        assertEquals(-10, testPlayer.getHp());

        Wall testWall = new Wall(5, 5, 5, 5, 10);
        testBullet.hit(testWall);
        assertEquals(0, testWall.getHp());
        assertEquals(10, testBullet.getHp());
        assertEquals(-2, testBullet.getDx());
        assertEquals(2, testBullet.getDy());
    }
}
