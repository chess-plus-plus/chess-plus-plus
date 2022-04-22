package com.chessplusplus.game.component.movement;

import com.chessplusplus.game.Board;
import com.chessplusplus.game.component.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * The RowMoveRestriction restricts a piece from being able to move into a given row.
 */
public class RowMoveRestriction implements MoveRestriction {

    // Defines which row is inaccessible.
    public int rowNumber;

    public RowMoveRestriction(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    @Override
    public List<Position> filterMoves(List<Position> possibleMoves, Position piecePosition, Board board) {
        List<Position> filteredMoves = new ArrayList<>();

        // Filter out any candidates that have an y value (e.g. on row y) that equals the
        // row number in this restriction.
        for (Position candidateMove : possibleMoves) {
            if (!(candidateMove.getY() == rowNumber)) {
                filteredMoves.add(candidateMove);
            }
        }

        return filteredMoves;
    }
}
