package com.chessplusplus.model.piece.movement;

import com.chessplusplus.model.board.Position;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * VerticalMovePattern is used to represent movement along the "y-axis"
 * on the board, aka upwards and downwards movement.
 */
public class VerticalMovePattern extends SimpleMovePattern {

    public VerticalMovePattern(int range) {
        super(range);
    }

    public VerticalMovePattern() {
        super();
    }

    /**
     * Creates a movement Pattern that gives the piece vertical movement
     * with a range of one square per turn.
     *
     * In normal chess this is used by the King and the Pawns.
     * @return VerticalMovePattern with range = 1.
     */
    public static VerticalMovePattern oneSquareVerticalMovement() {
        return new VerticalMovePattern(1);
    }

    /**
     * Creates a movement pattern that gives the piece vertical movement
     * with an unlimited range per turn.
     *
     * In normal chess this is used by the Rook & the Queen.
     * @return VerticalMovePattern with unlimited range.
     */
    public static VerticalMovePattern unlimitedVerticalMovement() {
        return new VerticalMovePattern(-1);
    }

    @Override
    public List<Position> getPossibleMoves(Position piece, int boardWidth, int boardHeight) {

        int maxMoveDistance = (range==-1) ? boardWidth : range;
        return IntStream
                .range(0, boardWidth)
                .filter(y -> Math.abs(y - piece.getY()) <= maxMoveDistance) // is in range
                .filter(y -> 0 <= y && y <= boardWidth)           // inside board
                .filter(y -> piece.getY() != y)                   // not own position
                .mapToObj(y -> new Position(piece.getX(), y))
                .collect(Collectors.toList());
    }

}
