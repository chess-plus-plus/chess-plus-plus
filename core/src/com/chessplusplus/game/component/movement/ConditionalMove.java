package com.chessplusplus.game.component.movement;

import com.chessplusplus.game.Board;
import com.chessplusplus.game.Piece;
import com.chessplusplus.game.component.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to express more complex movement rules than what the standard system supports.
 * <p>
 * Takes a gameCondition and a movementRule, and returns possible moves from the
 * movementRule when the condition is fulfilled.
 * <p>
 * Should only be used for very special cases of movement/strikes that go beyond
 * the standard movement system.
 */
public class ConditionalMove {

    private final GameCondition condition;
    private final MovementRule movementRule;

    public ConditionalMove(GameCondition condition, MovementRule movementRule) {
        this.condition = condition;
        this.movementRule = movementRule;
    }

    public List<Position> getPossibleMoves(Piece piece, Board board) {
        if (condition.checkCondition(piece, board))
            return movementRule.getPossibleMoves(piece.getPosition(), board.getWidth(), board.getHeight());
        else
            return new ArrayList<>();
    }

}
