package com.chessplusplus.game.component.movement;

import com.chessplusplus.game.component.Position;

import java.util.List;

/**
 * The MovePattern interface is used for rules that define the
 * possible ways a piece can move on the board.
 * <p>
 * Movement patterns are designed to be simple, only capable of defining
 * simple movement, and should not be used to create possible turns from a piece.
 * <p>
 * Movement patterns are also to be used for strikes, as a strike is just a move
 * that requires taking an enemy piece when making the move.
 */
public interface MovePattern {

    /**
     * Utility method all movement patterns must implement that generates a list of
     * all moves that could be made as a result of this rule, not considering other pieces.
     *
     * @param piecePosition The position of the piece.
     * @param boardWidth    Width of game board.
     * @param boardHeight   Height of game board.
     * @return All possible moves this movement pattern implies.
     */
    List<Position> getPossibleMoves(Position piecePosition, int boardWidth, int boardHeight);

}
