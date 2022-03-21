package com.chessplusplus.game.component.movement;

import com.chessplusplus.game.component.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * HorizontalMovementRule is used to represent movement along the "x-axis"
 * on the board, aka sideways movement.
 */
public class HorizontalMovementRule extends SimpleMovementRule {

    //TODO: Perhaps the default constructor should be private to reduce chance of us screwing it up
    public HorizontalMovementRule(int range) {
        super(range);
    }

    /**
     * Creates a movement rule that gives the piece horizontal movement
     * with a range of one square per turn.
     *
     * In normal chess this is used by the King.
     * @return HorizontalMovementRule with range = 1.
     */
    public static HorizontalMovementRule oneSquareHorizontalMovement() {
        return new HorizontalMovementRule(1);
    }

    /**
     * Creates a movement rule that gives the piece horizontal movement
     * with an unlimited range per turn.
     *
     * In normal chess this is used by the Rook & the Queen.
     * @return HorizontalMovementRule with unlimited range.
     */
    public static HorizontalMovementRule unlimitedHorisontalMovement() {
        return new HorizontalMovementRule(-1);
    }

    @Override
    public List<Position> getPossibleMoves(Position piecePosition, int boardWidth, int boardHeight) {
        List<Position> possibleMoves = new ArrayList<>();

        int rangeStart, rangeEnd; // Calculate range of possible y positions according to piece range
        if (range == -1) { // Unlimited range just sets the range to equal the board size
            rangeStart = 0;
            rangeEnd = boardWidth;
        } else { // Set range according to piece's position and range, then correct if range falls outside board
            rangeStart = piecePosition.getX() - range;
            if (rangeStart < 0) {
                rangeStart = 0;
            }

            rangeEnd = piecePosition.getX() + range;
            if (rangeEnd > boardWidth - 1) {
                rangeStart = boardWidth - 1;
            }
        }

        // Generate and add positions according to the range.
        for (int i = rangeStart; i < rangeEnd; i++) {
            Position position = new Position(i, piecePosition.getY());
            // y is constant since this rule is for horizontal movement

            if (!position.equals(piecePosition)) { // Filter out the position the piece is already in
                possibleMoves.add(position);
            }
        }

        return possibleMoves;
    }
}
