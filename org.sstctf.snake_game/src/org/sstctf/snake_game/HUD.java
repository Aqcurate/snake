package org.sstctf.snake_game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * This class handles all rendering of the HUD
 * during gameplay.
 * The HUD contains information about the current
 * score and displays this information on the bottom
 * of the screen.
 * 
 * @author Andrew Quach
 * @author Stanislav Lyakhov
 *
 * @version 1.0.0
 */
public class HUD {

    private int length;
    private Handler handler;

    /**
     * The HUD is initialized with only the handler.
     * @param handler the handler handles all game objects
     */
    public HUD(Handler handler) {
        this.handler = handler;
    }

    /**
     * Every tick, HUD uses the handler to find the Snake object
     * and get the current length of the snake.
     */
    public void tick() {
        for (int i = 0; i < handler.objects.size(); i++) {
            GameObject temp = handler.objects.get(i);
            if (temp.getID() == ID.Snake) {
                // Get current length of the snake
                length = ((Snake) temp).getLength();
            }
        }
    }

    /**
     * Renders the HUD white with a black border.
     * Draws the current score inside the HUD.
     * 
     * @param g the graphics object that draws on the canvas
     */
    public void render(Graphics g) {
        // Black border of the HUD on the bottom
        g.setColor(Color.BLACK);
        g.fillRect(0, Game.HEIGHT, Game.WIDTH, Game.HEIGHT+128);
        
        // White interior of the HUD
        g.setColor(Color.WHITE);
        g.fillRect(Game.SCALE, Game.HEIGHT, Game.WIDTH-Game.SCALE*2, 128-Game.SCALE*3);
        
        // Drawing the current length of the snake
        g.setColor(Color.BLACK);
        g.setFont(new Font("Helvetica", Font.PLAIN, 32));
        g.drawString("Snake Length: " + length, 32, Game.HEIGHT+48);
    }

    /**
     * The current length of the snake.
     * 
     * @return the length of the snake
     * @see org.sstctf.snake_game.HUD#length
     */
    public int getScore() {
        return length;
    }
}
