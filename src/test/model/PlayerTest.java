package model;

import model.gameobjects.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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


}
