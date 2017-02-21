package org.sstctf.snake_game;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

// Window for the game
public class Window {
    public Window(int width, int height, String title, Game game) {
        JFrame frame = new JFrame(title);
        JPanel panel = (JPanel) frame.getContentPane();
        
        panel.setPreferredSize(new Dimension(width, height+100));
        panel.setLayout(null);
        panel.add(game);
        frame.pack();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);

        game.start();
    }
}
