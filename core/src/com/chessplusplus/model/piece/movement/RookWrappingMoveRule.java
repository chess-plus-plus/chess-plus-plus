package com.chessplusplus.model.piece.movement;

import com.chessplusplus.model.board.Board;
import com.chessplusplus.model.piece.Piece;
import com.chessplusplus.model.piece.PieceType;
import com.chessplusplus.model.ChessTurn;
import com.chessplusplus.model.board.Position;

import java.util.ArrayList;
import java.util.List;

import static com.chessplusplus.model.piece.movement.MovementRayUtils.wrappingRays;

/**
 * The Rook Wrapping Move is a special Chess++ ability the rook gains at level 1.
 * It gives the rook the ability to "wrap" around the edge of the board.
 */
public class RookWrappingMoveRule implements SpecialMoveRule {

    @Override
    public List<ChessTurn> getLegalTurns(String playerId, Piece piece, Board gameBoard) {
        List<ChessTurn> legalTurns = new ArrayList<>();

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

    private List<ChessTurn> convertValidMovesToTurns(List<Position> ray, Piece piece, Board board) {
        List<ChessTurn> legalTurns = new ArrayList<>();

        List<Position> validMoves = MovementRayUtils.validMovesFromRayCollisionDetection(ray,
                piece.getPosition(), board);
        for (Position validMove : validMoves) {
            List<ChessTurn.Action> actions = new ArrayList<>();

            // Check if the validMove position contains an enemy piece, which makes this a strike
            if (board.getPiece(validMove) != null
                    && !board.getPiece(validMove).getPlayerId().equals(piece.getPlayerId())) {
                actions.add(new ChessTurn.Action(piece, ChessTurn.ActionType.STRIKE, piece.getPosition(), validMove));
                //actions.add(new Turn.Action(board.getPiece(validMove), Turn.ActionType.DESTRUCTION,
                        //validMove, validMove));
                // TODO: Uncomment when destruction of pieces works properly (if that change is made).
            }

            actions.add(new ChessTurn.Action(piece, ChessTurn.ActionType.MOVEMENT, piece.getPosition(), validMove));

            legalTurns.add(new ChessTurn(piece.getPlayerId(), actions));
        }
        return legalTurns;
    }

}
