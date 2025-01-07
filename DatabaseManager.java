import java.sql.*;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/music_db";
    private static final String USER = "root";
    private static final String PASS = "Shrey@22";

    // Establish connection to the database
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    // Insert user into database
    public void insertUser(String name, String email) throws SQLException {
        String query = "INSERT INTO Users (name, email) VALUES (?, ?)";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.executeUpdate();
        }
    }

    // Insert user preferences
    public void insertUserPreferences(int userId, String genre, String mood) throws SQLException {
        String query = "INSERT INTO Preferences (user_id, genre, mood) VALUES (?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setString(2, genre);
            stmt.setString(3, mood);
            stmt.executeUpdate();
        }
    }

    // Fetch user preferences
    public ResultSet getUserPreferences(int userId) throws SQLException {
        String query = "SELECT genre, mood FROM Preferences WHERE user_id = ?";
        Connection conn = connect();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, userId);
        return stmt.executeQuery();
    }

    // Fetch user by email
    public ResultSet getUserByEmail(String email) throws SQLException {
        String query = "SELECT id FROM Users WHERE email = ?";
        Connection conn = connect();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, email);
        return stmt.executeQuery();
    }
}

