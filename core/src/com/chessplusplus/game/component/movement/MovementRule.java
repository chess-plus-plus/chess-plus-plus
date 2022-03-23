package com.chessplusplus.game.component.movement;

import com.chessplusplus.game.component.Position;

import java.util.List;

/**
 * The MovementRule interface is used for rules that define the
 * possible and legal ways a piece can move on the board.
 */
public interface MovementRule {

    /**
     * Utility method all movement rules must implement that generates a list of
     * all moves that could be made as a result of this rule.
     *
     * @param piecePosition The position of the piece.
     * @param boardWidth    Width of game board.
     * @param boardHeight   Height of game board.
     * @return All possible moves this movement rule implies.
     */
    List<Position> getPossibleMoves(Position piecePosition, int boardWidth, int boardHeight);

}
