package com.chessplusplus.game.component.movement;

import com.badlogic.ashley.core.Component;

/**
 * A simple movement component is an abstract class that is used for all
 * movement components that can be characterised as a directional movement with a
 * (possibly limited) range. E.g. horizontal, vertical, and diagonal movement.
 * <p>
 * The range in SimpleMovementComponent defines how far a piece can move in its
 * direction in a single move. I.e. if a range is 4, the piece can move up to 4
 * squares in the given direction in one turn. For unlimited range, a value of
 * -1 is used instead.
 */
public abstract class SimpleMovementComponent implements Component {

    public int range;

    public SimpleMovementComponent(int range) {
        this.range = range;
    }

    public boolean hasUnlimitedRange() {
        return range == -1;
    }

}
