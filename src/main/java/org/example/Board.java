package org.example;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Board {
    private final char[][] grid;
    private final int rows;
    private final int cols;
    private final char emptySlot = '.';

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        grid = new char[rows][cols];
        // Üres tábla inicializálása
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = emptySlot;
            }
        }
    }

    // Játékállás betöltése fájlból
    public static Board loadFromFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            return new Board(6, 7); // Ha nincs fájl, akkor üres táblával kezdjük
        }

        List<String> lines = Files.readAllLines(file.toPath());
        if (lines.isEmpty()) {
            throw new IOException("Invalid file format");
        }

        // Az első sorban a tábla mérete
        String[] dimensions = lines.get(0).split(" ");
        int rows = Integer.parseInt(dimensions[0]);
        int cols = Integer.parseInt(dimensions[1]);

        Board board = new Board(rows, cols);

        // A további sorok a tábla aktuális állását tartalmazzák
        for (int i = 0; i < rows; i++) {
            String row = lines.get(i + 1); // Az első sor után jönnek a tábla sorai
            for (int j = 0; j < cols; j++) {
                char cell = row.charAt(j);
                board.grid[i][j] = cell;
            }
        }

        return board;
    }

    public boolean makeMove(Player player, String column) {
        int colIndex = columnToIndex(column);
        if (colIndex == -1 || colIndex >= cols) {
            return false;
        }

        for (int row = rows - 1; row >= 0; row--) {
            if (grid[row][colIndex] == emptySlot) {
                grid[row][colIndex] = player.getSymbol();
                return true;
            }
        }

        return false; // Ha az oszlop tele van
    }

    public boolean isFull() {
        for (int j = 0; j < cols; j++) {
            if (grid[0][j] == emptySlot) {
                return false;
            }
        }
        return true;
    }

    public boolean checkWin(char symbol) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (checkDirection(row, col, 1, 0, symbol) ||
                        checkDirection(row, col, 0, 1, symbol) ||
                        checkDirection(row, col, 1, 1, symbol) ||
                        checkDirection(row, col, 1, -1, symbol)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDirection(int row, int col, int rowDelta, int colDelta, char symbol) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            int r = row + i * rowDelta;
            int c = col + i * colDelta;
            if (r >= 0 && r < rows && c >= 0 && c < cols && grid[r][c] == symbol) {
                count++;
            } else {
                break;
            }
        }
        return count == 4;
    }

    private int columnToIndex(String column) {
        return column.toLowerCase().charAt(0) - 'a';
    }

    public void display() {
        System.out.println("  a b c d e f g");
        for (int i = 0; i < rows; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < cols; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean isColumnAvailable(String column) {
        int colIndex = columnToIndex(column);
        if (colIndex < 0 || colIndex >= cols) {
            return false;
        }
        return grid[0][colIndex] == emptySlot;
    }
    // Pálya kiírása a savetoFile-ba
    public void saveToFile(String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            // Írjuk a tábla méretét
            writer.write(rows + " " + cols + "\n");

            // Írjuk a tábla állását
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    writer.write(grid[i][j]);
                }
                writer.write("\n");
            }
        }
    }
    public char[][] getGrid() {
        return grid;
    }

}
