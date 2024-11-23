package nye.gameresult;
import java.util.Objects;

public final class GameResult {
    private final String winnerName;
    private final char winnerSymbol;
    private final boolean isDraw;

    public GameResult(String winnerName, char winnerSymbol, boolean isDraw) {
        this.winnerName = winnerName;
        this.winnerSymbol = winnerSymbol;
        this.isDraw = isDraw;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public char getWinnerSymbol() {
        return winnerSymbol;
    }

    public boolean isDraw() {
        return isDraw;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameResult that = (GameResult) o;
        return winnerSymbol == that.winnerSymbol && isDraw == that.isDraw && Objects.equals(winnerName, that.winnerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(winnerName, winnerSymbol, isDraw);
    }

    @Override
    public String toString() {
        return isDraw
                ? "GameResult[draw=true]"
                : String.format("GameResult[winner=%s, symbol=%c]", winnerName, winnerSymbol);
    }
}

