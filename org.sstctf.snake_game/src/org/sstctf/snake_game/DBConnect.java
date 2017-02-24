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
     * Initializes the information necessary to connect to the database.
     * 
     * @param host the location of the database
     * @param user the username of the user accessing the database
     * @param pass the password of the user accessing the database
     */
    public DBConnect(String host, String user, String pass) {
        this.host = host;
        this.user = user;
        this.pass = pass;
    }
    
    /**
     * Helper method that creates the connection to the specified database.
     * 
     * @return the SQL connection
     * @throws SQLException
     */
    private Connection connectDB() throws SQLException {
        Connection conn = DriverManager.getConnection(host, user, pass);
        return conn;
    }

    /**
     * Getting the top 5 scores from the leaderboards and returning it as a string.
     * The string separates the names and scores by a dash.
     * The string separates different scores by newline.
     * 
     * @return a string containing the top 5 scores in leaderboards delimited by newlines
     * @throws SQLException
     */
    public String getScores() throws SQLException {
        Connection conn = connectDB();
        StringBuilder sb = new StringBuilder();
        Statement stmt = conn.createStatement();
        // Get the top 5 scores from table leaderboards
        ResultSet results = stmt.executeQuery("SELECT * from leaderboards ORDER BY score DESC LIMIT 5");
        // Generate a string from the top 5 results
        while (results.next()) {
            sb.append(results.getString("name") + " - " + results.getInt("score") + "\n");
        }
        stmt.close();
        conn.close();
        return sb.toString();
    }
    
    /**
     * The method updates the leaderboards table with the current score.
     * The query consists of the user inputed name and their corresponding score.
     * The name is truncated to a maximum of 3 characters and capitalized.
     * 
     * @param points the number of points the user achieved
     * @param name the inputed name of the user
     * @throws SQLException
     */
    public void update(int points, String name) throws SQLException {
        Connection conn = connectDB();
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO leaderboards (name, score) VALUES (?, ?)");
        // Ensures name is at least 3 characters
        name += "   ";
        // Takes the first three letters of the name and converts it to upercase
        name = name.substring(0, 3).toUpperCase();
        pstmt.setString(1, name);
        pstmt.setInt(2, points);
        pstmt.executeUpdate();
        conn.close();
    }
}

