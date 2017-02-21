package org.sstctf.snake_game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnect {

    private String host;
    private String user;
    private String pass;
    private int limiter;

    public DBConnect() {
        host = "jdbc:mysql://localhost/game?useSSL=false";
        user = "guest";
        pass = "guest";
        limiter = 1;
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
        if (limiter != 0) {
            Connection conn = connectDB();
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO leaderboards (name, score) VALUES ('guest2', ?)");
            pstmt.setInt(1, points);
            pstmt.executeUpdate();
            conn.close();
            limiter = 0;
        }
    }
}

