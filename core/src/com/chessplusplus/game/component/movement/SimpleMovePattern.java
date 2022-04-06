package com.chessplusplus.game.component.movement;

/**
 * A simple movement pattern is an abstract class that is used for all
 * movement patterns that can be characterised as a directional movement with a
 * (possibly limited) range. E.g. horizontal, vertical, and diagonal movement.
 * <p>
 * The range defines how far a piece can move in its direction in a single move.
 * I.e. if a range is 4, the piece can move up to 4squares in the given direction in one turn.
 * For unlimited range, a value of -1 is used instead.
 */
public abstract class SimpleMovePattern implements MovePattern {

    public int range;

    /**
     * Default constructor, defines range.
     *
     * @param range Range of the movement pattern.
     */
    public SimpleMovePattern(int range) {
        this.range = range;
    }

    /**
     * Alternative constructor that gives a pattern unlimited range.
     */
    public SimpleMovePattern() {
        this(-1);
    }

    /**
     * Utility method to check if the pattern's range is unlimited.
     *
     * @return true for unlimited range, false otherwise.
     */
    public boolean hasUnlimitedRange() {
        return range == -1;
    }

}
