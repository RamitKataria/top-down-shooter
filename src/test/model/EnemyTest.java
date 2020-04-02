package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyTest {

    @Test
    public void testConstructorRegular() {
        Enemy e = new Enemy(10, 20, 50, 2, -2, 15);
        assertEquals(10, e.getPosX());
        assertEquals(20, e.getPosY());
        assertEquals(50, e.getWidth());
        assertEquals(50, e.getHeight());
        assertEquals(2, e.getDx());
        assertEquals(-2, e.getDy());
        assertEquals(15, e.getHp());
        assertFalse(e.isAuto());
    }

    @Test
    public void testConstructorAuto() {
        Player target = new Player(1, 1, 1, 1, 1);
        Enemy e = new Enemy(10, 20, 5, 50, 15, target);
        assertEquals(10, e.getPosX());
        assertEquals(20, e.getPosY());
        assertEquals(50, e.getWidth());
        assertEquals(50, e.getHeight());
        assertEquals(15, e.getHp());
        assertEquals(target, e.getPlayer());
        assertTrue(e.isAuto());
    }

    @Test
    public void testSetPlayer() {
        Player target = new Player(1, 1, 1, 1, 1);
        Enemy e = new Enemy(10, 20, 5, 50, 15, null);
        assertNull(e.getPlayer());
        e.setPlayer(target);
        assertEquals(target, e.getPlayer());
    }

    @Test
    public void testUpdateRegular() {
        Enemy e = new Enemy(10, 20, 50, 2, -2, 15);
        e.update();
        assertEquals(12, e.getPosX());
        assertEquals(18, e.getPosY());
    }

    @Test
    public void testUpdateAuto() {
        Player target = new Player(1000, 10, 1, 1, 1);
        Enemy e = new Enemy(10, 10, 5, 50, 15, target);
        e.update();
        assertEquals(10, e.getPosY());
        assertEquals(15, e.getPosX());
    }
}
