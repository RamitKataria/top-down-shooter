package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CollisionPairTest {

    @Test
    public void testConstructor() {
        Bullet testB = new Bullet(1, 1, 1, 1, 1, 1);
        Enemy testE = new Enemy(1, 1, 1, 1, 1, 1);
        CollisionPair testCP = new CollisionPair(testB, testE);
        assertTrue(testCP.contains(testB));
        assertTrue(testCP.contains(testE));
    }
}
