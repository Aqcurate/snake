package org.sstctf.snake_game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.sql.SQLException;

import javax.swing.JOptionPane;

/**
 * This class handles all rendering past player death.
 * The class uses the DBConnect object to render leaderboard statistics.
 * On death, the gameState gets changed to the DEATH state.
 * 
 * @author Andrew Quach
 * @author Stanislav Lyakhov
 *
 * @version 1.0.0
 */
public class DeathScreen {

    private boolean hasDB = true;
    private DBConnect db;
    private String leaderboard;
    private HUD hud;
    
    /**
     * Connects to the database on creation of DeathScreen object.
     * Initializes whether the set up the database system and the
     * leaderboard string to default states.
     * 
     * @param hud the hud object contains information about current score
     */
    public DeathScreen(HUD hud) {
        db = new DBConnect("jdbc:mysql://localhost/game?useSSL=false", "guest", "guest");
        hasDB = true;
        leaderboard = "";
        this.hud = hud;
    }

    /**
     * Executes when the player dies.
     * The state of the game is set to DEATH.
     * Checks if the user set up a database system.
     * If the database system is set up, the user is prompted for initials.
     * Sends the initials and current score to the database object.
     * Retrieves the leaderboard string from the database object.
     */
    public void onDeath() {
        Game.gameState = State.DEATH;
        // Check if database is set up
        if (hasDB) {
            try {
                // Show dialogue box
                String name = JOptionPane.showInputDialog("Enter your initials:", "");
                // If the user clicks cancel or inputs no name, make the default name NUL
                if (name == null || name.equals("")) name = "NUL";
                // Update the database
                db.update(hud.getScore(), name);
                // Get the leaderboard string
                leaderboard = db.getScores();
            } catch (SQLException s) {
                // Database errors and is not set up properly
                hasDB = false;
                System.out.println(s.getMessage());
            }
        }
    }

    /**
     * Helper method that draws a string centered horizontally.
     * 
     * @param g the graphics object that draws on the canvas
     * @param text the text we wish to draw
     * @param vertical y-coord we wish to draw at
     */
    private void drawCenteredString(Graphics g, String text, int vertical) {
        // Gets the metrics of current font
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        // Calculates the x-coord we need to draw at to have it centered horizontally
        int center = (Game.WIDTH - metrics.stringWidth(text)) / 2;
        g.drawString(text, center, vertical);
    }
    
    /**
     * Method that controls the end-screen rendering.
     * Renders out the black background, the gameover box
     * the retry box, the final score box, and the leaderboard box.
     * 
     * @param g the graphics object that draws on the canvas
     */
    public void render(Graphics g) {
        // Set Background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT+100);
        
        // Set Gameover Box
        g.setColor(Color.RED);
        g.fillRect(Game.SCALE, Game.SCALE, Game.WIDTH-2*Game.SCALE, Game.SCALE*8);
        g.setFont(new Font("Helvetica", Font.PLAIN, 64));
        g.setColor(Color.BLACK);
        drawCenteredString(g, "Game Over", Game.SCALE*7);
        
        // Set Retry Box
        g.setColor(Color.WHITE);
        g.fillRect(Game.SCALE, Game.SCALE*10, Game.WIDTH-2*Game.SCALE, Game.SCALE*4);
        g.setFont(new Font("Helvetica", Font.PLAIN, 32));
        g.setColor(Color.BLACK);
        drawCenteredString(g, "Final Score: " + hud.getScore(), Game.SCALE*13);
        
        // Set Final Score Box
        g.setColor(Color.WHITE);
        g.fillRect(Game.SCALE, Game.SCALE*15, Game.WIDTH-2*Game.SCALE, Game.SCALE*4);
        g.setColor(Color.BLACK);
        drawCenteredString(g, "Press 'R' to Retry", Game.SCALE*18);

        // Set Leaderboard Box
        g.setColor(Color.YELLOW);
        g.fillRect(Game.SCALE, Game.SCALE*20, Game.WIDTH-2*Game.SCALE, Game.HEIGHT-Game.SCALE*21+100);
        g.setFont(new Font("Helvetica", Font.PLAIN, 32));
        int leaderboardY = Game.SCALE*23;
        g.setColor(Color.BLACK);
        drawCenteredString(g, "Leaderboard:", leaderboardY);
        g.setColor(Color.BLUE);
        for (String line : leaderboard.split("\n")) {
            drawCenteredString(g, line, leaderboardY += Game.SCALE*3);
        }
    }
}
