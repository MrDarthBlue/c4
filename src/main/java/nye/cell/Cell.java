package nye.cell;
import java.util.Objects;

public final class Cell {
    private final int row;
    private final int col;
    private final char content;

    public Cell(int row, int col, char content) {
        this.row = row;
        this.col = col;
        this.content = content;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public char getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return row == cell.row && col == cell.col && content == cell.content;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col, content);
    }

    @Override
    public String toString() {
        return String.format("Cell[row=%d, col=%d, content=%c]", row, col, content);
    }
}
