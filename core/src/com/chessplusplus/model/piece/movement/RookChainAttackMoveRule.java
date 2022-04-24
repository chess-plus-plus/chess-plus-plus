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
 * The Rook Chain Attack move is a special Chess++ ability the rook gains at level 2.
 * It allows it to strike multiple pieces at the same time.
 */
// TODO: Does not work properly
public class RookChainAttackMoveRule implements SpecialMoveRule {

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

        // Add all of the valid strikes arising from the given rays.
        legalTurns.addAll(convertValidStrikesToTurn(northRay, piece, gameBoard));
        legalTurns.addAll(convertValidStrikesToTurn(eastRay, piece, gameBoard));
        legalTurns.addAll(convertValidStrikesToTurn(southRay, piece, gameBoard));
        legalTurns.addAll(convertValidStrikesToTurn(westRay, piece, gameBoard));

        System.out.println(legalTurns);
        return legalTurns;
    }

    /**
     * Utility method that takes an attack ray and generates a list of actions derived from
     * that ray.
     */
    private List<ChessTurn> convertValidStrikesToTurn(List<Position> ray, Piece piece, Board board) {
        List<ChessTurn> legalTurns = new ArrayList<>();

        // Get a (possibly empty) list of enemy pieces that can be struck using the given ray.
        List<Position> validStrikes = MovementRayUtils.adjacentEnemyRayCalc(ray, piece.getPlayerId(), board);

        // Only create a turn if any strikes are possible.
        if (validStrikes.size() != 0) {
            List<ChessTurn.Action> actions = new ArrayList<>();

            // Add a strike and destruction action for each of the enemy pieces.
            for (Position validStrikePos : validStrikes) {
                actions.add(new ChessTurn.Action(piece, ChessTurn.ActionType.STRIKE, piece.getPosition(),
                        validStrikePos));
                // actions.add(new Turn.Action(board.getPiece(validStrikePos), Turn.ActionType.DESTRUCTION,
                        //validStrikePos, validStrikePos));
                // TODO: Uncomment when destruction of pieces works properly (if that change is made).
            }

            // Add a movement to the first enemyPiece the piece striked
            actions.add(new ChessTurn.Action(piece, ChessTurn.ActionType.MOVEMENT, piece.getPosition(), validStrikes.get(0)));
            legalTurns.add(new ChessTurn(piece.getPlayerId(), actions));
        }

        return legalTurns;
    }

}
