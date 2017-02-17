package org.sstctf.snake_game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Game Board Class
public class Board extends GameObject{
    private Handler handler;
    private List<int[]> unoccupiedSpaces = new ArrayList<int[]>();
    
	public Board(int width, int length, Handler handler){
    	super(0, 0, ID.Board);
    	this.handler = handler;
        for (int i = 1; i < (Game.WIDTH/Game.SCALE) - 1; i++) {
        	for (int j = 1; j < (Game.HEIGHT/Game.SCALE) - 1; j++) {
        		unoccupiedSpaces.add(new int[] {i, j});
        	}
        }
    }

    public List<int[]> getUnoccupiedSpaces() {
		return unoccupiedSpaces;
	}

	public void removeUnoccupiedSpace(int[] space) {
    	for (int i = 0; i < unoccupiedSpaces.size(); i++) {
    		if (Arrays.equals(unoccupiedSpaces.get(i), space)) {
    			unoccupiedSpaces.remove(i);
    			break;
    		}
    	}
	}
	
	public void addUnoccupiedSpace(int[] space) {
		unoccupiedSpaces.add(space);
	}
	
	@Override
	public void tick() {
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		g.setColor(Color.BLUE);
		g.fillRect(Game.SCALE, Game.SCALE, Game.WIDTH-32, Game.HEIGHT-32);
	}

	@Override
	public List<Rectangle> getBounds() {
		List<Rectangle> hitboxes = new ArrayList<Rectangle>();
		hitboxes.add(new Rectangle(0, 0, Game.SCALE, Game.HEIGHT));
		hitboxes.add(new Rectangle(Game.WIDTH-Game.SCALE, 0, Game.SCALE, Game.HEIGHT));
		hitboxes.add(new Rectangle(Game.SCALE, 0, Game.WIDTH-32, Game.SCALE));
		hitboxes.add(new Rectangle(Game.SCALE, Game.HEIGHT-Game.SCALE, Game.WIDTH-32, Game.SCALE));
		return hitboxes;
	}
}
