package org.sstctf.snake_game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
/**
 * This class represents the controllable character; the snake.
 * The class handles player movement 
 * as well as growth and collision
 * 
 * It is an object of the game and thus extends GameObject.
 * 
 * @author Andrew Quach
 * @author Stanislav Lyakhov
 *
 * @version 1.0.0
 */
public class Snake extends GameObject {
    private int length;
    // Direction is where the snake actually goes
    // Input direction is where the play wants it to go
    private Direction direction;
    private Direction inputDirection;
    private List<int[]> snakeParts = new ArrayList<int[]>();

    private Handler handler;
    private Game game;
    
    /**
     * Initializes the snake with a position.
     * The handler and game are passed to find other
     * game objects and call for the game end respectively.
     * 
     * @param posX position of the snake on the X axis
     * @param posY position of the snake on the Y axis
     * @param handler handles all game objects
     * @param game handles the game
     */
    public Snake(int posX, int posY, Handler handler, Game game) {
        super(posX, posY, ID.Snake);
        direction = Direction.NONE;
        inputDirection = Direction.NONE;
        snakeParts.add(getPosition());
        length = snakeParts.size();

        this.handler = handler;
        this.game = game;
    }

    /**
     * Move in a certain direction.
     * 
     * @param dir direction of movement of the snake
     */
    // Moving the snake based on input direction
    private void move(Direction dir) {
        Direction lastDirection = direction;
        // If the length is not 1, the snake cannot move into itself
        if (length == 1 || lastDirection != dir.opposite()) direction = dir;
        // Keep track of the last direction
        int[] lastPosition = snakeParts.get(snakeParts.size() - 1);
        // Set a new position based on the direction given
        switch (direction) {
        case NORTH:
            setPosition(new int[] {lastPosition[0], lastPosition[1]-1});
            break;
        case SOUTH:
            setPosition(new int[] {lastPosition[0], lastPosition[1]+1});
            break;
        case WEST:
            setPosition(new int[] {lastPosition[0]-1, lastPosition[1]});
            break;
        case EAST:
            setPosition(new int[] {lastPosition[0]+1, lastPosition[1]});
            break;
        default:
            break;
        }
    }
    
    /**
     * Setting the snake input direction
     * 
     * @param inputDirection
     * @see org.sstctf.snake_game.Snake#inputDirection
     */
    public void setInputDirection(Direction inputDirection) {
        this.inputDirection = inputDirection;
    }
    
    /**
     * Setting the snake length
     * 
     * @return length of the snake
     * @see org.sstctf.snake_game.Snake#length
     */
    
    public int getLength() {
        return length;
    }

    /**
     * Getting the list of snake parts
     * 
     * @return list of snake parts
     * @see org.sstctf.snake_game.Snake#snakeParts
     */
    public List<int[]> getSnakeParts() {
        return snakeParts;
    }

    /**
     * Describes collision of the snake with different objects.
     * Handles growth of snake when snake collides with a Pellet.
     * Handles snake collision with the wall.
     * Handles snake collision with its body.
     */
    private void collision() {
        for (int i = 0; i < handler.objects.size(); i++) {
            GameObject temp = handler.objects.get(i);
            // Game over if the Snake head collides with its body
            if (temp.getID() == ID.Snake) {
                for (int j = 0; j < temp.getBounds().size() - 1; j++) {
                    // Check if the head and body collision boxes intersect
                    if (getBounds().get(getBounds().size()-1).intersects(temp.getBounds().get(j))) {
                        game.onDeath();
                    }
                }
            }
            // Game over if the Snake collides into a wall
            if (temp.getID() == ID.Board) {
                for (int j = 0; j < temp.getBounds().size(); j++) {
                    // Check if the head and wall collision boxes intersect
                    if (getBounds().get(getBounds().size()-1).intersects(temp.getBounds().get(j))) {
                        game.onDeath();
                    }
                }
            }
            // The snake increases in length if it gets a pellet
            if (temp.getID() == ID.Pellet) {
                for (int j = 0; j < temp.getBounds().size(); j++) {
                    // Checking if the head and pellet collision boxes intersect
                    if (getBounds().get(getBounds().size()-1).intersects(temp.getBounds().get(j))) {
                        ((Pellet) temp).setPellet(false);
                        length++;
                    }
                }
            }
        }
    }

    /**
     * Override of GameObject tick method.
     * Calls a move function during every tick.
     * Checks collision every tick.
     * Removes last part of snake.
     * @see org.sstctf.snake_game.GameObject#tick()
     */
    @Override
    public void tick() {
        move(inputDirection);
        if (direction != Direction.NONE) snakeParts.add(getPosition());
        collision();
        if (snakeParts.size() != length) snakeParts.remove(0);
    }

    /**
     * Override of GameObject render method.
     * Renders the snake using green rectangles.
     * 
     * @param g the graphics object that draws on the canvas
     * @see org.sstctf.snake_game.GameObject#render()
     */
    @Override
    public void render(Graphics g) {
        g.setColor(Color.GREEN);
        for (int[] part : snakeParts) {
            g.fillRect(part[0]*Game.SCALE, part[1]*Game.SCALE, Game.SCALE, Game.SCALE);
        }
    }


    /**
     * Override of GameObject getBounds method.
     * Handles the hitboxes of the snake
     * 
     * @return hitboxes the collision hitboxes of the snake
     * @see org.sstctf.snake_game.GameObject#getBounds()
     */
    @Override
    public List<Rectangle> getBounds() {
        List<Rectangle> hitboxes = new ArrayList<Rectangle>();
        for (int[] part : snakeParts) {
            hitboxes.add(new Rectangle(part[0]*Game.SCALE, part[1]*Game.SCALE, Game.SCALE, Game.SCALE));
        }
        return hitboxes;
    }
}
