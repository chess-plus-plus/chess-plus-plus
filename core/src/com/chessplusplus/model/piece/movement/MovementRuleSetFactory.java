package com.chessplusplus.model.piece.movement;


import java.util.ArrayList;
import java.util.List;

/**
 * Creates movement rule-sets for pieces.
 */
public class MovementRuleSetFactory {

    /**
     * Creates movement rule-set for a standard chess pawn.
     *
     * @param moveDir          Defines which way the pawn can move along the y axis, expressed as 1 or -1.
     * @param maxRow           The id of the top row.
     * @param defaultPromotion Whether to default promotions to Queen pieces.
     * @return Pawn movement rule-set.
     */
    public static MovementRuleSet createPawn(int moveDir, int maxRow, boolean defaultPromotion) {
        List<MovePattern> movePatterns = new ArrayList<>();
        movePatterns.add(VerticalMovePattern.oneSquareVerticalMovement());

        List<MovePattern> strikeRules = new ArrayList<>();
        strikeRules.add(DiagonalMovePattern.oneSquareDiagonalMovement());

        List<MoveRestriction> restrictions = new ArrayList<>();
        restrictions.add(new DirectionalMoveRestriction(0, moveDir));

        // TODO: This may not be correct for pawns with en passant, should check
        restrictions.add(new CollisionMoveRestriction());

        if (moveDir == 1) {
            restrictions.add(new RowMoveRestriction(maxRow));
        } else {
            restrictions.add(new RowMoveRestriction(0));
        }

        List<SpecialMoveRule> specialMoveRules = new ArrayList<>();
        specialMoveRules.add(new PawnDoubleFirstMoveRule());
        specialMoveRules.add(new EnPassantMoveRule());
        specialMoveRules.add(new PromotionMoveRule(defaultPromotion));

        return new MovementRuleSet.Builder(movePatterns, strikeRules)
                .specialMoveRules(specialMoveRules)
                .movementRestrictions(restrictions)
                .strikeRestrictions(restrictions)
                .build();
    }

    /**
     * Creates movement rule-set for a standard chess bishop.
     *
     * @return Bishop movement rule-set.
     */
    public static MovementRuleSet createBishopMoveRules() {
        List<MovePattern> movePatterns = new ArrayList<>();
        movePatterns.add(DiagonalMovePattern.unlimitedDiagonalMovement());

        List<MoveRestriction> restrictions = new ArrayList<>();
        restrictions.add(new CollisionMoveRestriction());

        return new MovementRuleSet.Builder(movePatterns)
                .movementRestrictions(restrictions)
                .strikeRestrictions(restrictions)
                .build();
    }

    /**
     * Creates movement rule-set for a standard chess knight.
     *
     * @return Knight movement rule-set.
     */
    public static MovementRuleSet createKnightMoveRules() {
        List<MovePattern> movePatterns = new ArrayList<>();
        movePatterns.add(CurvingMovePattern.standardKnightMovement());

        return new MovementRuleSet.Builder(movePatterns).build();
    }

    /**
     * Creates movement rule-set for a standard chess rook.
     *
     * @return Rook movement rule-set.
     */
    public static MovementRuleSet createRookMoveRules() {
        List<MovePattern> movePatterns = new ArrayList<>();
        movePatterns.add(HorizontalMovePattern.unlimitedHorizontalMovement());
        movePatterns.add(VerticalMovePattern.unlimitedVerticalMovement());

        List<MoveRestriction> restrictions = new ArrayList<>();
        restrictions.add(new CollisionMoveRestriction());

        return new MovementRuleSet.Builder(movePatterns)
                .movementRestrictions(restrictions)
                .strikeRestrictions(restrictions)
                .build();
    }

    /**
     * Creates movement rule-set for a standard chess queen.
     *
     * @return Queen movement rule-set.
     */
    public static MovementRuleSet createQueenMoveRules() {
        List<MovePattern> movePatterns = new ArrayList<>();
        movePatterns.add(HorizontalMovePattern.unlimitedHorizontalMovement());
        movePatterns.add(VerticalMovePattern.unlimitedVerticalMovement());
        movePatterns.add(DiagonalMovePattern.unlimitedDiagonalMovement());

        List<MoveRestriction> restrictions = new ArrayList<>();
        restrictions.add(new CollisionMoveRestriction());

        return new MovementRuleSet.Builder(movePatterns)
                .movementRestrictions(restrictions)
                .strikeRestrictions(restrictions)
                .build();
    }

    /**
     * Creates movement rule-set for a standard chess king.
     *
     * @return King movement rule-set.
     */
    public static MovementRuleSet createKingMoveRules() {
        List<MovePattern> movePatterns = new ArrayList<>();
        movePatterns.add(HorizontalMovePattern.oneSquareHorizontalMovement());
        movePatterns.add(VerticalMovePattern.oneSquareVerticalMovement());
        movePatterns.add(DiagonalMovePattern.oneSquareDiagonalMovement());

        List<SpecialMoveRule> specialMoveRules = new ArrayList<>();
        specialMoveRules.add(new CastlingMoveRule());

        List<MoveRestriction> restrictions = new ArrayList<>();
        restrictions.add(new CollisionMoveRestriction());

        return new MovementRuleSet.Builder(movePatterns)
                .strikeRestrictions(restrictions)
                .strikeRestrictions(restrictions)
                .specialMoveRules(specialMoveRules)
                .build();
    }

}
