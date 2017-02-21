package org.sstctf.snake_game;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

// Window for the game
public class Window extends Canvas {

    private static final long serialVersionUID = -4453391185232301469L;

    public Window(int width, int height, String title, Game game) {
        JFrame frame = new JFrame(title);

        frame.setPreferredSize(new Dimension(width+100, height+100));
        frame.setMaximumSize(new Dimension(width+100, height+100));
        frame.setMinimumSize(new Dimension(width+100, height+100));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().add(game);

        frame.setVisible(true);

        game.start();
    }
}
