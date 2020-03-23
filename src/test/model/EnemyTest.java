package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnemyTest {

    @Test
    public void testConstructor() {
        Enemy e = new Enemy(10, 20, 50, 25, 2, -2, 15);
        assertEquals(10, e.getPosX());
        assertEquals(20, e.getPosY());
        assertEquals(50, e.getWidth());
        assertEquals(25, e.getHeight());
        assertEquals(2, e.getDx());
        assertEquals(-2, e.getDy());
        assertEquals(15, e.getHp());
    }
}
