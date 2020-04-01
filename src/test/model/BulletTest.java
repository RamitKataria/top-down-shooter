package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BulletTest {

    @Test
    public void testConstructor() {
        Bullet b = new Bullet(10, 20, 25, 2, -2, 15);
        assertEquals(10, b.getPosX());
        assertEquals(20, b.getPosY());
        assertEquals(25, b.getWidth());
        assertEquals(25, b.getHeight());
        assertEquals(2, b.getDx());
        assertEquals(-2, b.getDy());
        assertEquals(15, b.getHp());
    }
}
