package com.chessplusplus.game.component.movement;

import com.chessplusplus.game.Board;
import com.chessplusplus.game.Piece;
import com.chessplusplus.game.PieceType;
import com.chessplusplus.game.component.Position;

/**
 * En Passant is a weird chess rule, look it up.
 */
public class EnPassantCondition implements GameCondition {

    @Override
    public boolean checkCondition(Piece piece, Board board) {
        if (piece.getPieceType() != PieceType.PAWN) { // Only pawns can do en passant.
            return false;
        }

        // These positions might not be valid, but we'll leave the board to deal with that.
        Position leftSquare = piece.getPosition().adjacentLeftPosition();
        Position rightSquare = piece.getPosition().adjacentRightPosition();

        return checkSquare(leftSquare, board) || checkSquare(rightSquare, board);
    }

    /**
     * Helper function that verifies that a position fulfills the following conditions:
     * 1: Has a pawn from the opposite team in it.
     * 2: That pawn has only moved once.
     * 3: That pawn moved two squares in its first move.
     *
     * NOTE: This might not work properly with added RPG mechanics.
     * @param position Position of square to verify.
     * @param board Chess board.
     * @return true if all conditions are valid.
     */
    private boolean checkSquare(Position position, Board board) {
        Piece otherPiece = board.getPiece(position);

        return otherPiece != null
                && otherPiece.getPieceType() == PieceType.PAWN
                && otherPiece.getMoves() == 1;
    }

}
