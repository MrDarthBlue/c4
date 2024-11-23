package org.example;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;

public class ConnectFour {
    public static void main(String[] args) {
        // Inicializáljuk az adatbázist
        Database.initializeDatabase();

        Scanner scanner = new Scanner(System.in);
        String filePath = "game_state.txt"; // A játékállást tartalmazó fájl elérési útja
        Board board;

        // Kérjük a felhasználót, hogy szeretné-e betölteni az előző játék állását
        System.out.println("Do you want to load the previous game state? (y/n):");
        String loadResponse = scanner.nextLine().trim().toLowerCase();

        if (loadResponse.equals("y")) {
            try {
                board = Board.loadFromFile(filePath);
            } catch (IOException e) {
                System.out.println("Error loading game state: " + e.getMessage());
                return; // Ha hiba történt a betöltéskor, kilépünk
            }
        } else {
            // Ha nem szeretnék betölteni, hozzunk létre egy új, üres táblát
            board = new Board(6, 7); // Az alapértelmezett tábla méret 6x7
            // Ha szükséges, töröljük a fájlt
            File file = new File(filePath);
            if (file.exists()) {
                file.delete(); // Töröljük a fájlt, ha létezik
            }
        }

        // Játékos neveinek bekérése
        System.out.println("Enter your name, Player Yellow:");
        String playerName = scanner.nextLine();
        Player human = new Player(playerName, 'Y');

        AIPlayer ai = new AIPlayer("AI", 'R');

        System.out.println("Welcome to Connect Four, " + human.getName() + "!");
        board.display();

        boolean gameOver = false;
        while (!gameOver) {
            // Humán játékos lépése
            System.out.println("Your move (choose a column: a, b, c, ...):");
            String move = scanner.nextLine();
            while (!board.makeMove(human, move)) {
                System.out.println("Invalid move! Try again:");
                move = scanner.nextLine();
            }
            board.display();

            if (board.checkWin(human.getSymbol())) {
                System.out.println("You win!");
                // Játékos nyereményének frissítése az adatbázisban
                Database.updatePlayerWins(human.getName());
                gameOver = true;
                break;
            }

            if (board.isFull()) {
                System.out.println("It's a draw!");
                break;
            }

            // Gépi játékos lépése
            System.out.println("AI is making a move...");
            String aiMove = ai.chooseMove(board);
            board.makeMove(ai, aiMove);
            board.display();

            if (board.checkWin(ai.getSymbol())) {
                System.out.println("AI wins!");
                gameOver = true;
                break;
            }

            if (board.isFull()) {
                System.out.println("It's a draw!");
                break;
            }
        }

        // A játék állásának mentése a fájlba
        try {
            board.saveToFile(filePath);
        } catch (IOException e) {
            System.out.println("Error saving game state: " + e.getMessage());
        }

        // High score táblázat megjelenítése
        Database.displayHighScores();

        scanner.close();
    }
}


