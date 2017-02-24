package org.sstctf.snake_game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class DeathScreen {

    private boolean hasDB = true;
    private DBConnect db;
    private String leaderboard;
    private HUD hud;
    
    public DeathScreen(HUD hud) {
        db = new DBConnect("jdbc:mysql://localhost/game?useSSL=false", "guest", "guest");
        hasDB = true;
        leaderboard = "";
        this.hud = hud;
    }

    public void onDeath() {
        Game.gameState = State.DEATH;
        if (hasDB) {
            try {
                String name = JOptionPane.showInputDialog("Enter your initials:", "");
                if (name == null || name.equals("")) name = "AAA";
                db.update(hud.getScore(), name);
                leaderboard = db.getScores();
            } catch (SQLException s) {
                hasDB = false;
                System.out.println(s.getMessage());
            }
        }
    }

    private void drawCenteredString(Graphics g, String text, int vertical) {
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int center = (Game.WIDTH - metrics.stringWidth(text)) / 2;
        g.drawString(text, center, vertical);
    }
    
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
        
        // Set Final Score
        g.setColor(Color.WHITE);
        g.fillRect(Game.SCALE, Game.SCALE*15, Game.WIDTH-2*Game.SCALE, Game.SCALE*4);
        g.setColor(Color.BLACK);
        drawCenteredString(g, "Press 'R' to Retry", Game.SCALE*18);

        // Set Leaderboard
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
