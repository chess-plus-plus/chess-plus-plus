package com.chessplusplus.game.component.movement;

/**
 * DiagonalMovementComponent is used to represent movement along the diagonals
 * on the board.
 *
 * Note that all diagonal movement has a constant "ratio" of 1:1, e.g. when a
 * piece moves 2 squares up the x-axis it must also move 2 squares up or down
 * along the y axis.
 *
 * For diagonal movement with a ratio that is not 1:1 use the CuringMovementComponent.
 */
public class DiagonalMovementComponent extends SimpleMovementComponent {

    public DiagonalMovementComponent(int range) {
        super(range);
    }

    /**
     * Creates a movement component that gives the piece diagonal movement
     * with a range of one square per turn.
     *
     * In normal chess this is used by the King.
     * @return HorizontalMovementComponent with range = 1.
     */
    public static DiagonalMovementComponent oneSquareDiagonalMovement() {
        return new DiagonalMovementComponent(1);
    }

    /**
     * Creates a movement component that gives the piece diagonal movement
     * with an unlimited range per turn.
     *
     * In normal chess this is used by the Bishop & the Queen.
     * @return HorizontalMovementComponent with unlimited range.
     */
    public static DiagonalMovementComponent unlimitedDiagonalMovement() {
        return new DiagonalMovementComponent(-1);
    }

}
