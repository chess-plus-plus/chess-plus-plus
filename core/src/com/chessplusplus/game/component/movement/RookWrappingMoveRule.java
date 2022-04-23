package com.chessplusplus.game.component.movement;

import com.chessplusplus.game.Board;
import com.chessplusplus.game.Piece;
import com.chessplusplus.game.PieceType;
import com.chessplusplus.game.Turn;
import com.chessplusplus.game.component.Position;

import java.util.ArrayList;
import java.util.List;

import static com.chessplusplus.game.component.movement.MovementRayUtils.wrappingRays;

/**
 * The Rook Wrapping Move is a special Chess++ ability the rook gains at level 1.
 * It gives the rook the ability to "wrap" around the edge of the board.
 */
public class RookWrappingMoveRule implements SpecialMoveRule {

    @Override
    public List<Turn> getLegalTurns(String playerId, Piece piece, Board gameBoard) {
        List<Turn> legalTurns = new ArrayList<>();

        if (piece.getPieceType() != PieceType.ROOK) { // Only pawns can do en passant.
            return legalTurns;
        }

        // Create a "ray" wrapping around the board for each cardinal direction.
        List<Position> northRay = wrappingRays(piece.getPosition(), 0, 1, gameBoard);
        List<Position> eastRay = wrappingRays(piece.getPosition(), 1, 0, gameBoard);
        List<Position> southRay = wrappingRays(piece.getPosition(), 0, -1, gameBoard);
        List<Position> westRay = wrappingRays(piece.getPosition(), -1, 0, gameBoard);

        // Add all of the legal turns that arise from the wrappinng movement.
        legalTurns.addAll(convertValidMovesToTurns(northRay, piece, gameBoard));
        legalTurns.addAll(convertValidMovesToTurns(eastRay, piece, gameBoard));
        legalTurns.addAll(convertValidMovesToTurns(southRay, piece, gameBoard));
        legalTurns.addAll(convertValidMovesToTurns(westRay, piece, gameBoard));

        return legalTurns;
    }

    private List<Turn> convertValidMovesToTurns(List<Position> ray, Piece piece, Board board) {
        List<Turn> legalTurns = new ArrayList<>();

        List<Position> validMoves = MovementRayUtils.validMovesFromRayCollisionDetection(ray,
                piece.getPosition(), board);
        for (Position validMove : validMoves) {
            List<Turn.Action> actions = new ArrayList<>();

            // Check if the validMove position contains an enemy piece, which makes this a strike
            if (board.getPiece(validMove) != null
                    && !board.getPiece(validMove).getPlayerId().equals(piece.getPlayerId())) {
                actions.add(new Turn.Action(piece, Turn.ActionType.STRIKE, piece.getPosition(), validMove));
                //actions.add(new Turn.Action(board.getPiece(validMove), Turn.ActionType.DESTRUCTION,
                        //validMove, validMove));
                // TODO: Uncomment when destruction of pieces works properly (if that change is made).
            }

            actions.add(new Turn.Action(piece, Turn.ActionType.MOVEMENT, piece.getPosition(), validMove));

            legalTurns.add(new Turn(piece.getPlayerId(), actions));
        }
        return legalTurns;
    }

}
