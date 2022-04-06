package com.chessplusplus.game.component.movement;

import com.chessplusplus.game.Board;
import com.chessplusplus.game.Piece;
import com.chessplusplus.game.PieceType;
import com.chessplusplus.game.Turn;
import com.chessplusplus.game.component.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Castling is a special chess rule that involves moving multiple pieces (king and rook)
 * at the same time, look it up.
 */
public class CastlingMoveRule implements SpecialMoveRule {

    @Override
    public List<Turn> getLegalTurns(String playerId, Piece king, Board gameBoard) {
        ArrayList<Turn> legalTurns = new ArrayList<>();

        // Check if the piece is a king, and if it has moved.
        if (king.getPieceType() != PieceType.KING || king.getActions().size() > 0) {
            return legalTurns;
        }

        // This is kind of silly, but it gets the corner squares of row the king is on,
        // Where we expect unmoved rooks to be placed.
        // The "kingside" rook is the one closest to the king, in the H column.
        // The "queenside" rook is the one closest to the queen, in the A column.
        Position kingPos = king.getPosition();
        Piece kingsideRook = gameBoard.getPiece(king.getPosition().adjacentRightPosition()
                .adjacentRightPosition().adjacentRightPosition());
        Piece queensideRook = gameBoard.getPiece(king.getPosition().adjacentLeftPosition()
                .adjacentLeftPosition().adjacentLeftPosition().adjacentLeftPosition());

        if (checkRookAndVerifyPath(kingPos, kingsideRook, gameBoard, 1)) {
            ArrayList<Turn.Action> actionList = new ArrayList<>();
            actionList.add(new Turn.Action(king, Turn.ActionType.MOVEMENT, kingPos,
                    kingPos.adjacentRightPosition().adjacentRightPosition()));
            actionList.add(new Turn.Action(kingsideRook, Turn.ActionType.MOVEMENT,
                    kingsideRook.getPosition(), kingPos.adjacentRightPosition()));

            legalTurns.add(new Turn(playerId, actionList));
        }

        if (checkRookAndVerifyPath(kingPos, queensideRook, gameBoard, -1)) {
            ArrayList<Turn.Action> actionList = new ArrayList<>();
            actionList.add(new Turn.Action(king, Turn.ActionType.MOVEMENT, kingPos,
                    kingPos.adjacentLeftPosition().adjacentLeftPosition()));
            actionList.add(new Turn.Action(queensideRook, Turn.ActionType.MOVEMENT,
                    queensideRook.getPosition(), kingPos.adjacentLeftPosition()));

            legalTurns.add(new Turn(playerId, actionList));
        }

        return legalTurns;
    }

    /**
     * Helper function that verifies castling conditions:
     * 1: Both the King and Rook can not have moved
     * 2: King piece is not in check TODO: Implement
     * 3: Squares between king and rook should be empty
     * 4: The squares the king will move across and to cannot be under attack TODO: Implement
     *
     * @param kingPos Position of the king, assumed to be correct
     * @param rook    Potential rook piece, can be null.
     * @param board   Chess board.
     * @return true if all conditions are valid.
     */
    private boolean checkRookAndVerifyPath(Position kingPos, Piece rook,
                                           Board board, int dirDelta) {
        if (rook == null) { // Check if rook square actually has a rook in it.
            return false;
        } else if (rook.getActions().size() > 0) { // Check condition #1.
            return false;
        } // if (board.squareIsUnderAttack(kingPos) or something similar to check condition #2

        // Generate all squares in-between the rook and king.
        int tmp = kingPos.getX() + dirDelta;
        List<Position> inbetweenSquares = new ArrayList<>();
        while (tmp != rook.getPosition().getX()) {
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
