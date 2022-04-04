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
    private List<ConditionalMove> conditionalMoves;
    private List<MovementRestriction> movementRestrictions;

    private List<MovementRule> strikeRules;
    private List<ConditionalMove> conditionalStrikes;
    private List<MovementRestriction> strikeRestrictions;

    public MovementRuleSet(Builder builder) {
        movementRules = builder.movementRules;
        conditionalMoves = builder.conditionalMoves;
        movementRestrictions = builder.movementRestrictions;
        strikeRules = builder.strikeRules;
        conditionalStrikes = builder.conditionalStrikes;
        strikeRestrictions = builder.strikeRestrictions;
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

    /**
     * Builder class that makes generating movement rule sets much easier.
     */
    public static class Builder {

        private final List<MovementRule> movementRules;
        private List<ConditionalMove> conditionalMoves = new ArrayList<>();
        private List<MovementRestriction> movementRestrictions = new ArrayList<>();

        private final List<MovementRule> strikeRules;
        private List<ConditionalMove> conditionalStrikes = new ArrayList<>();
        private List<MovementRestriction> strikeRestrictions = new ArrayList<>();

        /**
         * Standard constructor, used for pieces where movement and strike rules are different.
         */
        public Builder(List<MovementRule> movementRules, List<MovementRule> strikeRules) {
            this.movementRules = movementRules;
            this.strikeRules = strikeRules;
        }

        /**
         * Simple constructor that copies strike rules from movement rules.
         */
        public Builder(List<MovementRule> movementRules) {
            this.movementRules = movementRules;
            this.strikeRules = new ArrayList<>(movementRules);
        }

        public Builder conditionalMoves(List<ConditionalMove> conditionalMoves) {
            this.conditionalMoves = conditionalMoves;
            return this;
        }

        public Builder movementRestrictions(List<MovementRestriction> movementRestrictions) {
            this.movementRestrictions = movementRestrictions;
            return this;
        }

        public Builder conditionalStrikes(List<ConditionalMove> conditionalStrikes) {
            this.conditionalStrikes = conditionalStrikes;
            return this;
        }

        public Builder strikeRestrictions(List<MovementRestriction> strikeRestrictions) {
            this.strikeRestrictions = strikeRestrictions;
            return this;
        }

        public MovementRuleSet build() {
            return new MovementRuleSet(this);
        }

    }

}
