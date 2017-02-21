package org.sstctf.snake_game;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

// Handles all the objects tick() and render() methods
public class Handler {
    List<GameObject> objects = new ArrayList<GameObject>();

    public void tick() {
        for (int i = 0; i < objects.size(); i++) {
            GameObject temp = objects.get(i);
            temp.tick();
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < objects.size(); i++) {
            GameObject temp = objects.get(i);
            temp.render(g);
        }
    }

    public void addObject(GameObject object) {
        objects.add(object);
    }

    public void removeObject(GameObject object) {
        objects.remove(object);
    }

    public void removeAll() {
        objects.clear();
    }
}
