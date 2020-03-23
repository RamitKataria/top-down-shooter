package model;

import javafx.geometry.HorizontalDirection;
import javafx.geometry.VerticalDirection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PlayerTest {
    Player player;

    @BeforeEach
    public void setUp() {
        player = new Player(50, 100, 25, 30, 2);
    }

    @Test
    public void testConstructor() {
        assertEquals(50, player.getPosX());
        assertEquals(100, player.getPosY());
        assertEquals(25, player.getWidth());
        assertEquals(30, player.getHeight());
        assertEquals(0, player.getDx());
        assertEquals(0, player.getDy());
        assertEquals(VerticalDirection.UP, player.getVerticalFacingDirection());
        assertNull(player.getHorizontalFacingDirection());
    }

    @Test
    public void testUpdate() {
        player.setDx(1);
        player.setDy(1);
        player.update();
        assertEquals(HorizontalDirection.RIGHT, player.getHorizontalFacingDirection());
        assertEquals(VerticalDirection.DOWN, player.getVerticalFacingDirection());

        player.setDx(-1);
        player.setDy(-1);
        player.update();
        assertEquals(HorizontalDirection.LEFT, player.getHorizontalFacingDirection());
        assertEquals(VerticalDirection.UP, player.getVerticalFacingDirection());
    }

    @Test
    public void testSetHorizontalMovingDirection() {
        player.setHorizontalMovingDirection(HorizontalDirection.RIGHT);
        player.setVerticalMovingDirection(null);
        assertEquals(HorizontalDirection.RIGHT, player.getHorizontalFacingDirection());
    }

    @Test
    public void testSetVerticalMovingDirection() {
        player.setVerticalMovingDirection(VerticalDirection.UP);
        player.setHorizontalMovingDirection(null);
        assertEquals(VerticalDirection.UP, player.getVerticalFacingDirection());
    }
}
