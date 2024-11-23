package org.example;

import org.junit.jupiter.api.*;
import java.sql.*;
import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {

    @BeforeEach
    public void setUp() {
        // Lehetőség a tesztadatok törlésére az adatbázisból
        try (Connection conn = Database.connect()) {
            Statement stmt = conn.createStatement();
            stmt.execute("DELETE FROM players;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDatabaseConnection() {
        try (Connection conn = Database.connect()) {
            assertNotNull(conn); // Az adatbázis kapcsolat nem lehet null
        } catch (SQLException e) {
            fail("Database connection failed: " + e.getMessage());
        }
    }

    @Test
    public void testInitializeDatabase() {
        Database.initializeDatabase();
        try (Connection conn = Database.connect()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='players';");
            assertTrue(rs.next(), "The players table should exist in the database.");
        } catch (SQLException e) {
            fail("Database initialization failed: " + e.getMessage());
        }
    }

    @Test
    public void testUpdatePlayerWins_NewPlayer() {
        Database.updatePlayerWins("Player1");
        try (Connection conn = Database.connect()) {
            PreparedStatement pstmt = conn.prepareStatement("SELECT wins FROM players WHERE name = ?");
            pstmt.setString(1, "Player1");
            ResultSet rs = pstmt.executeQuery();
            assertTrue(rs.next());
            assertEquals(1, rs.getInt("wins"), "Player1 should have 1 win.");
        } catch (SQLException e) {
            fail("Error updating player wins: " + e.getMessage());
        }
    }

    @Test
    public void testUpdatePlayerWins_ExistingPlayer() {
        Database.updatePlayerWins("Player1");
        Database.updatePlayerWins("Player1");
        try (Connection conn = Database.connect()) {
            PreparedStatement pstmt = conn.prepareStatement("SELECT wins FROM players WHERE name = ?");
            pstmt.setString(1, "Player1");
            ResultSet rs = pstmt.executeQuery();
            assertTrue(rs.next());
            assertEquals(2, rs.getInt("wins"), "Player1 should have 2 wins.");
        } catch (SQLException e) {
            fail("Error updating player wins: " + e.getMessage());
        }
    }

    @Test
    public void testDisplayHighScores() {
        Database.updatePlayerWins("Player1");
        Database.updatePlayerWins("Player2");
        try (Connection conn = Database.connect()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name, wins FROM players ORDER BY wins DESC LIMIT 2;");
            assertTrue(rs.next());
            assertEquals("Player1", rs.getString("name"));
            assertEquals(1, rs.getInt("wins"));
            assertTrue(rs.next());
            assertEquals("Player2", rs.getString("name"));
            assertEquals(1, rs.getInt("wins"));
        } catch (SQLException e) {
            fail("Error displaying high scores: " + e.getMessage());
        }
    }
}
