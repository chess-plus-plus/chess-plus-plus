package com.chessplusplus.game.component.movement;

/**
 * HorizontalMovementComponent is used to represent movement along the "x-axis"
 * on the board, aka sideways movement.
 */
public class HorizontalMovementComponent extends SimpleMovementComponent {

    //TODO: Perhaps the default constructor should be private to reduce chance of us screwing it up
    public HorizontalMovementComponent(int range) {
        super(range);
    }

    /**
     * Creates a movement component that gives the piece horizontal movement
     * with a range of one square per turn.
     *
     * In normal chess this is used by the King.
     * @return HorizontalMovementComponent with range = 1.
     */
    public static HorizontalMovementComponent oneSquareHorizontalMovement() {
        return new HorizontalMovementComponent(1);
    }

    /**
     * Creates a movement component that gives the piece horizontal movement
     * with an unlimited range per turn.
     *
     * In normal chess this is used by the Rook & the Queen.
     * @return HorizontalMovementComponent with unlimited range.
     */
    public static HorizontalMovementComponent unlimitedHorisontalMovement() {
        return new HorizontalMovementComponent(-1);
    }

}
