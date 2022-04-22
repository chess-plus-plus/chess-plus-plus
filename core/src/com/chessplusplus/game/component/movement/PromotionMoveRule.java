package com.chessplusplus.game.component.movement;

import com.chessplusplus.game.Board;
import com.chessplusplus.game.Piece;
import com.chessplusplus.game.PieceFactory;
import com.chessplusplus.game.PieceType;
import com.chessplusplus.game.Turn;
import com.chessplusplus.game.component.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Allows a pawn to promote when it moves into the final row.
 */
public class PromotionMoveRule implements SpecialMoveRule {

    @Override
    public List<Turn> getLegalTurns(String playerId, Piece piece, Board gameBoard) {
        ArrayList<Turn> legalTurns = new ArrayList<>();

        // Check if the piece is actually a pawn
        if (piece.getPieceType() != PieceType.PAWN) { // Only pawns can promote.
            return legalTurns;
        }

        // Get a list of possible strike and move candidates.
        List<Position> moveCandidates = piece.getMovementRules().getMoveCandidates(
                piece.getPosition(), gameBoard.getWidth(), gameBoard.getHeight(), false);
        List<Position> strikeCandidates = piece.getMovementRules().getMoveCandidates(
                piece.getPosition(), gameBoard.getWidth(), gameBoard.getHeight(), true);

        int topRow = gameBoard.getHeight() - 1;
        int botRow = 0;

        // If a move candidate is in either the max or min row and that is empty we create turns to promote.
        for (Position moveCandidate : moveCandidates) {
            if (gameBoard.squareIsEmpty(moveCandidate) &&
                    (moveCandidate.getY() == topRow || moveCandidate.getY() == botRow)) {
                legalTurns.add(createPromotionTurn(piece, moveCandidate, PieceType.BISHOP, false, gameBoard));
                legalTurns.add(createPromotionTurn(piece, moveCandidate, PieceType.KNIGHT, false, gameBoard));
                legalTurns.add(createPromotionTurn(piece, moveCandidate, PieceType.ROOK, false, gameBoard));
                legalTurns.add(createPromotionTurn(piece, moveCandidate, PieceType.QUEEN, false, gameBoard));
            }
        }

        // If a move candidate is in either the max or min row and that is not empty we create turns to promote.
        for (Position strikeCandidate : strikeCandidates) {
            if (!gameBoard.squareIsEmpty(strikeCandidate) &&
                    (strikeCandidate.getY() == topRow || strikeCandidate.getY() == botRow)) {
                legalTurns.add(createPromotionTurn(piece, strikeCandidate, PieceType.BISHOP, true, gameBoard));
                legalTurns.add(createPromotionTurn(piece, strikeCandidate, PieceType.KNIGHT, true, gameBoard));
                legalTurns.add(createPromotionTurn(piece, strikeCandidate, PieceType.ROOK, true, gameBoard));
                legalTurns.add(createPromotionTurn(piece, strikeCandidate, PieceType.QUEEN, true, gameBoard));
            }
        }

        return legalTurns;
    }

    /**
     * Utility method that creates a Promotion turn according to given parameters.
     */
    private Turn createPromotionTurn(Piece piece, Position targetPos, PieceType newPieceType,
                                     boolean isStrike, Board board) {
        Piece newPiece = PieceFactory.createPowerPiece(piece.getPlayerId(), targetPos, newPieceType);

        ArrayList<Turn.Action> actionList = new ArrayList<>();
        if (isStrike) {
            actionList.add(new Turn.Action(board.getPiece(targetPos), Turn.ActionType.DESTRUCTION,
                    targetPos, targetPos));
        }

        actionList.add(new Turn.Action(piece, Turn.ActionType.DESTRUCTION, piece.getPosition(),
                piece.getPosition()));
        actionList.add(new Turn.Action(newPiece, Turn.ActionType.CREATION, targetPos, targetPos));

        return new Turn(piece.getPlayerId(), actionList);
    }

}
