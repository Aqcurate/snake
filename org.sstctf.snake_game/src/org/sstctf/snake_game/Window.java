package org.sstctf.snake_game;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This class represents the game window.
 * 
 * @author Andrew Quach
 * @author Stanislav Lyakhov
 * 
 * @version 1.0.0
 */
public class Window {
    /**
     * Generates the game window with a specified width and height
     * 
     * @param width the width of the window 
     * @param height the height of the window
     * @param title the name of the game
     * @param game the game canvas
     * 
     * @version 1.0.0
     */
    public Window(int width, int height, String title, Game game) {
        JFrame frame = new JFrame(title);
        JPanel panel = (JPanel) frame.getContentPane();
        
        // Set the panel size to the game size plus 100 for the HUD
        panel.setPreferredSize(new Dimension(width, height+100));
        panel.setLayout(null);
        // Add the game canvas
        panel.add(game);
        // Add panel to the frame
        frame.pack();
        
        // Closes window upon the issue of a close command.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Makes window non-resizable.
        frame.setResizable(false);
        // Sets the default location of the frame to the middle of the screen.
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        game.start();
    }
}
