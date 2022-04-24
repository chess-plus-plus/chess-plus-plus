package com.chessplusplus.model.piece.movement;

import com.chessplusplus.model.board.Board;
import com.chessplusplus.model.piece.Piece;
import com.chessplusplus.model.ChessTurn;

import java.util.List;

/**
 * The King's special move rule gives XP to nearby pieces when the king strikes an enemy piece.
 */
public class KingSpecialMoveRule implements SpecialMoveRule {

    @Override
    public List<ChessTurn> getLegalTurns(String playerId, Piece piece, Board gameBoard) {
        // TODO: Implement
        return null;
    }

}
