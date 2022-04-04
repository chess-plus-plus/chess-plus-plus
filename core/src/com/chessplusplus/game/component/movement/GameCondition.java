package com.chessplusplus.game.component.movement;

import com.chessplusplus.game.Board;
import com.chessplusplus.game.Piece;

/**
 * A Game Condition is just a verifiable condition in the game.
 * <p>
 * It can be used specifically to verify the condition of a specific Piece,
 * such as whether it has moved before or not, or it can be more complex
 * and check multiple pieces if needed.
 */
public interface GameCondition {

    /**
     * Verify if the condition is fulfilled or not.
     *
     * @param piece A specific piece if condition is related to one piece.
     * @param board Board, used for more complex conditions.
     * @return true if condition is fulfilled, false otherwise.
     */
    boolean checkCondition(Piece piece, Board board);

}
