package com.chessplusplus.game.component.movement;

import com.chessplusplus.game.Board;
import com.chessplusplus.game.Piece;
import com.chessplusplus.game.Turn;
import com.chessplusplus.game.component.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * A Movement RuleSet is a collection of all of the relevant movement rules
 * that provides relevant functionality.
 */
public class MovementRuleSet {

    private List<MovePattern> movePatterns;
    private List<MoveRestriction> moveRestrictions;

    private List<MovePattern> strikePatterns;
    private List<MoveRestriction> strikeRestrictions;

    private List<SpecialMoveRule> specialMoveRules;

    public MovementRuleSet(Builder builder) {
        movePatterns = builder.movePatterns;
        specialMoveRules = builder.specialMoveRules;
        moveRestrictions = builder.moveRestrictions;
        strikePatterns = builder.strikePatterns;
        strikeRestrictions = builder.strikeRestrictions;
    }

    /**
     * Generates a list of legal turns the piece can take.
     *
     * @param piece The piece.
     * @param board Game board.
     * @return List of all turns the piece can take.
     */
    public List<Turn> getLegalTurns(Piece piece, Board board) {
        List<Turn> legalTurns = new ArrayList<>();

        // Get legal positions the piece can move into
        List<Position> legalMoves = getLegalMoves(piece.getPosition(), board, false,
                movePatterns, piece.getPlayerId(), moveRestrictions);
        // Get legal positions the piece can strike into
        List<Position> legalStrikes = getLegalMoves(piece.getPosition(), board, true,
                strikePatterns, piece.getPlayerId(), strikeRestrictions);

        legalTurns.addAll(mapMovesToTurns(piece, legalMoves, false));
        legalTurns.addAll(mapMovesToTurns(piece, legalStrikes, true));

        for (SpecialMoveRule specialMoveRule : specialMoveRules) {
            legalTurns.addAll(specialMoveRule.getLegalTurns(piece.getPlayerId(), piece, board));
        }

        return legalTurns;
    }

    /**
     * Creates simple move/strike turns from a list of positions.
     * Does not check validity of moves.
     *
     * @param piece    The piece.
     * @param moves    List of squares the piece can move/strike into.
     * @param isStrike Whether the move is a strike.
     * @return List of turns mapped from moves.
     */
    private List<Turn> mapMovesToTurns(Piece piece, List<Position> moves, boolean isStrike) {
        List<Turn> turns = new ArrayList<>();
        for (Position move : moves) {
            List<Turn.Action> actions = new ArrayList<>();
            if (isStrike) {
                actions.add(new Turn.Action(piece, Turn.ActionType.STRIKE, piece.getPosition(), move));
            }

            actions.add(new Turn.Action(piece, Turn.ActionType.MOVEMENT, piece.getPosition(), move));
            turns.add(new Turn(piece.getPlayerId(), actions));
        }

        return turns;
    }

    /**
     * Utility method that generates all possible moves arising from the movement patterns,
     * then filters based on move restrictions and whether square is empty or not.
     * <p>
     * TODO: Does not handle movement blocking at this point
     * suggest maybe adding that logic to the move patterns themselves.
     *
     * @param piecePosition        Position of the piece.
     * @param board                Game board.
     * @param isStrike             Whether the move is a normal move or a strike-move.
     * @param movementPatterns     Movement patterns used for candidate generation.
     * @param playerId             Id of player
     * @param movementRestrictions Movement restrictions used for candidate filtering.
     * @return List of legal moves (not turns).
     */
    public List<Position> getLegalMoves(Position piecePosition, Board board, boolean isStrike,
                                         List<MovePattern> movementPatterns, String playerId,
                                         List<MoveRestriction> movementRestrictions) {

        // Get all move candidates arising from move patterns
        List<Position> possibleMoves = getPossibleMoveCandidates(piecePosition, board.getWidth(),
                board.getHeight(), movementPatterns);

        // Filter out candidates based on move restrictions
        for (MoveRestriction restriction : movementRestrictions) {
            possibleMoves = restriction.filterMoves(possibleMoves, piecePosition);
        }

        // Filter out candidates based on whether the square is empty or not
        // (depends on whether the action is a move or a strike).
        List<Position> validMoves = new ArrayList<>();
        for (Position position : possibleMoves) {
            if (isStrike && !board.squareIsEmpty(position)
                    && !board.getPiece(position).getPlayerId().equals(playerId)) {
                validMoves.add(position);
            } else if (!isStrike && board.squareIsEmpty(position)) {
                validMoves.add(position);
            }
        }

        return validMoves;
    }

