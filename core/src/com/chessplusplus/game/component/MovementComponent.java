package com.chessplusplus.game.component;

import com.badlogic.ashley.core.Component;
import com.chessplusplus.game.component.movement.MovementRule;

import java.util.ArrayList;
import java.util.List;

/**
 * A MovementComponent represents how a piece can move across the board.
 * Movement is expressed as a set of "MovementRules" that can be combined
 * dynamically to express complex movement patterns available in chess.
 */
public class MovementComponent implements Component {

    public List<MovementRule> movementRules;

    public MovementComponent(List<MovementRule> movementRules) {
        this.movementRules = movementRules;
    }

    /**
     * Utility method that generates all possible moves arising from the movement rules.
     *
     * @param piecePosition The position of the piece.
     * @param boardWidth    Width of game board.
     * @param boardHeight   Height of game board.
     * @return All possible moves this movement rule implies.
     */
    public List<Position> getAllPossibleMoves(Position piecePosition, int boardWidth, int boardHeight) {
        List<Position> possibleMoves = new ArrayList<>();

        for (MovementRule movementRule : movementRules) {
            List<Position> ruleMoves = movementRule.getPossibleMoves(piecePosition, boardWidth, boardHeight);

            // Make sure to only add positions that haven't already been added to the set.
            for (Position position : ruleMoves) {
                if (!possibleMoves.contains(position)) {
                    possibleMoves.add(position);
                }
            }
        }

        return possibleMoves;
    }

}
