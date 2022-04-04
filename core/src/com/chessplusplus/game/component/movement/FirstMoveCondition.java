package com.chessplusplus.game.component.movement;

import com.chessplusplus.game.Board;
import com.chessplusplus.game.Piece;

/**
 * Condition used to see if a piece has moved yet.
 *
 * Used for pawns, kings, and rooks in base chess.
 */
public class FirstMoveCondition implements GameCondition {

    @Override
    public boolean checkCondition(Piece piece, Board board) {
        return piece.getMoves() == 0;
    }

}
