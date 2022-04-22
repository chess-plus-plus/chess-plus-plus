package com.chessplusplus.game;


import com.chessplusplus.game.component.movement.CastlingMoveRule;
import com.chessplusplus.game.component.movement.CollisionMoveRestriction;
import com.chessplusplus.game.component.movement.PromotionMoveRule;
import com.chessplusplus.game.component.movement.RowMoveRestriction;
import com.chessplusplus.game.component.movement.SpecialMoveRule;
import com.chessplusplus.game.component.movement.CurvingMovePattern;
import com.chessplusplus.game.component.movement.DiagonalMovePattern;
import com.chessplusplus.game.component.movement.DirectionalMoveRestriction;
import com.chessplusplus.game.component.movement.EnPassantMoveRule;
import com.chessplusplus.game.component.movement.PawnDoubleFirstMoveRule;
import com.chessplusplus.game.component.movement.HorizontalMovePattern;
import com.chessplusplus.game.component.movement.MoveRestriction;
import com.chessplusplus.game.component.movement.MovePattern;
import com.chessplusplus.game.component.movement.MovementRuleSet;
import com.chessplusplus.game.component.movement.VerticalMovePattern;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates movement rule-sets for pieces.
 */
public class MovementFactory {

    /**
     * Creates movement rule-set for a standard chess pawn.
     *
     * @param moveDir Defines which way the pawn can move along the y axis, expressed as 1 or -1
     * @return Pawn movement rule-set.
     */
    public static MovementRuleSet createPawn(int moveDir, int maxRow) {
        List<MovePattern> movePatterns = new ArrayList<>();
        movePatterns.add(VerticalMovePattern.oneSquareVerticalMovement());

        List<MovePattern> strikeRules = new ArrayList<>();
        strikeRules.add(DiagonalMovePattern.oneSquareDiagonalMovement());

        List<MoveRestriction> restrictions = new ArrayList<>();
        restrictions.add(new DirectionalMoveRestriction(0, moveDir));
        if (moveDir == 1) {
            restrictions.add(new RowMoveRestriction(maxRow));
        } else {
            restrictions.add(new RowMoveRestriction(0));
        }

        List<SpecialMoveRule> specialMoveRules = new ArrayList<>();
        specialMoveRules.add(new PawnDoubleFirstMoveRule());
        specialMoveRules.add(new EnPassantMoveRule());
        specialMoveRules.add(new PromotionMoveRule());

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

        return new MovementRuleSet.Builder(movePatterns).specialMoveRules(specialMoveRules).build();
    }

}
