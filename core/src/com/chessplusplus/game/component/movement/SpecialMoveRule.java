package com.chessplusplus.game.component.movement;

import com.chessplusplus.game.Board;
import com.chessplusplus.game.Piece;
import com.chessplusplus.game.ChessTurn;

import java.util.List;

/**
 * Used to express more complex movement rules than the standard
 * move pattern and restriction system can support.
 * <p>
 * Additionally, unlike move patterns, it generates a set of
 * possible Turns that can be executed, allowing for much higher complexity.
 */
public interface SpecialMoveRule {

    /**
     * Utility method that generates a list of all legal turns that could be made
     * as a result of this rule, after factoring in the condition of the board.
     *
     * @param piece     The piece.
     * @param gameBoard Game board.
     * @return All legal turns that can be initiated from this piece.
     */
    List<ChessTurn> getLegalTurns(String playerId, Piece piece, Board gameBoard);

}
