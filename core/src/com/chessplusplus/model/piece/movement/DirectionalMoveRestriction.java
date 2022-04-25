package com.chessplusplus.model.piece.movement;

import com.chessplusplus.model.board.Board;
import com.chessplusplus.model.board.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * The DirectionalMoveRestriction restricts a piece to only be able to move in a single direction.
 */
public class DirectionalMoveRestriction implements MoveRestriction {

    /* Defines which positions (relative to the Piece's position) are considered valid.
     * A directional filter value of 1 means that positions with a higher or equal relative value to
     * the respective axis are valid. Conversely a value of -1 means the opposite (lower relative
     * values are valid), and 0 means all values on the axis are valid.
     *
     * (e.g. if piece is in (2,2) with dirFilterX = 1, then only values of (x>2, y) are valid.)
     */
    public int dirFilterX;
    public int dirFilterY;

    // TODO: A constructor that doesn't directly supply int values would be good.
    public DirectionalMoveRestriction(int dirFilterX, int dirFilterY) {
        this.dirFilterX = dirFilterX;
        this.dirFilterY = dirFilterY;
    }

    @Override
    public List<Position> filterMoves(List<Position> possibleMoves, Position piecePosition, Board board) {
        List<Position> legalMoves = new ArrayList<>();

        // 1: Calculate relative position of candidate to piece
        // 2: Normalise values against directional filter values of X and Y
        // 3: Valid values will always have a value of 0 or higher
        for (Position candidateMove : possibleMoves) {
            int relX = candidateMove.getX() - piecePosition.getX();
            int relY = candidateMove.getY() - piecePosition.getY();

            if ((relX * dirFilterX) >= 0 && (relY * dirFilterY) >= 0) {
                legalMoves.add(candidateMove);
            }
        }

        return legalMoves;
    }

}

