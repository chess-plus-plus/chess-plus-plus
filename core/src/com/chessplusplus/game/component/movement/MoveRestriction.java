package com.chessplusplus.game.component.movement;

import com.chessplusplus.game.component.Position;

import java.util.List;

/**
 * A MoveRestriction is the inverse of a MovementPattern in that it defines what
 * moves are *not* legal.
 * <p>
 * A MovementRestriction should be applied *after* generating possible moves from
 * all of the MovementPatterns.
 */
public interface MoveRestriction {

    /**
     * Takes in a set of possible moves and filters out the moves that
     * violate the filter conditions.
     *
     * @param possibleMoves Set of possible movement candidates to verify or discard.
     * @param piecePosition The position of the piece.
     * @return All legal moves that are valid according to this restriction.
     */
    List<Position> filterMoves(List<Position> possibleMoves, Position piecePosition);

}
