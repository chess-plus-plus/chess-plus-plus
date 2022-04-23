package com.chessplusplus.game.component.movement;

import com.chessplusplus.game.Board;
import com.chessplusplus.game.Piece;
import com.chessplusplus.game.Turn;

import java.util.List;

/**
 * The King's special move rule gives XP to nearby pieces when the king strikes an enemy piece.
 */
public class KingSpecialMoveRule implements SpecialMoveRule {

    @Override
    public List<Turn> getLegalTurns(String playerId, Piece piece, Board gameBoard) {
        // TODO: Implement
        return null;
    }

}
