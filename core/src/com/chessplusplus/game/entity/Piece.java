package com.chessplusplus.game.entity;


import com.badlogic.ashley.core.Entity;
import com.chessplusplus.game.component.MovementComponent;
import com.chessplusplus.game.component.Position;
import com.chessplusplus.game.component.PositionComponent;
import com.chessplusplus.game.component.StrikeComponent;
import com.chessplusplus.game.component.movement.CurvingMovementRule;
import com.chessplusplus.game.component.movement.DiagonalMovementRule;
import com.chessplusplus.game.component.movement.HorizontalMovementRule;
import com.chessplusplus.game.component.movement.MovementRule;
import com.chessplusplus.game.component.movement.VerticalMovementRule;

import java.util.ArrayList;
import java.util.List;

//TODO: Will likely be remodeled into a factory-esque class for easily creating piece entities.
public class Piece {

    /**
     * Creates a Pawn piece Entity in a given position.
     *
     * @param position Position of the piece.
     * @return New Pawn piece Entity.
     */
    public static Entity createPawn(Position position) {
        List<MovementRule> movementRules = new ArrayList<>();
        List<MovementRule> strikeRules = new ArrayList<>();
        movementRules.add(VerticalMovementRule.oneSquareVerticalMovement());
        strikeRules.add(DiagonalMovementRule.oneSquareDiagonalMovement());
        //TODO: Needs special rules:
        // 1: Promotion
        // 2: Directional movement
        // 3: Double move on first move
        // 4: En passant

        return createPiece(position, movementRules, strikeRules);
    }

    /**
     * Creates a Bishop piece Entity in a given position.
     *
     * @param position Position of the piece.
     * @return New Bishop piece Entity.
     */
    public static Entity createBishop(Position position) {
        List<MovementRule> movementRules = new ArrayList<>();
        movementRules.add(DiagonalMovementRule.unlimitedDiagonalMovement());

        return createSimplePiece(position, movementRules);
    }

    /**
     * Creates a Knight (horse) piece Entity in a given position.
     *
     * @param position Position of the piece.
     * @return New Knight piece Entity.
     */
    public static Entity createKnight(Position position) {
        List<MovementRule> movementRules = new ArrayList<>();
        movementRules.add(CurvingMovementRule.standardKnightMovement());

        return createSimplePiece(position, movementRules);
    }

    /**
     * Creates a Rook piece Entity in a given position.
     *
     * @param position Position of the piece.
     * @return New Rook piece Entity.
     */
    public static Entity createRook(Position position) {
        List<MovementRule> movementRules = new ArrayList<>();
        movementRules.add(HorizontalMovementRule.unlimitedHorisontalMovement());
        movementRules.add(VerticalMovementRule.unlimitedVerticalMovement());

        //TODO: Needs to support castling
        return createSimplePiece(position, movementRules);
    }

    /**
     * Creates a Queen piece Entity in a given position.
     *
     * @param position Position of the piece.
     * @return New Queen piece Entity.
     */
    public static Entity createQueen(Position position) {
        List<MovementRule> movementRules = new ArrayList<>();
        movementRules.add(HorizontalMovementRule.unlimitedHorisontalMovement());
        movementRules.add(VerticalMovementRule.unlimitedVerticalMovement());
        movementRules.add(DiagonalMovementRule.unlimitedDiagonalMovement());

        return createSimplePiece(position, movementRules);
    }

    /**
     * Creates a King piece Entity in a given position.
     *
     * @param position Position of the piece.
     * @return New King piece Entity.
     */
    public static Entity createKing(Position position) {
        List<MovementRule> movementRules = new ArrayList<>();
        movementRules.add(HorizontalMovementRule.oneSquareHorizontalMovement());
        movementRules.add(VerticalMovementRule.oneSquareVerticalMovement());
        movementRules.add(DiagonalMovementRule.oneSquareDiagonalMovement());

        //TODO: Needs to support castling
        return createSimplePiece(position, movementRules);
    }

    /**
     * Creates a piece Entity where the strike and movement rules are identical.
     * Strike rules are copied from movement rules, so they can be differentiated later.
     *
     * @param position      Position of the piece.
     * @param movementRules List of all movement rules.
     * @return Piece Entity.
     */
    private static Entity createSimplePiece(Position position, List<MovementRule> movementRules) {
        return createPiece(position, movementRules, new ArrayList<>(movementRules));
    }

    /**
     * Creates a piece Entity from a position, a list of movement rules and a list of strike rules.
     *
     * @param position      Position of the piece.
     * @param movementRules List of all movement rules.
     * @param strikeRules   List of all strike rules.
     * @return Piece Entity.
     */
    private static Entity createPiece(Position position, List<MovementRule> movementRules,
                                      List<MovementRule> strikeRules) {
        return new Entity()
                .add(new PositionComponent(position))
                .add(new MovementComponent(movementRules))
                .add(new StrikeComponent(strikeRules));
    }

}
