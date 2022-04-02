package com.chessplusplus.game.entity;


import com.chessplusplus.game.Piece;
import com.chessplusplus.game.component.Position;
import com.chessplusplus.game.component.movement.CurvingMovementRule;
import com.chessplusplus.game.component.movement.DiagonalMovementRule;
import com.chessplusplus.game.component.movement.DirectionalMovementRestriction;
import com.chessplusplus.game.component.movement.HorizontalMovementRule;
import com.chessplusplus.game.component.movement.MovementRestriction;
import com.chessplusplus.game.component.movement.MovementRule;
import com.chessplusplus.game.component.movement.MovementRuleSet;
import com.chessplusplus.game.component.movement.VerticalMovementRule;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates chess pieces.
 */
public class PieceFactory {

    /**
     * Creates a Pawn piece object in a given position.
     *
     * @param position Position of the piece.
     * @return New Pawn piece object
     */
    public static Piece createPawn(Position position, int dirFilterY) {
        List<MovementRule> movementRules = new ArrayList<>();
        movementRules.add(VerticalMovementRule.oneSquareVerticalMovement());

        List<MovementRule> strikeRules = new ArrayList<>();
        strikeRules.add(DiagonalMovementRule.oneSquareDiagonalMovement());

        List<MovementRestriction> restrictions = new ArrayList<>();
        restrictions.add(new DirectionalMovementRestriction(0, dirFilterY));
        //TODO: Needs special rules:
        // 1: Promotion
        // 3: Double move on first move
        // 4: En passant

        MovementRuleSet ruleSet = new MovementRuleSet(movementRules, restrictions, strikeRules);
        return new Piece(position, ruleSet);
    }

    /**
     * Creates a Bishop piece object in a given position.
     *
     * @param position Position of the piece.
     * @return New Bishop piece object
     */
    public static Piece createBishop(Position position) {
        List<MovementRule> movementRules = new ArrayList<>();
        movementRules.add(DiagonalMovementRule.unlimitedDiagonalMovement());

        return new Piece(position, new MovementRuleSet(movementRules));
    }

    /**
     * Creates a Knight (horse) piece object in a given position.
     *
     * @param position Position of the piece.
     * @return New Knight piece object.
     */
    public static Piece createKnight(Position position) {
        List<MovementRule> movementRules = new ArrayList<>();
        movementRules.add(CurvingMovementRule.standardKnightMovement());

        return new Piece(position, new MovementRuleSet(movementRules));
    }

    /**
     * Creates a Rook piece object in a given position.
     *
     * @param position Position of the piece.
     * @return New Rook piece object
     */
    public static Piece createRook(Position position) {
        List<MovementRule> movementRules = new ArrayList<>();
        movementRules.add(HorizontalMovementRule.unlimitedHorisontalMovement());
        movementRules.add(VerticalMovementRule.unlimitedVerticalMovement());

        //TODO: Needs to support castling
        return new Piece(position, new MovementRuleSet(movementRules));
    }

    /**
     * Creates a Queen piece object in a given position.
     *
     * @param position Position of the piece.
     * @return New Queen piece object
     */
    public static Piece createQueen(Position position) {
        List<MovementRule> movementRules = new ArrayList<>();
        movementRules.add(HorizontalMovementRule.unlimitedHorisontalMovement());
        movementRules.add(VerticalMovementRule.unlimitedVerticalMovement());
        movementRules.add(DiagonalMovementRule.unlimitedDiagonalMovement());

        return new Piece(position, new MovementRuleSet(movementRules));
    }

    /**
     * Creates a King piece object in a given position.
     *
     * @param position Position of the piece.
     * @return New King piece object
     */
    public static Piece createKing(Position position) {
        List<MovementRule> movementRules = new ArrayList<>();
        movementRules.add(HorizontalMovementRule.oneSquareHorizontalMovement());
        movementRules.add(VerticalMovementRule.oneSquareVerticalMovement());
        movementRules.add(DiagonalMovementRule.oneSquareDiagonalMovement());

        //TODO: Needs to support castling
        return new Piece(position, new MovementRuleSet(movementRules));
    }

}
