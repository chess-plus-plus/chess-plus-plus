package com.chessplusplus.game.component.movement;

/**
 * A simple movement rule is an abstract class that is used for all
 * movement rules that can be characterised as a directional movement with a
 * (possibly limited) range. E.g. horizontal, vertical, and diagonal movement.
 * <p>
 * The range in SimpleMovementRange defines how far a piece can move in its
 * direction in a single move. I.e. if a range is 4, the piece can move up to 4
 * squares in the given direction in one turn. For unlimited range, a value of
 * -1 is used instead.
 */
public abstract class SimpleMovementRule implements MovementRule {

    public int range;

    public SimpleMovementRule(int range) {
        this.range = range;
    }

    public boolean hasUnlimitedRange() {
        return range == -1;
    }

}
