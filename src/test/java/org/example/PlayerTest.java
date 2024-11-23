package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    @Test
    public void testPlayerCreation() {
        Player player = new Player("Player", 'Y');
        assertEquals("Player", player.getName());
        assertEquals('Y', player.getSymbol());
    }

    @Test
    public void testEquals() {
        Player player1 = new Player("Player", 'Y');
        Player player2 = new Player("Player", 'Y');
        Player player3 = new Player("Player", 'R');
        assertTrue(player1.equals(player2));
        assertFalse(player1.equals(player3));
    }

    @Test
    public void testHashCode() {
        Player player = new Player("Player", 'Y');
        assertEquals(player.hashCode(), new Player("Player", 'Y').hashCode());
    }
}
