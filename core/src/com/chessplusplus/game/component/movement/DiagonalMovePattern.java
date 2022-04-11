package com.chessplusplus.game.component.movement;

import com.chessplusplus.game.component.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.DoublePredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * DiagonalMovePattern is used to represent movement along the diagonals
 * on the board.
 *
 * Note that all diagonal movement has a constant "ratio" of 1:1, e.g. when a
 * piece moves 2 squares up the x-axis it must also move 2 squares up or down
 * along the y axis.
 *
 * For diagonal movement with a ratio that is not 1:1 use the CuringMovePattern.
 */
public class DiagonalMovePattern extends SimpleMovePattern {

    public DiagonalMovePattern(int range) {
        super(range);
    }

    public DiagonalMovePattern() {
        super();
    }

    /**
     * Creates a movement pattern that gives the piece diagonal movement
     * with a range of one square per turn.
     *
     * In normal chess this is used by the King.
     * @return DiagonalMovePattern with range = 1.
     */
    public static DiagonalMovePattern oneSquareDiagonalMovement() {
        return new DiagonalMovePattern(1);
    }

    /**
     * Creates a movement pattern that gives the piece diagonal movement
     * with an unlimited range per turn.
     *
     * In normal chess this is used by the Bishop & the Queen.
     * @return DiagonalMovePattern with unlimited range.
     */
    public static DiagonalMovePattern unlimitedDiagonalMovement() {
        return new DiagonalMovePattern(-1);
    }



    @Override
    public List<Position> getPossibleMoves(Position piece, int boardWidth, int boardHeight) {
        int maxMoveDistance = (range==-1) ? boardWidth: range;

        // diagonal defined as y = x and y = -x
        // absolute both axes results in simple test case x == y
        Predicate<Position> isDiagonalAndInRange = (move) -> {
            int x = Math.abs(piece.getX() - move.getX());
            int y = Math.abs(piece.getY() - move.getY());
            return x == y && x <= maxMoveDistance;
        };

        return IntStream
                .range(0, boardWidth * boardHeight)
                .mapToObj(i -> new Position(i%boardWidth, i/boardWidth))
                .filter(isDiagonalAndInRange)
                .filter(m -> !m.equals(piece)) // move is not own position
                .collect(Collectors.toList());
    }

}
