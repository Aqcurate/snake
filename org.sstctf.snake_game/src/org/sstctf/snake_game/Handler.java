package org.sstctf.snake_game;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles all operations on all game objects.
 * Specifically, it allows for easy calling of tick() and
 * render() methods on all game objects.
 * 
 * @author Andrew Quach
 * @author Stanislav Lyahkov
 *
 * @version 1.0.0
 */
public class Handler {
    List<GameObject> objects = new ArrayList<GameObject>();

    /**
     * Loops through all game objects and calls each objects'
     * tick method.
     */
    public void tick() {
        for (int i = 0; i < objects.size(); i++) {
            GameObject temp = objects.get(i);
            temp.tick();
        }
    }
    
    /**
     * Loops through all game objects and calls each objects'
     * render method passing the graphics object.
     * 
     * @param g the graphics object that draws on the canvas
     */
    public void render(Graphics g) {
        for (int i = 0; i < objects.size(); i++) {
            GameObject temp = objects.get(i);
            temp.render(g);
        }
    }

    /**
     * Adds an object to the objects list.
     * @param object a GameObject to be added to objects list
     */
    public void addObject(GameObject object) {
        objects.add(object);
    }

    /**
     * Removes an object to the objects list.
     * @param object a GameObject to be removed from the objects list
     */
    public void removeObject(GameObject object) {
        objects.remove(object);
    }

    /**
     * Clears out all objects from the objects list.
     */
    public void removeAll() {
        objects.clear();
    }
}
