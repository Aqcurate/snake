package org.sstctf.snake_game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

// Abstract class for all game objects
public abstract class GameObject {
	private int[] position;
	private ID id;
	
	public GameObject(int posX, int posY, ID id) {
		position = new int[] {posX, posY};
		this.id = id;
	}
	
	// tick() executes every tick
	public abstract void tick();
	// Render renders out the object
	public abstract void render(Graphics g);
	// getBounds() detail the boundaries of the object
	public abstract List<Rectangle> getBounds();
	
	public int[] getPosition() {
		return position;
	}
	
	public void setPosition(int[] position) {
		this.position = position;
	}
	
	public ID getID() {
		return id;
	}

	public void setID(ID id) {
		this.id = id;
	}
}
