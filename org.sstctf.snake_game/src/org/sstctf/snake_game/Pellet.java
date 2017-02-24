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
                // Occupied Spaces are simply spaces where the snake parts are
                occupiedSpaces = (((Snake) temp).getSnakeParts());
            }
        }
        
        // Generate random coordinates to spawn
        Random random = new Random();
        
        // Check if the board is full
        while (occupiedSpaces.size() != 1444) {
            int[] spawn =  {random.nextInt(38)+1, random.nextInt(38)+1};
            if (checkSpace(spawn, occupiedSpaces)) {
                return spawn;
            }
        }
        
        // Return nonexistent coordinates if the board is full
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
     * @param isPellet isPellet is a boolean detailing whether a pellet
     * exists on the current board or not
     */
    public void setPellet(boolean isPellet) {
        this.isPellet = isPellet;
    }

    /**
     * Override of GameObject tick method.
     * Checks if pellet exists every tick,
     * if not spawns new pellet.
     * 
     * @see org.sstctf.snake_game.GameObject#tick()
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
     * 
     * @param g the graphics object that draws on the canvas
     * @see org.sstctf.snake_game.GameObject#render()
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
     * @see org.sstctf.snake_game.GameObject#getBounds()
     */
    @Override
    public List<Rectangle> getBounds() {
        List<Rectangle> hitboxes = new ArrayList<Rectangle>();
        hitboxes.add(new Rectangle(getPosition()[0]*Game.SCALE, getPosition()[1]*Game.SCALE, Game.SCALE, Game.SCALE));
        return hitboxes;
    }

}
