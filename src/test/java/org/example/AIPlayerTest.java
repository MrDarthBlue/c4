package org.example;

import nye.players.AIPlayer;
import nye.tabla.Board;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AIPlayerTest {
    @Test
    public void testChooseMove_ValidMove() {
        AIPlayer ai = new AIPlayer("AI", 'R');
        Board board = new Board(6, 7);
        String move = ai.chooseMove(board);
        assertTrue(move.matches("[a-g]")); // Az AI választott lépésének oszlopnak kell lennie
    }
}

