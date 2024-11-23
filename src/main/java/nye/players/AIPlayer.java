package nye.players;

import nye.tabla.Board;

import java.util.Random;

public class AIPlayer extends Player {
    private final Random random = new Random();

    public AIPlayer(String name, char symbol) {
        super(name, symbol);
    }

    public String chooseMove(Board board) {
        int colIndex;
        do {
            colIndex = random.nextInt(7); // 7 oszlop
        } while (!board.isColumnAvailable(indexToColumn(colIndex)));

        return indexToColumn(colIndex);
    }

    private String indexToColumn(int index) {
        return String.valueOf((char) ('a' + index));
    }
}
