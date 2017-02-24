package org.sstctf.snake_game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
/**
 * This class represents the pellet that can be picked up by the snake.
 * The class deals with spawning the pellet
 * and rendering it.
 * 
 * It is an object of the game and thus extends GameObject.
 * 
 * @author Andrew Quach
 * @author Stanislav Lyakhov
 *
 * @version 1.0.0
 */
public class Pellet extends GameObject {

    private Handler handler;
    private boolean isPellet;

    /**
     * Creates a pellet given its X and Y coordinates.
     * Sets the ID of the object to Pellet.
     *  
     * @param posX the position of the pellet on the X axis
     * @param posY the position of the pellet on the Y axis
     * @param handler handles all game objects
     */
    public Pellet(int posX, int posY, Handler handler) {
        super(posX, posY, ID.Pellet);
        this.handler = handler;
        isPellet = true;
    }


    /**
     * Handles the random generation of a pellet object.
     * 
     * @return the coordinates of the next spawn point of the pellet
     */
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
            int[] spawn =  {random.nextInt(38)+1, random.nextInt(38)+1};
            if (checkSpace(spawn, occupiedSpaces)) {
                return spawn;
            }
        }
        return new int[]{-1, -1};
    }

    /**
     * Checks if a randomly generated spawn point is occupied.
     * Helper method for generating random spawn point for the pellet object.
     * 
     * @param spawn array containing the X and  Y coordinates of a planned spawn point
     * @param occupiedSpaces list of cood1inates of spaces occupied by the snake
     * 
     * @return true if spawn point is occupied
     * @return false if spawn point is unoccupied
     * 
     */
    private boolean checkSpace(int[] spawn, List<int[]> occupiedSpaces){
        for (int i = 0; i < occupiedSpaces.size(); i++) {
            if (Arrays.equals(occupiedSpaces.get(i), spawn)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Sets the pellet to either existing/not existing
     * 
     * @param isPellet
     */
    public void setPellet(boolean isPellet) {
        this.isPellet = isPellet;
    }

    /**
     * Override of GameObject tick method.
     * Checks if pellet exists every tick,
     * if not spawns new pellet.
     */
    @Override
    public void tick() {
        if (!isPellet) {
            setPosition(randomSpawn());
            isPellet = true;
        }
    }

    /**
     * Override of GameObject render method.
     * Handles the rendering of the pellet.
     * Yellow rectangle.
     */
    @Override
    public void render(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(getPosition()[0]*Game.SCALE, getPosition()[1]*Game.SCALE, Game.SCALE, Game.SCALE);
    }

    /**
     * Override of GameObject getBounds method.
     * Handles the hitboxes of the pellet.
     * 
     * @return the hitboxes of the pellet
     */
    @Override
    public List<Rectangle> getBounds() {
        List<Rectangle> hitboxes = new ArrayList<Rectangle>();
        hitboxes.add(new Rectangle(getPosition()[0]*Game.SCALE, getPosition()[1]*Game.SCALE, Game.SCALE, Game.SCALE));
        return hitboxes;
    }

}
