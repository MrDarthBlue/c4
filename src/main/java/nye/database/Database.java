package nye.database;
import java.sql.*;

public class Database {
    private static final String DB_URL = "jdbc:sqlite:game_highscores.db";

    // Adatbázis kapcsolat létrehozása
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    // Játékosok táblájának létrehozása, ha nem létezik
    public static void initializeDatabase() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS players ("
                + "name TEXT PRIMARY KEY,"
                + "wins INTEGER NOT NULL DEFAULT 0"
                + ");";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            System.out.println("Database initialization error: " + e.getMessage());
        }
    }

    // Játékos nyereményének frissítése
    public static void updatePlayerWins(String playerName) {
        String updateSQL = "INSERT INTO players (name, wins) VALUES (?, 1) "
                + "ON CONFLICT(name) DO UPDATE SET wins = wins + 1;";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
            pstmt.setString(1, playerName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating player wins: " + e.getMessage());
        }
    }

    // High score táblázat megjelenítése
    public static void displayHighScores() {
        String selectSQL = "SELECT name, wins FROM players ORDER BY wins DESC LIMIT 10;";

        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(selectSQL)) {
            System.out.println("High Scores:");
            while (rs.next()) {
                String name = rs.getString("name");
                int wins = rs.getInt("wins");
                System.out.println(name + ": " + wins);
            }
        } catch (SQLException e) {
            System.out.println("Error displaying high scores: " + e.getMessage());
        }
    }
}

