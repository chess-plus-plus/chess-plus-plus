package com.chessplusplus.game.component.movement;

import com.chessplusplus.game.Board;
import com.chessplusplus.game.Piece;
import com.chessplusplus.game.PieceType;
import com.chessplusplus.game.Turn;
import com.chessplusplus.game.component.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * En Passant is a weird chess rule, look it up.
 */
public class EnPassantMoveRule implements SpecialMoveRule {

    @Override
    public List<Turn> getLegalTurns(String playerId, Piece pawn, Board gameBoard) {
        ArrayList<Turn> legalTurns = new ArrayList<>();

        // Check if the piece is actually a pawn
        if (pawn.getPieceType() != PieceType.PAWN) { // Only pawns can do en passant.
            return legalTurns;
        }

        // Note: These pieces may be null
        Piece leftPiece = gameBoard.getPiece(pawn.getPosition().adjacentLeftPosition());
        Piece rightPiece = gameBoard.getPiece(pawn.getPosition().adjacentRightPosition());

        Position pawnPos = pawn.getPosition();

        if (checkPiece(playerId, leftPiece)) {
            Position otherPiecePos = leftPiece.getPosition();
            ArrayList<Turn.Action> actionList = new ArrayList<>();
            actionList.add(new Turn.Action(pawn, Turn.ActionType.STRIKE, pawnPos,
                    otherPiecePos));
            actionList.add(new Turn.Action(leftPiece, Turn.ActionType.DESTRUCTION, otherPiecePos,
                    otherPiecePos));
            actionList.add(new Turn.Action(pawn, Turn.ActionType.MOVEMENT, pawnPos, otherPiecePos));

            legalTurns.add(new Turn(playerId, actionList));
        }

        if (checkPiece(playerId, rightPiece)) {
            Position otherPiecePos = rightPiece.getPosition();
            ArrayList<Turn.Action> actionList = new ArrayList<>();
            actionList.add(new Turn.Action(pawn, Turn.ActionType.STRIKE, pawnPos,
                    otherPiecePos));
            actionList.add(new Turn.Action(rightPiece, Turn.ActionType.DESTRUCTION, otherPiecePos,
                    otherPiecePos));
            actionList.add(new Turn.Action(pawn, Turn.ActionType.MOVEMENT, pawnPos, otherPiecePos));

            legalTurns.add(new Turn(playerId, actionList));
        }

        return legalTurns;
    }

    /**
     * Helper function that verifies that a position fulfills the following conditions:
     * 1: Has a pawn from the opposite team in it.
     * 2: That pawn has only moved once.
     * 3: That pawn moved two squares in its first move.
     *
     * @param playerId The player id of the player controlling the pawn.
     * @param otherPiece The other pawn piece, may be null.
     * @return true if all conditions are valid.
     */
    private boolean checkPiece(String playerId, Piece otherPiece) {
        return otherPiece != null
                && otherPiece.getPieceType() == PieceType.PAWN // Check condition #1
                && !otherPiece.getPlayerId().equals(playerId) // Check condition #1
                && checkCondition2And3(otherPiece);
    }

    /**
     * Check if condition 2 and 3 are valid.
     */
    private boolean checkCondition2And3(Piece pawn) {
        if (pawn.getActions().size() != 1) {
            return false;
        }

        Turn.Action moveAction = pawn.getActions().get(0);
        Position startPos = moveAction.startPos;
        Position endPos = moveAction.actionPos;

        return Math.abs(startPos.getX() - endPos.getX()) == 2;
    }

}
