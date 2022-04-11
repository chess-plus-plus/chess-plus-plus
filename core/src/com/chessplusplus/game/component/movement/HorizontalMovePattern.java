package com.chessplusplus.game.component.movement;

import com.chessplusplus.game.component.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * HorizontalMovePattern is used to represent movement along the "x-axis"
 * on the board, aka sideways movement.
 */
public class HorizontalMovePattern extends SimpleMovePattern {

    public HorizontalMovePattern(int range) {
        super(range);
    }

    public HorizontalMovePattern() {
        super();
    }

    /**
     * Creates a movement pattern that gives the piece horizontal movement
     * with a range of one square per turn.
     *
     * In normal chess this is used by the King.
     * @return HorizontalMovePattern with range = 1.
     */
    public static HorizontalMovePattern oneSquareHorizontalMovement() {
        return new HorizontalMovePattern(1);
    }

    /**
     * Creates a movement pattern that gives the piece horizontal movement
     * with an unlimited range per turn.
     *
     * In normal chess this is used by the Rook & the Queen.
     * @return HorizontalMovePattern with unlimited range.
     */
    public static HorizontalMovePattern unlimitedHorizontalMovement() {
        return new HorizontalMovePattern(-1);
    }


    @Override
    public List<Position> getPossibleMoves(Position piece, int boardWidth, int boardHeight) {
        int maxMoveDistance = (range==-1) ? boardWidth: range;
        return IntStream
                .range(0, boardWidth)
                .filter(x -> x - piece.getX() <= maxMoveDistance) // is in range
                .filter(x -> 0 <= x && x <= boardWidth)           // inside board
                .filter(x -> piece.getX() != x)                   // not own position
                .mapToObj(x -> new Position(x, piece.getY()))
                .collect(Collectors.toList());
    }
}
