package com.chessplusplus.game.component.movement;

/**
 * VerticalMovementComponent is used to represent movement along the "y-axis"
 * on the board, aka upwards and downwards movement.
 */
public class VerticalMovementComponent extends SimpleMovementComponent {

    public VerticalMovementComponent(int range) {
        super(range);
    }

    /**
     * Creates a movement component that gives the piece vertical movement
     * with a range of one square per turn.
     *
     * In normal chess this is used by the King and the Pawns.
     * @return HorizontalMovementComponent with range = 1.
     */
    public static VerticalMovementComponent oneSquareVerticalMovement() {
        return new VerticalMovementComponent(1);
    }

    /**
     * Creates a movement component that gives the piece vertical movement
     * with an unlimited range per turn.
     *
     * In normal chess this is used by the Rook & the Queen.
     * @return HorizontalMovementComponent with unlimited range.
     */
    public static VerticalMovementComponent unlimitedVerticalMovement() {
        return new VerticalMovementComponent(-1);
    }

}
