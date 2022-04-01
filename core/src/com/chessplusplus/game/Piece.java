package com.chessplusplus.game;

import com.chessplusplus.game.component.Position;
import com.chessplusplus.game.component.movement.MovementRule;

import java.util.ArrayList;
import java.util.List;

public class Piece {

    Position position;
    List<MovementRule> movementRules;
    List<MovementRule> strikeRules;

    public Piece(Position position, List<MovementRule> movementRules, List<MovementRule> strikeRules) {
        this.position = position;
        this.movementRules = movementRules;
        this.strikeRules = strikeRules;
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
