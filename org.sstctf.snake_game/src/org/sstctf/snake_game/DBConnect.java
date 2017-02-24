package org.sstctf.snake_game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class handles connecting to the MySQL Database.
 * It handles the insertion and retrieval of top scores on the leaderboard.
 * 
 * @author Andrew Quach
 * @author Stanislav Lyahkov
 *
 * @version 1.0.0
 */
public class DBConnect {

    private String host;
    private String user;
    private String pass;

    /**
     * Initializes the information necessary to connect to the database
     * 
     */
    public DBConnect() {
        host = "jdbc:mysql://localhost/game?useSSL=false";
        user = "guest";
        pass = "guest";
    }
    
    private Connection connectDB() throws SQLException {
        Connection conn = DriverManager.getConnection(host, user, pass);
        return conn;
    }

    public String getScores(HUD hud) throws SQLException {
        Connection conn = connectDB();
        StringBuilder sb = new StringBuilder();
        Statement stmt = conn.createStatement();
        ResultSet results = stmt.executeQuery("SELECT * from leaderboards ORDER BY score DESC");
        while (results.next()) {
            sb.append(results.getString("name") + " - " + results.getInt("score") + "\n");
        }
        stmt.close();
        conn.close();
        return sb.toString();
    }

    public void update(int points) throws SQLException {
        Connection conn = connectDB();
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO leaderboards (name, score) VALUES ('guest2', ?)");
        pstmt.setInt(1, points);
        pstmt.executeUpdate();
        conn.close();
    }
}

