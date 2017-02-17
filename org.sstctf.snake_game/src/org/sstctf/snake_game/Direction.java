package org.sstctf.snake_game;

// Enumerating out directions the Snake can move
public enum Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST,
    NONE;
    
	// Finding the complements to the directions
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