package org.sstctf.snake_game;

import java.awt.Color;
import java.awt.Font;
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
        g.fillRect(0, Game.HEIGHT, Game.WIDTH, Game.HEIGHT+128);
        g.setColor(Color.WHITE);
        g.fillRect(Game.SCALE, Game.HEIGHT, Game.WIDTH-Game.SCALE*2, 128-Game.SCALE*3);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Helvetica", Font.PLAIN, 32));
        g.drawString("Snake Length: " + length, 32, Game.HEIGHT+48);
    }

    public int getScore() {
        return length;
    }
}
