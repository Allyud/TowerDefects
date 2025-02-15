package database;

import java.sql.*;

public class DatabaseManager {
    private static final String URL = "jdbc:postgresql://localhost:5432/tower_defense";
    private static final String USER = "postgres";
    private static final String PASSWORD = "3477";

    private static DatabaseManager instance;
    private Connection connection;


    public DatabaseManager() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connected.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public void saveGameRecord(String playerName, int score) {
        String sql = "INSERT INTO game_records (player_name, score) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, playerName);
            stmt.setInt(2, score);
            stmt.executeUpdate();
            System.out.println("Game record saved.");
            showHighScores();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showHighScores() {
        String sql = "SELECT * FROM game_records ORDER BY score DESC LIMIT 10";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            System.out.println("Top Ten Game Records:");
            while (rs.next()) {
                System.out.print(rs.getString("player_name") + " ");
                System.out.print(rs.getString("score") + " ") ;
                System.out.println(rs.getString("date"));
            }

        } catch (SQLException e){
            e.printStackTrace();
        }


    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
