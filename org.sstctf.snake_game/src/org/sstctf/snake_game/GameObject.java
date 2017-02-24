package org.sstctf.snake_game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

/**
 * Abstract class that is the parent of all game objects.
 * Forces child classes to implement tick(), render(), and getBounds() methods.
 * Stores the current position and ID of all game objects.
 * 
 * @author Andrew Quach
 * @author Stanislav Lyakhov
 *
 * @version 1.0.0
 */
public abstract class GameObject {
    private int[] position;
    private ID id;

    /**
     * Initializes the object with a position and an
     * associated id.
     * 
     * @param posX posX is the x-coord of the object
     * @param posY posX is the y-coord of the object
     * @param id id is the id of the object
     */
    public GameObject(int posX, int posY, ID id) {
        position = new int[] {posX, posY};
        this.id = id;
    }

    /**
     * tick() accounts for game updates every tick.
     */
    public abstract void tick();
    
    /**
     * Render takes in a graphics object and draws the screen repeatedly
     * @param g
     */
    public abstract void render(Graphics g);
    
    /**
     * Generates the boundaries for the game object.
     * Used for collision checking.
     * @return the list of rectangles that represent the hitbox
     */
    public abstract List<Rectangle> getBounds();
    
    /**
     * The position of the object.
     * 
     * @return the position of the object using an array with x and y
     * coordinates
     * @see org.sstctf.snake_game.GameObject#position
     */
    public int[] getPosition() {
        return position;
    }

    /**
     * Setting the position of the object.
     * 
     * @param position sets the position of the object using an array with
     * x and y coordinates
     * @see org.sstctf.snake_game.GameObject#position
     */
    public void setPosition(int[] position) {
        this.position = position;
    }

    /**
     * The id of the object.
     * 
     * @return the id of the object
     * @see org.sstctf.snake_game.GameObject#id
     */
    public ID getID() {
        return id;
    }

    /**
     * Setting the id of the object.
     * 
     * @param id sets the id of the object
     * @see org.sstctf.snake_game.GameObject#id
     */
    public void setID(ID id) {
        this.id = id;
    }
}
