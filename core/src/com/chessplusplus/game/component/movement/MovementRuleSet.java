package com.chessplusplus.game.component.movement;

import com.chessplusplus.game.component.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * A Movement RuleSet is a collection of all of the relevant movement rules
 * that provides relevant functionality.
 */
//TODO: naming is probably confusing.
public class MovementRuleSet {

    private List<MovementRule> movementRules;
    private List<MovementRestriction> movementRestrictions;


    private List<MovementRule> strikeRules;

    public MovementRuleSet(List<MovementRule> movementRules,
                           List<MovementRestriction> movementRestrictions,
                           List<MovementRule> strikeRules) {
        this.movementRules = movementRules;
        this.movementRestrictions = movementRestrictions;
        this.strikeRules = strikeRules;
    }

    /**
     * Creates a MovementRuleSet with no movement restrictions.
     */
    public MovementRuleSet(List<MovementRule> movementRules, List<MovementRule> strikeRules) {
        this(movementRules, new ArrayList<>(), strikeRules);
    }

    /**
     * Creates a MovementRuleSet where the movement and strike rules are identical.
     */
    public MovementRuleSet(List<MovementRule> movementRules) {
        this(movementRules, new ArrayList<>(movementRules));
    }

    /**
     * Utility method that generates all legal moves arising from the movement rule-set.
     *
     * @param piecePosition The position of the piece.
     * @param boardWidth    Width of game board.
     * @param boardHeight   Height of game board.
     * @return All possible moves this movement rule implies.
     */
    public List<Position> getLegalMoves(Position piecePosition, int boardWidth, int boardHeight) {
        List<Position> possibleMoves = getPossibleMoves(piecePosition, boardWidth, boardHeight);
        for (MovementRestriction restriction : movementRestrictions) {
            possibleMoves = restriction.filterMoves(possibleMoves, piecePosition);
        }

        return possibleMoves;
    }

    /**
     * Utility method that generates all possible moves arising from the movement rules.
     * Can be used for both movement and strike rules
     */
    private List<Position> getPossibleMoves(Position piecePosition, int boardWidth, int boardHeight) {

        List<Position> possiblePositions = new ArrayList<>();

        for (MovementRule movementRule : movementRules) {
            List<Position> ruleMoves = movementRule.getPossibleMoves(piecePosition, boardWidth, boardHeight);

            // Make sure to only add positions that haven't already been added to the set.
            for (Position position : ruleMoves) {
                if (!possiblePositions.contains(position)) {
                    possiblePositions.add(position);
                }
            }
        }

        return possiblePositions;
    }

}
