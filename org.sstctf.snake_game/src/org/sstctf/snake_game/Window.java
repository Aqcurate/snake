package org.sstctf.snake_game;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

// Window for the game
/**
 * This class generates the game window.
 * 
 * @author Andrew Quach
 * @author Stanislav Lyakhov
 *
 */
public class Window {
    public Window(int width, int height, String title, Game game) {
        JFrame frame = new JFrame(title);
        JPanel panel = (JPanel) frame.getContentPane();
        
        panel.setPreferredSize(new Dimension(width, height+100));
        panel.setLayout(null);
        panel.add(game);
        frame.pack();
        
        //Closes window upon the issue of a close command.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Makes window non-resizable.
        frame.setResizable(false);
        //Sets the default location of the frame to the middle of the screen.
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);

        game.start();
    }
}
