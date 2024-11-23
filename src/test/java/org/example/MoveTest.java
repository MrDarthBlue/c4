package org.example;

import nye.move.Move;
import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MoveTest {
    @Test
    public void testMoveCreation() {
        Move move = new Move("Player", 'Y', "a");
        assertEquals("Player", move.getPlayerName());
        assertEquals('Y', move.getSymbol());
        assertEquals("a", move.getColumn());
    }

    @Test
    public void testEquals() {
        Move move1 = new Move("Player", 'Y', "a");
        Move move2 = new Move("Player", 'Y', "a");
        Move move3 = new Move("AI", 'R', "b");
        assertTrue(move1.equals(move2));
        assertFalse(move1.equals(move3));
    }

    @Test
    public void testHashCode() {
        Move move = new Move("Player", 'Y', "a");
        assertEquals(move.hashCode(), new Move("Player", 'Y', "a").hashCode());
    }
}

