package org.sstctf.snake_game;

import java.awt.Color;
import java.awt.Graphics;

public class HUD {

    private int length;
    private Handler handler;

    public HUD(Handler handler) {
        this.handler = handler;
    }

    public void tick() {
        for (int i = 0; i < handler.objects.size(); i++) {
            GameObject temp = handler.objects.get(i);
            if (temp.getID() == ID.Snake) {
                length = ((Snake) temp).getLength();
            }
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawString("Score: " + length, 16, Game.HEIGHT+32);
    }

    public int getScore() {
        return length;
    }
}
