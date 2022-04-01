package com.chessplusplus.game.entity;


import com.chessplusplus.game.Piece;
import com.chessplusplus.game.component.Position;
import com.chessplusplus.game.component.movement.CurvingMovementRule;
import com.chessplusplus.game.component.movement.DiagonalMovementRule;
import com.chessplusplus.game.component.movement.HorizontalMovementRule;
import com.chessplusplus.game.component.movement.MovementRule;
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
    public static Piece createPawn(Position position) {
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
     * Creates a Bishop piece object in a given position.
     *
     * @param position Position of the piece.
     * @return New Bishop piece object
     */
    public static Piece createBishop(Position position) {
        List<MovementRule> movementRules = new ArrayList<>();
        movementRules.add(DiagonalMovementRule.unlimitedDiagonalMovement());

        return createSimplePiece(position, movementRules);
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

        return createSimplePiece(position, movementRules);
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
        return createSimplePiece(position, movementRules);
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

        return createSimplePiece(position, movementRules);
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
        return createSimplePiece(position, movementRules);
    }

    /**
     * Creates a piece object where the strike and movement rules are identical.
     * Strike rules are copied from movement rules, so they can be differentiated later.
     *
     * @param position      Position of the piece.
     * @param movementRules List of all movement rules.
     * @return Piece object
     */
    private static Piece createSimplePiece(Position position, List<MovementRule> movementRules) {
        return createPiece(position, movementRules, new ArrayList<>(movementRules));
    }

    /**
     * Creates a piece object from a position, a list of movement rules and a list of strike rules.
     *
     * @param position      Position of the piece.
     * @param movementRules List of all movement rules.
     * @param strikeRules   List of all strike rules.
     * @return Piece object
     */
    private static Piece createPiece(Position position, List<MovementRule> movementRules,
                                     List<MovementRule> strikeRules) {
        return new Piece(position, movementRules, strikeRules);
    }

}
