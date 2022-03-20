package com.chessplusplus.game.component.movement;

import com.chessplusplus.game.component.Position;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Position> getPossibleMoves(Position piecePosition, int boardWidth, int boardHeight) {
        List<Position> possibleMoves = new ArrayList<>();

        int rangeStart, rangeEnd; // Calculate range of possible y positions according to piece range
        if (range == -1) { // Unlimited range just sets the range to equal the board size
            rangeStart = 0;
            rangeEnd = boardHeight;
        } else { // Set range according to piece's position and range, then correct if range falls outside board
            rangeStart = piecePosition.getY() - range;
            if (rangeStart < 0) {
                rangeStart = 0;
            }

            rangeEnd = piecePosition.getY() + range;
            if (rangeEnd > boardHeight - 1) {
                rangeStart = boardHeight - 1;
            }
        }

        // Generate and add positions according to the range.
        for (int i = rangeStart; i < rangeEnd; i++) {
            Position position = new Position(piecePosition.getX(), i);
            // x is constant since this rule is for vertical movement

            if (!position.equals(piecePosition)) { // Filter out the position the piece is already in
                possibleMoves.add(position);
            }
        }

        return possibleMoves;
    }

}
