package org.example;
import nye.tabla.Board;
import nye.players.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

public class BoardTest {
    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board(6, 7); // Új 6x7-es tábla
    }

    @Test
    public void testMakeMove_ValidMove() {
        Player player = new Player("Player", 'Y');
        boolean result = board.makeMove(player, "a");
        assertTrue(result);
        assertEquals('Y', board.getGrid()[5][0]); // Az 'a' oszlopba a játékos lépése
    }

    @Test
    public void testMakeMove_InvalidColumn() {
        Player player = new Player("Player", 'Y');
        boolean result = board.makeMove(player, "h"); // Érvénytelen oszlop
        assertFalse(result);
    }

    @Test
    public void testIsColumnAvailable_ColumnAvailable() {
        assertTrue(board.isColumnAvailable("a"));
    }

    @Test
    public void testIsColumnAvailable_ColumnFull() {
        Player player = new Player("Player", 'Y');
        // Töltsük meg az 'a' oszlopot
        for (int i = 0; i < 6; i++) {
            board.makeMove(player, "a");
        }
        assertFalse(board.isColumnAvailable("a"));
    }

    @Test
    public void testCheckWin_HorizontalWin() {
        Player player = new Player("Player", 'Y');
        board.makeMove(player, "a");
        board.makeMove(player, "b");
        board.makeMove(player, "c");
        board.makeMove(player, "d");
        assertTrue(board.checkWin('Y'));
    }

    @Test
    public void testCheckWin_VerticalWin() {
        Player player = new Player("Player", 'Y');
        for (int i = 0; i < 4; i++) {
            board.makeMove(player, "a");
        }
        assertTrue(board.checkWin('Y'));
    }

    @Test
    public void testLoadFromFile() throws IOException {
        Board loadedBoard = Board.loadFromFile("game_state.txt");
        assertNotNull(loadedBoard);
    }

    @Test
    public void testSaveToFile() throws IOException {
        board.makeMove(new Player("Player", 'Y'), "a");
        board.saveToFile("game_state_test.txt");
        Board loadedBoard = Board.loadFromFile("game_state_test.txt");
        assertEquals(board.getGrid()[5][0], loadedBoard.getGrid()[5][0]);
    }
}
