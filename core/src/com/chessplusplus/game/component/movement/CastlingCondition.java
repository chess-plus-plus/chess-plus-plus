package com.chessplusplus.game.component.movement;

import com.chessplusplus.game.Board;
import com.chessplusplus.game.Piece;
import com.chessplusplus.game.PieceType;
import com.chessplusplus.game.component.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Castling is a special chess rule that involves moving multiple pieces (king and rook)
 * at the same time, look it up.
 */
public class CastlingCondition implements GameCondition {

    @Override
    public boolean checkCondition(Piece piece, Board board) {
        if (piece.getPieceType() != PieceType.KING || piece.getMoves() > 0) {
            // Only kings that have not moved can initiate en passant.
            return false;
        }

        // This is kind of silly, but it gets the corner squares of row the king is on,
        // Where we expect unmoved rooks to be placed.
        // The "kingside" rook is the one closest to the king, in the H column.
        // The "queenside" rook is the one closest to the queen, in the A column.
        Position kingsideRookSquare = piece.getPosition().adjacentRightPosition()
                .adjacentRightPosition().adjacentRightPosition();
        Position queensideRookSquare = piece.getPosition().adjacentLeftPosition()
                .adjacentLeftPosition().adjacentLeftPosition().adjacentLeftPosition();

        return false;
    }

    /**
     * Helper function that verifies castling conditions:
     * 1: Both the King and Rook can not have moved
     * 2: King piece is not in check TODO: Implement
     * 3: Squares between king and rook should be empty
     * 4: The squares the king will move across and to cannot be under attack TODO: Implement
     * @param kingPos Position of the king, assumed to be correct
     * @param rookPos Position of potential rook, can be empty/contain other piece.
     * @param board Chess board.
     * @return true if all conditions are valid.
     */
    private boolean checkRookSquareAndVerifyPath(Position kingPos, Position rookPos,
                                                 Board board, int dirDelta) {
        // Get both of the pieces.
        Piece rook = board.getPiece(rookPos);
        Piece king = board.getPiece(kingPos);
        if (rook == null) { // Check if rook square actually has a rook in it.
            return false;
        } else if (rook.getMoves() > 0 || king.getMoves() > 0) { // Check condition #1.
            return false;
        } // if (board.squareIsUnderAttack(kingPos) or something similar to check condition #2

        // Generate all squares in-between the rook and king.
        int tmp = kingPos.getX() + dirDelta;
        List<Position> inbetweenSquares = new ArrayList<>();
        while (tmp != rookPos.getX()) {
            inbetweenSquares.add(new Position(tmp, kingPos.getY()));
            tmp++;
        }

        // Verify that all the squares in-between the rook and king are empty. Verify condition #3.
        for (Position inbetweenSquare : inbetweenSquares) {
            if (board.getPiece(inbetweenSquare) == null) {
                // board.squareIsUnderAttack(board.getPiece(inbetweenSquare)) to check condition #4.
                return false;
            }
        }

        return true;
    }

}