    /**
     * Utility method that generates all possible moves arising from the movement patterns.
     * Can be used for both movement and strike patterns.
     * <p>
     * Does not take board state into consideration.
     */
    private List<Position> getPossibleMoveCandidates(Position piecePosition, int boardWidth,
                                                     int boardHeight, List<MovePattern> movementPatterns) {

        List<Position> possiblePositions = new ArrayList<>();

        for (MovePattern movePattern : movementPatterns) {
            List<Position> ruleMoves = movePattern.getPossibleMoves(piecePosition, boardWidth, boardHeight);

            // Make sure to only add positions that haven't already been added to the set.
            for (Position position : ruleMoves) {
                if (!possiblePositions.contains(position)) {
                    possiblePositions.add(position);
                }
            }
        }

        return possiblePositions;
    }

    public List<MovePattern> getMovePatternsCopy() {
        return new ArrayList<>(movePatterns);
    }

    public void setMovePatterns(List<MovePattern> movePatterns) {
        this.movePatterns = movePatterns;
    }

    public List<MoveRestriction> getMoveRestrictionsCopy() {
        return new ArrayList<>(moveRestrictions);
    }

    public void setMoveRestrictions(List<MoveRestriction> moveRestrictions) {
        this.moveRestrictions = moveRestrictions;
    }

    public List<MovePattern> getStrikePatternsCopy() {
        return new ArrayList<>(strikePatterns);
    }

    public void setStrikePatterns(List<MovePattern> strikePatterns) {
        this.strikePatterns = strikePatterns;
    }

    public List<MoveRestriction> getStrikeRestrictionsCopy() {
        return new ArrayList<>(strikeRestrictions);
    }

    public void setStrikeRestrictions(List<MoveRestriction> strikeRestrictions) {
        this.strikeRestrictions = strikeRestrictions;
    }

    public List<SpecialMoveRule> getSpecialMoveRulesCopy() {
        return new ArrayList<>(specialMoveRules);
    }

    public void setSpecialMoveRules(List<SpecialMoveRule> specialMoveRules) {
        this.specialMoveRules = specialMoveRules;
    }

    /**
     * Builder class that makes generating movement rule sets much easier.
     */
    public static class Builder {

        private final List<MovePattern> movePatterns;
        private List<MoveRestriction> moveRestrictions = new ArrayList<>();

        private final List<MovePattern> strikePatterns;
        private List<MoveRestriction> strikeRestrictions = new ArrayList<>();

        private List<SpecialMoveRule> specialMoveRules = new ArrayList<>();

        /**
         * Standard constructor, used for pieces where movement and strike rules are different.
         */
        public Builder(List<MovePattern> movePatterns, List<MovePattern> strikePatterns) {
            this.movePatterns = movePatterns;
            this.strikePatterns = strikePatterns;
        }

        /**
         * Simple constructor that copies strike rules from movement rules.
         */
        public Builder(List<MovePattern> movePatterns) {
            this.movePatterns = movePatterns;
            this.strikePatterns = new ArrayList<>(movePatterns);
        }

        public Builder movementRestrictions(List<MoveRestriction> moveRestrictions) {
            this.moveRestrictions = moveRestrictions;
            return this;
        }

        public Builder strikeRestrictions(List<MoveRestriction> strikeRestrictions) {
            this.strikeRestrictions = strikeRestrictions;
            return this;
        }

        public Builder specialMoveRules(List<SpecialMoveRule> specialMoveRules) {
            this.specialMoveRules = specialMoveRules;
            return this;
        }

        public MovementRuleSet build() {
            return new MovementRuleSet(this);
        }

    }

    @Override
    public String toString() {
        return "MovementRuleSet{" +
                "movePatterns=" + movePatterns +
                ", moveRestrictions=" + moveRestrictions +
                ", strikePatterns=" + strikePatterns +
                ", strikeRestrictions=" + strikeRestrictions +
                ", specialMoveRules=" + specialMoveRules +
                '}';
    }
}
