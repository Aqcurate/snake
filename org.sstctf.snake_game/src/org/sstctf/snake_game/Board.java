package org.sstctf.snake_game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the snake gameboard.
 * The class deals with the rendering of the gameboard
 * and the hitboxes of the gameboard edges.
 * 
 * It is an object of the game and thus extends GameObject.
 * 
 * @author Andrew Quach
 * @author Stanislav Lyahkov
 * 
 * @version 1.0.0
 */
public class Board extends GameObject{
    /**
     * Creates the gameboard given a width and length.
     * Sets the ID of the object to Board.
     * 
     * @param width the width of the board
     * @param length the length of the board
     */
    public Board(int width, int length) {
        super(0, 0, ID.Board);
    }

    /**
     * Mandatory override of GameObject tick method.
     * 
     * @see org.sstctf.snake_game.GameObject#tick()
     */
    @Override
    public void tick() {
    }

    /**
     * Override of GameObject render method.
     * Renders the gameboard blue with a black border.
     * 
     * @param g the graphics object that draws on the canvas
     * @see org.sstctf.snake_game.GameObject#render()
     */
    @Override
    public void render(Graphics g) {
        // Black gameboard border
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
        
        // Blue gameboard interior
        g.setColor(Color.BLUE);
        g.fillRect(Game.SCALE, Game.SCALE, Game.WIDTH-Game.SCALE*2, Game.HEIGHT-Game.SCALE*2);
    }

    /**
     * Override of GameObject getBounds method.
     * Generates the boundaries for the gameboard walls.
     * Hitboxes are represented with a list of rectangles.
     * 
     * @return hitboxes the list of rectangles that represent the hitbox
     * @see org.sstctf.snake_game.GameObject#getBounds()
     */
    @Override
    public List<Rectangle> getBounds() {
        List<Rectangle> hitboxes = new ArrayList<Rectangle>();
        // Left gameboard wall
        hitboxes.add(new Rectangle(0, 0, Game.SCALE, Game.HEIGHT));
        // Right gameboard wall
        hitboxes.add(new Rectangle(Game.WIDTH-Game.SCALE, 0, Game.SCALE, Game.HEIGHT));
        // Top gameboard wall
        hitboxes.add(new Rectangle(Game.SCALE, 0, Game.WIDTH-Game.SCALE*2, Game.SCALE));
        // Bottom gameboard wall
        hitboxes.add(new Rectangle(Game.SCALE, Game.HEIGHT-Game.SCALE, Game.WIDTH-Game.SCALE*2, Game.SCALE));
        return hitboxes;
    }
}
