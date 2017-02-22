package org.sstctf.snake_game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Pellet extends GameObject {

    private Handler handler;
    private boolean isPellet;

    public Pellet(int posX, int posY, Handler handler) {
        super(posX, posY, ID.Pellet);
        this.handler = handler;
        isPellet = true;
    }

    // Makes sure the pellet spawns in an non occupied area
    public int[] randomSpawn() {
        List<int[]> occupiedSpaces = null;
        for (int i = 0; i < handler.objects.size(); i++) {
            GameObject temp = handler.objects.get(i);
            if (temp.getID() == ID.Snake) {
                occupiedSpaces = (((Snake) temp).getSnakeParts());
            }
        }
        Random random = new Random();
        while (occupiedSpaces.size() != 1444) {
            int[] i =  {random.nextInt(38)+1, random.nextInt(38)+1};
            if (!occupiedSpaces.contains(i)) {
                return i;
            }
        }
        return new int[]{-1, -1};
    }

    public void setPellet(boolean isPellet) {
        this.isPellet = isPellet;
    }

    @Override
    public void tick() {
        if (!isPellet) {
            setPosition(randomSpawn());
            isPellet = true;
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(getPosition()[0]*Game.SCALE, getPosition()[1]*Game.SCALE, Game.SCALE, Game.SCALE);
    }

    @Override
    public List<Rectangle> getBounds() {
        List<Rectangle> hitboxes = new ArrayList<Rectangle>();
        hitboxes.add(new Rectangle(getPosition()[0]*Game.SCALE, getPosition()[1]*Game.SCALE, Game.SCALE, Game.SCALE));
        return hitboxes;
    }

}
