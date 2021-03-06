package com.chessplusplus.model.piece.movement;

import com.chessplusplus.model.board.Board;
import com.chessplusplus.model.piece.Piece;
import com.chessplusplus.model.piece.PieceFactory;
import com.chessplusplus.model.piece.PieceType;
import com.chessplusplus.model.ChessTurn;
import com.chessplusplus.model.board.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Allows a pawn to promote when it moves into the final row.
 */
public class PromotionMoveRule implements SpecialMoveRule {

    // Determines whether we automatically promote pawns to queen.
    private final boolean defaultPromotion;

    public PromotionMoveRule(boolean defaultPromotion) {
        this.defaultPromotion = defaultPromotion;
    }

    @Override
    public List<ChessTurn> getLegalTurns(String playerId, Piece piece, Board gameBoard) {
        ArrayList<ChessTurn> legalTurns = new ArrayList<>();

        // Check if the piece is actually a pawn
        if (piece.getPieceType() != PieceType.PAWN) { // Only pawns can promote.
            return legalTurns;
        }

        // Get a list of possible strike and move candidates.
        List<Position> moveCandidates = piece.getMovementRules().getMoveCandidates(
                piece.getPosition(), gameBoard.getWidth(), gameBoard.getHeight(), false);
        List<Position> strikeCandidates = piece.getMovementRules().getMoveCandidates(
                piece.getPosition(), gameBoard.getWidth(), gameBoard.getHeight(), true);

        // Filter out positions the pawn cannot enter
        moveCandidates = filterOutCandidates(moveCandidates,
                piece.getMovementRules().getMoveRestrictionsCopy(), piece.getPosition(), gameBoard);
        strikeCandidates = filterOutCandidates(strikeCandidates,
                piece.getMovementRules().getStrikeRestrictionsCopy(), piece.getPosition(), gameBoard);


        int topRow = gameBoard.getHeight() - 1;
        int botRow = 0;

        // If a move candidate is in either the max or min row and that is empty we create turns to promote.
        for (Position moveCandidate : moveCandidates) {
            if (gameBoard.squareIsEmpty(moveCandidate) &&
                    (moveCandidate.getY() == topRow || moveCandidate.getY() == botRow)) {
                if (!defaultPromotion) {
                    legalTurns.add(createPromotionTurn(piece, moveCandidate, PieceType.BISHOP, false, gameBoard));
                    legalTurns.add(createPromotionTurn(piece, moveCandidate, PieceType.KNIGHT, false, gameBoard));
                    legalTurns.add(createPromotionTurn(piece, moveCandidate, PieceType.ROOK, false, gameBoard));
                }
                legalTurns.add(createPromotionTurn(piece, moveCandidate, PieceType.QUEEN, false, gameBoard));
            }
        }

        // If a move candidate is in either the max or min row and that is not empty we create turns to promote.
        for (Position strikeCandidate : strikeCandidates) {
            if (!gameBoard.squareIsEmpty(strikeCandidate) &&
                    (strikeCandidate.getY() == topRow || strikeCandidate.getY() == botRow)) {
                if (!defaultPromotion) {
                    legalTurns.add(createPromotionTurn(piece, strikeCandidate, PieceType.BISHOP, true, gameBoard));
                    legalTurns.add(createPromotionTurn(piece, strikeCandidate, PieceType.KNIGHT, true, gameBoard));
                    legalTurns.add(createPromotionTurn(piece, strikeCandidate, PieceType.ROOK, true, gameBoard));
                }
                legalTurns.add(createPromotionTurn(piece, strikeCandidate, PieceType.QUEEN, true, gameBoard));
            }
        }

        return legalTurns;
    }

    private List<Position> filterOutCandidates(List<Position> positions,
                                               List<MoveRestriction> restrictions,
                                               Position pos,
                                               Board board) {
        DirectionalMoveRestriction directionalMoveRestriction = null;
        for (MoveRestriction restriction : restrictions) {
            if (restriction.getClass() == DirectionalMoveRestriction.class) {
                directionalMoveRestriction = (DirectionalMoveRestriction) restriction;
            }
        }

        if (directionalMoveRestriction == null) {
            return positions;
        } else {
            return directionalMoveRestriction.filterMoves(positions, pos, board);
        }
    }

    /**
     * Utility method that creates a Promotion turn according to given parameters.
     */
    private ChessTurn createPromotionTurn(Piece piece, Position targetPos, PieceType newPieceType,
                                          boolean isStrike, Board board) {
        Piece newPiece = PieceFactory.createPowerPiece(piece.getPlayerId(), targetPos, newPieceType);

        ArrayList<ChessTurn.Action> actionList = new ArrayList<>();
        if (isStrike) {
            actionList.add(new ChessTurn.Action(board.getPiece(targetPos), ChessTurn.ActionType.DESTRUCTION,
                    targetPos, targetPos));
        }
        actionList.add(new ChessTurn.Action(piece, ChessTurn.ActionType.MOVEMENT, piece.getPosition(), targetPos));
        actionList.add(new ChessTurn.Action(piece, ChessTurn.ActionType.DESTRUCTION, piece.getPosition(),
                piece.getPosition()));
        actionList.add(new ChessTurn.Action(newPiece, ChessTurn.ActionType.CREATION, targetPos, targetPos));

        return new ChessTurn(piece.getPlayerId(), actionList);
    }

}
