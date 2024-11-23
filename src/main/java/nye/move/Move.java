package nye.move;
import java.util.Objects;

public final class Move {
    private final String playerName;
    private final char symbol;
    private final String column;

    public Move(String playerName, char symbol, String column) {
        this.playerName = playerName;
        this.symbol = symbol;
        this.column = column;
    }

    public String getPlayerName() {
        return playerName;
    }

    public char getSymbol() {
        return symbol;
    }

    public String getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return symbol == move.symbol && Objects.equals(playerName, move.playerName) && Objects.equals(column, move.column);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerName, symbol, column);
    }

    @Override
    public String toString() {
        return String.format("Move[player=%s, symbol=%c, column=%s]", playerName, symbol, column);
    }
}

