package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CollisionPairTest {

    @BeforeEach
    public void setUpTests() {

    }

    @Test
    public void testConstructor() {
        Player testP = new Player(1, 1, 1, 1, 2);
        Enemy testE = new Enemy(1, 1, 1, 1, 1, 1);
        CollisionPair testCP = new CollisionPair(testP, testE);
        assertTrue(testCP.contains(testP));
        assertTrue(testCP.contains(testE));
    }

    @Test
    public void testExecuteCollisionDoesntHappen() {
        Player testP = new Player(1, 1, 1, 1, 0);
        Enemy testE = new Enemy(1, 1, 1, 1, 1, 1);
        CollisionPair pair = new CollisionPair(testP, testE);
        pair.executeCollision();
        assertEquals(0, testP.getHp());
        assertEquals(1, testE.getHp());

        testP = new Player(1, 1, 1, 1, 1);
        testE = new Enemy(1, 1, 1, 1, 1, 0);
        pair = new CollisionPair(testP, testE);
        pair.executeCollision();
        assertEquals(1, testP.getHp());
        assertEquals(0, testE.getHp());

        testP = new Player(1, 1, 1, 1, 0);
        testE = new Enemy(1, 1, 1, 1, 1, 0);
        pair = new CollisionPair(testP, testE);
        pair.executeCollision();
        assertEquals(0, testP.getHp());
        assertEquals(0, testE.getHp());
    }

    @Test
    public void testExecuteCollisionHappens() {
        Player testP = new Player(1, 1, 1, 1, 5);
        Enemy testE = new Enemy(1, 1, 1, 1, 1, 3);
        CollisionPair pair = new CollisionPair(testP, testE);
        pair.executeCollision();
        assertEquals(2, testP.getHp());
        assertEquals(-2, testE.getHp());
    }

    @Test
    public void testEquals() {
        Player testP = new Player(1, 1, 1, 1, 5);
        Enemy testE = new Enemy(1, 1, 1, 1, 1, 3);
        Wall testW = new Wall(1, 1, 1, 1, 1);

        CollisionPair pair1 = new CollisionPair(testP, testE);
        CollisionPair pair2 = new CollisionPair(testP, testE);
        assertEquals(pair1, pair2);
        assertEquals(pair1, pair1);

        pair1 = new CollisionPair(testP, testE);
        pair2 = new CollisionPair(testE, testP);
        assertEquals(pair1, pair2);

        pair1 = new CollisionPair(testW, testE);
        pair2 = new CollisionPair(testE, testE);
        assertNotEquals(pair1, pair2);

        pair1 = new CollisionPair(testP, testW);
        pair2 = new CollisionPair(testP, testE);
        assertNotEquals(pair1, pair2);

        assertNotEquals(pair1, testE);
    }
}
