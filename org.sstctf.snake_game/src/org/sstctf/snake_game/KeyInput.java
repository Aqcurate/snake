package org.sstctf.snake_game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Acts as a key listener for the Game object.
 * Handles movement controls and reseting the game.
 * 
 * @author Andrew Quach
 * @author Stanislav Lyakhov
 *
 * @version 1.0.0
 */
public class KeyInput extends KeyAdapter {
    private Handler handler;
    private Game game;

    /**
     * KeyInput is initialized with only handler and game.
     * Handler allows for KeyInput to loop through game objects.
     * 
     * @param handler handles all game objects
     */
    public KeyInput(Handler handler) {
        this.handler = handler;
    }

    /**
     * Overrides keyPressed() method in KeyAdapter.
     * Every time a key is pressed, the method is called with
     * a specific key event. 
     * The key event is decoded and translated into keyboard presses.
     * WASD moves the snake, R restarts the game, <ESC> exits the game.
     * 
     * @param event event is the key that was pressed
     */
    @Override
    public void keyPressed(KeyEvent event) {
        int key = event.getKeyCode();

        for (int i = 0; i < handler.objects.size(); i++) {
            GameObject temp = handler.objects.get(i);

            if (temp.getID() == ID.Snake) {
                // WASD Controls
                if (key == KeyEvent.VK_W) ((Snake) temp).setInputDirection(Direction.NORTH);
                if (key == KeyEvent.VK_A) ((Snake) temp).setInputDirection(Direction.WEST);
                if (key == KeyEvent.VK_S) ((Snake) temp).setInputDirection(Direction.SOUTH);
                if (key == KeyEvent.VK_D) ((Snake) temp).setInputDirection(Direction.EAST);
            }
        }
        
        // Restart the game by removing all objects and re-adding them
        if (key == KeyEvent.VK_R) {
            handler.removeAll();
            handler.addObject(new Board(0, 0));
            handler.addObject(new Snake(1, 1, handler, game));
            handler.addObject(new Pellet(5, 5, handler));
            Game.gameState = State.GAME;
        }

        // Escape exits the game
        if (key == KeyEvent.VK_ESCAPE) System.exit(1);
    }
}
