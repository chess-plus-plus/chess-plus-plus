package com.chessplusplus.model.piece.movement;

import com.chessplusplus.model.board.Board;
import com.chessplusplus.model.piece.Piece;
import com.chessplusplus.model.ChessTurn;
import com.chessplusplus.model.board.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<ChessTurn> getLegalTurns(Piece piece, Board board) {
        List<ChessTurn> legalTurns = new ArrayList<>();

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
    private List<ChessTurn> mapMovesToTurns(Piece piece, List<Position> moves, boolean isStrike) {
        List<ChessTurn> turns = new ArrayList<>();
        for (Position move : moves) {
            List<ChessTurn.Action> actions = new ArrayList<>();
            if (isStrike) {
                actions.add(new ChessTurn.Action(piece, ChessTurn.ActionType.STRIKE, piece.getPosition(), move));
            }

            actions.add(new ChessTurn.Action(piece, ChessTurn.ActionType.MOVEMENT, piece.getPosition(), move));
            turns.add(new ChessTurn(piece.getPlayerId(), actions));
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
            List<Position> restrictedMoves = restriction.filterMoves(possibleMoves, piecePosition, board);

            possibleMoves = possibleMoves.stream()
                    .filter(restrictedMoves::contains)
                    .collect(Collectors.toList());
        }

        if(isStrike)
            possibleMoves = possibleMoves.stream()
                    .filter(p -> !board.squareIsEmpty(p))
                    .filter(p -> !board.getPiece(p).getPlayerId().equals(playerId))
                    .collect(Collectors.toList());

        if(!isStrike) possibleMoves = possibleMoves.stream()
                .filter(p -> board.squareIsEmpty(p)|| !board.getPiece(p).getPlayerId().equals(playerId))
                .collect(Collectors.toList());

        return possibleMoves;
    }

    public List<Position> getMoveCandidates(Position piecePos, int boardWidth, int boardHeight,
                                            boolean isStrike) {
        if (isStrike) {
            return getPossibleMoveCandidates(piecePos, boardWidth, boardHeight, strikePatterns);
        } else {
            return getPossibleMoveCandidates(piecePos, boardWidth, boardHeight, movePatterns);
        }
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
