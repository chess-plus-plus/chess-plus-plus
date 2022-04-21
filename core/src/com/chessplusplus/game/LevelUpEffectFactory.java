package com.chessplusplus.game;

import com.chessplusplus.game.component.movement.DiagonalMovePattern;
import com.chessplusplus.game.component.movement.HorizontalMovePattern;
import com.chessplusplus.game.component.movement.MovePattern;
import com.chessplusplus.game.component.movement.MovementRuleSet;
import com.chessplusplus.game.component.movement.VerticalMovePattern;
import com.chessplusplus.game.entity.MovementFactory;
import com.chessplusplus.game.system.LevelEngine;

import static com.chessplusplus.game.system.LevelEngine.LevelUpEffect;
import static com.chessplusplus.game.RPGConfig.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//TODO: Comments
public class LevelUpEffectFactory {

    public static int level1Threshold = 100;
    public static int level2Threshold = 200;

    public static LevelEngine createDefaultRPGRules() {
        HashMap<PieceType, HashMap<Integer, LevelUpEffect>> upgradeScheme = new HashMap<>();

        HashMap<Integer, LevelUpEffect> pawnUpgrades = new HashMap<>();
        pawnUpgrades.put(1, pawnLevel1Ability());
        pawnUpgrades.put(2, pawnLevel2Ability());

        HashMap<Integer, LevelUpEffect> rookUpgrades = new HashMap<>();
        rookUpgrades.put(1, rookLevel1Ability());
        rookUpgrades.put(2, rookLevel2Ability());

        HashMap<Integer, LevelUpEffect> knightUpgrades = new HashMap<>();
        knightUpgrades.put(1, knightLevel1Ability());
        knightUpgrades.put(2, knightLevel2Ability());

        HashMap<Integer, LevelUpEffect> bishopUpgrades = new HashMap<>();
        bishopUpgrades.put(1, bishopLevel1Ability());
        bishopUpgrades.put(2, bishopLevel2Ability());

        HashMap<Integer, LevelUpEffect> queenUpgrades = new HashMap<>();
        queenUpgrades.put(1, queenLevelUpAbility());

        upgradeScheme.put(PieceType.PAWN, pawnUpgrades);
        upgradeScheme.put(PieceType.ROOK, rookUpgrades);
        upgradeScheme.put(PieceType.KNIGHT, knightUpgrades);
        upgradeScheme.put(PieceType.BISHOP, bishopUpgrades);
        upgradeScheme.put(PieceType.QUEEN, queenUpgrades);

        return new LevelEngine(upgradeScheme);
    }

    public static LevelUpEffect pawnLevel1Ability() {
        MovementRuleSet movementRuleSet = MovementFactory.createPawn(0);
        movementRuleSet.setMoveRestrictions(new ArrayList<>());

        return new LevelUpEffect(PAWN_LEVEL_1_THRESHOLD, movementRuleSet);
    }

    public static LevelUpEffect pawnLevel2Ability() {
        List<MovePattern> movePatterns = new ArrayList<>();
        movePatterns.add(HorizontalMovePattern.oneSquareHorizontalMovement());
        movePatterns.add(VerticalMovePattern.oneSquareVerticalMovement());
        movePatterns.add(DiagonalMovePattern.oneSquareDiagonalMovement());

        MovementRuleSet movementRuleSet = new MovementRuleSet.Builder(movePatterns).build();

        return new LevelUpEffect(PAWN_LEVEL_2_THRESHOLD, movementRuleSet);
    }

    public static LevelUpEffect rookLevel1Ability() {
        return new LevelUpEffect(ROOK_LEVEL_1_THRESHOLD, MovementFactory.createRookMoveRules());
        //TODO: change once blocking is supported.
    }

    public static LevelUpEffect rookLevel2Ability() {
        return new LevelUpEffect(ROOK_LEVEL_2_THRESHOLD, MovementFactory.createRookMoveRules());
        //TODO: change once blocking is supported. (Needs custom moveRule).
    }

    public static LevelUpEffect knightLevel1Ability() {
        MovementRuleSet knightMoveSet = MovementFactory.createKnightMoveRules();
        List<MovePattern> movePatterns = knightMoveSet.getMovePatternsCopy();
        movePatterns.add(HorizontalMovePattern.oneSquareHorizontalMovement());
        movePatterns.add(VerticalMovePattern.oneSquareVerticalMovement());
        movePatterns.add(DiagonalMovePattern.oneSquareDiagonalMovement());

        return new LevelUpEffect(KNIGHT_LEVEL_1_THRESHOLD, knightMoveSet);
    }

    public static LevelUpEffect knightLevel2Ability() {
        return new LevelUpEffect(KNIGHT_LEVEL_2_THRESHOLD, MovementFactory.createKnightMoveRules());
        //TODO: Implement custom moveRule for this (see docs for rules).
    }

    public static LevelUpEffect bishopLevel1Ability() {
        MovementRuleSet bishopMoveSet = MovementFactory.createBishopMoveRules();
        List<MovePattern> movePatterns = bishopMoveSet.getMovePatternsCopy();
        movePatterns.add(HorizontalMovePattern.oneSquareHorizontalMovement());
        movePatterns.add(VerticalMovePattern.oneSquareVerticalMovement());

        return new LevelUpEffect(BISHOP_LEVEL_1_THRESHOLD, bishopMoveSet);
    }

    public static LevelUpEffect bishopLevel2Ability() {
        return new LevelUpEffect(BISHOP_LEVEL_2_THRESHOLD, bishopLevel1Ability().getNewMovementRuleSet());
        //TODO: Remove blocking from this move set, once blocking is implemented
    }

    public static LevelUpEffect queenLevelUpAbility() {
        return new LevelUpEffect(QUEEN_LEVEL_UP_THRESHOLD, MovementFactory.createQueenMoveRules());
        //TODO: Add custom move rule that spawns a pawn.
    }

}
