package org.sstctf.snake_game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
    private Handler handler;
    private Game game;

    public KeyInput(Handler handler, Game game) {
        this.handler = handler;
        this.game = game;
    }

    public void keyPressed(KeyEvent event) {
        int key = event.getKeyCode();

        for (int i = 0; i < handler.objects.size(); i++) {
            GameObject temp = handler.objects.get(i);

            if (temp.getID() == ID.Snake) {
                if (key == KeyEvent.VK_W) ((Snake) temp).setInputDirection(Direction.NORTH);
                if (key == KeyEvent.VK_A) ((Snake) temp).setInputDirection(Direction.WEST);
                if (key == KeyEvent.VK_S) ((Snake) temp).setInputDirection(Direction.SOUTH);
                if (key == KeyEvent.VK_D) ((Snake) temp).setInputDirection(Direction.EAST);
            }
        }

        if (key == KeyEvent.VK_R) {
            handler.removeAll();
            handler.addObject(new Board(0, 0));
            handler.addObject(new Snake(1, 1, handler, game));
            handler.addObject(new Pellet(5, 5, handler));
            Game.gameState = State.GAME;
        }

        if (key == KeyEvent.VK_ESCAPE) System.exit(1);
    }
}
