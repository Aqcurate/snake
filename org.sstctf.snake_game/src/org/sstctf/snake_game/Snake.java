package org.sstctf.snake_game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Snake extends GameObject {
    private int length;
    // Direction is where the snake actually goes
    // Input direction is where the play wants it to go
    private Direction direction;
    private Direction inputDirection;
    private List<int[]> snakeParts = new ArrayList<int[]>();

    private Handler handler;
    private Game game;

    public Snake(int posX, int posY, Handler handler, Game game) {
        super(posX, posY, ID.Snake);
        direction = Direction.NONE;
        inputDirection = Direction.NONE;
        snakeParts.add(getPosition());
        length = snakeParts.size();

        this.handler = handler;
        this.game = game;
    }

    // Moving the snake based on input direction
    public void move(Direction dir) {
        Direction lastDirection = direction;
        // If the length is not 1, the snake cannot move into itself
        if (length == 1 || lastDirection != dir.opposite()) direction = dir;
        int[] lastPosition = snakeParts.get(snakeParts.size() - 1);
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

    public void setInputDirection(Direction inputDirection) {
        this.inputDirection = inputDirection;
    }

    public int getLength() {
        return length;
    }

    public List<int[]> getSnakeParts() {
        return snakeParts;
    }

    public void collision() {
        for (int i = 0; i < handler.objects.size(); i++) {
            GameObject temp = handler.objects.get(i);
            // Game over if the Snake head collides with its body
            if (temp.getID() == ID.Snake) {
                for (int j = 0; j < temp.getBounds().size() - 1; j++) {
                    if (getBounds().get(getBounds().size()-1).intersects(temp.getBounds().get(j))) {
                        game.onDeath();
                    }
                }
            }
            // Game over if the Snake collides into a wall
            if (temp.getID() == ID.Board) {
                for (int j = 0; j < temp.getBounds().size(); j++) {
                    if (getBounds().get(getBounds().size()-1).intersects(temp.getBounds().get(j))) {
                        game.onDeath();
                    }
                }
            }
            // The snake increases in length if it gets a pellet
            if (temp.getID() == ID.Pellet) {
                for (int j = 0; j < temp.getBounds().size(); j++) {
                    if (getBounds().get(getBounds().size()-1).intersects(temp.getBounds().get(j))) {
                        ((Pellet) temp).setPellet(false);
                        length++;
                    }
                }
            }
        }
    }

    @Override
    public void tick() {
        move(inputDirection);
        if (direction != Direction.NONE) snakeParts.add(getPosition());
        collision();
        if (snakeParts.size() != length) snakeParts.remove(0);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.GREEN);
        for (int[] part : snakeParts) {
            g.fillRect(part[0]*Game.SCALE, part[1]*Game.SCALE, Game.SCALE, Game.SCALE);
        }
    }

    @Override
    public List<Rectangle> getBounds() {
        List<Rectangle> hitboxes = new ArrayList<Rectangle>();
        for (int[] part : snakeParts) {
            hitboxes.add(new Rectangle(part[0]*Game.SCALE, part[1]*Game.SCALE, Game.SCALE, Game.SCALE));
        }
        return hitboxes;
    }
}
