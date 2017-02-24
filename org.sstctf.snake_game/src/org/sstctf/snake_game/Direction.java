package org.sstctf.snake_game;

/**
 * This class enumerates out the directions the snake can move.
 * 
 * @author Andrew Quach
 * @author Stanislav Lyahkov
 *
 * @version 1.0.0
 */
public enum Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST,
    NONE;

    /**
     * The method finds the complements to the direction
     * of the object that calls it.
     * 
     * @return the opposite of the direction of the object
     * that calls it
     */
    public Direction opposite() {
        switch(this) {
            case NORTH: return Direction.SOUTH;
            case SOUTH: return Direction.NORTH;
            case EAST: return Direction.WEST;
            case WEST: return Direction.EAST;
            default: return Direction.NONE;
        }
    }
}