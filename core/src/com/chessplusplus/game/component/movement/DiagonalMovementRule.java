package com.chessplusplus.game.component.movement;

import com.chessplusplus.game.component.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * DiagonalMovementRule is used to represent movement along the diagonals
 * on the board.
 *
 * Note that all diagonal movement has a constant "ratio" of 1:1, e.g. when a
 * piece moves 2 squares up the x-axis it must also move 2 squares up or down
 * along the y axis.
 *
 * For diagonal movement with a ratio that is not 1:1 use the CuringMovementRule.
 */
public class DiagonalMovementRule extends SimpleMovementRule {

    public DiagonalMovementRule(int range) {
        super(range);
    }

    /**
     * Creates a movement rule that gives the piece diagonal movement
     * with a range of one square per turn.
     *
     * In normal chess this is used by the King.
     * @return HorizontalMovementRule with range = 1.
     */
    public static DiagonalMovementRule oneSquareDiagonalMovement() {
        return new DiagonalMovementRule(1);
    }

    /**
     * Creates a movement rule that gives the piece diagonal movement
     * with an unlimited range per turn.
     *
     * In normal chess this is used by the Bishop & the Queen.
     * @return HorizontalMovementRule with unlimited range.
     */
    public static DiagonalMovementRule unlimitedDiagonalMovement() {
        return new DiagonalMovementRule(-1);
    }

    @Override
    public List<Position> getPossibleMoves(Position piecePosition, int boardWidth, int boardHeight) {
        List<Position> possibleMoves = new ArrayList<>();
        // This algorithm for generating possible moves has a time-complexity of O(x*y) where
        // x = board width and y = board height. This is inefficient, but for chess this is
        // acceptable, since board is never going to be very large.

        // Start by generating all possible positions on the board
        List<Position> allPositions = new ArrayList<>();
        for (int x = 0; x < boardWidth; x++) {
            for (int y = 0; y < boardHeight; y++) {
                Position position = new Position(x, y);
                allPositions.add(position);
            }
        }

        // Then we filter out potential positions according to 2 criteria:
        // 1: The potential position should not be the same position as the piece
        // 2; The difference between the potential position's and the piece position's
        //      x and y values should be equal, since this indicates that the position
        //      is diagonal to the piece's position
        for (Position potentialPosition : allPositions) {
            int xDiff = Math.abs(piecePosition.getX() - potentialPosition.getX());
            int yDiff = Math.abs(piecePosition.getY() - potentialPosition.getY());

            if (potentialPosition != piecePosition && xDiff == yDiff) {
                possibleMoves.add(potentialPosition);
            }
        }

        return possibleMoves;

    }

}
