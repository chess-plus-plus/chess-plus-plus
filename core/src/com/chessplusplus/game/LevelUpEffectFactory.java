package com.chessplusplus.game;

import com.chessplusplus.game.component.movement.CurvingMovePattern;
import com.chessplusplus.game.component.movement.DiagonalMovePattern;
import com.chessplusplus.game.component.movement.HorizontalMovePattern;
import com.chessplusplus.game.component.movement.MovePattern;
import com.chessplusplus.game.component.movement.MovementRuleSet;
import com.chessplusplus.game.component.movement.VerticalMovePattern;
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

    public static LevelEngine createDefaultRPGRules(int maxRow) {
        HashMap<PieceType, HashMap<Integer, LevelUpEffect>> upgradeScheme = new HashMap<>();

        HashMap<Integer, LevelUpEffect> pawnUpgrades = new HashMap<>();
        pawnUpgrades.put(1, pawnLevel1Ability(maxRow));
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

    public static LevelUpEffect pawnLevel1Ability(int maxRow) {
        MovementRuleSet movementRuleSet = MovementFactory.createPawn(0, maxRow);
        movementRuleSet.setMoveRestrictions(new ArrayList<>());

        return new LevelUpEffect(PAWN_LEVEL_2_THRESHOLD, movementRuleSet);
    }

    public static LevelUpEffect pawnLevel2Ability() {
        List<MovePattern> movePatterns = new ArrayList<>();
        movePatterns.add(HorizontalMovePattern.oneSquareHorizontalMovement());
        movePatterns.add(VerticalMovePattern.oneSquareVerticalMovement());
        movePatterns.add(DiagonalMovePattern.oneSquareDiagonalMovement());

        MovementRuleSet movementRuleSet = new MovementRuleSet.Builder(movePatterns).build();

        return new LevelUpEffect(Integer.MAX_VALUE, movementRuleSet);
    }

    public static LevelUpEffect rookLevel1Ability() {
        return new LevelUpEffect(ROOK_LEVEL_2_THRESHOLD, MovementFactory.createRookMoveRules());
        //TODO: change once blocking is supported.
    }

    public static LevelUpEffect rookLevel2Ability() {
        return new LevelUpEffect(Integer.MAX_VALUE, MovementFactory.createRookMoveRules());
        //TODO: change once blocking is supported. (Needs custom moveRule).
    }

    public static LevelUpEffect knightLevel1Ability() {
        MovementRuleSet knightMoveSet = MovementFactory.createKnightMoveRules();
        List<MovePattern> movePatterns = knightMoveSet.getMovePatternsCopy();
        movePatterns.add(HorizontalMovePattern.oneSquareHorizontalMovement());
        movePatterns.add(VerticalMovePattern.oneSquareVerticalMovement());
        movePatterns.add(DiagonalMovePattern.oneSquareDiagonalMovement());
        knightMoveSet.setMovePatterns(movePatterns);

        return new LevelUpEffect(KNIGHT_LEVEL_2_THRESHOLD, knightMoveSet);
    }

    public static LevelUpEffect knightLevel2Ability() {

        // Get the move-set from the lvl 1 ability.
        LevelUpEffect knightLvl1 = knightLevel1Ability();
        MovementRuleSet movementRuleSet = knightLvl1.getNewMovementRuleSet();
        // Get copies of the move and strike patterns to modify
        List<MovePattern> movePatternsCopy = movementRuleSet.getMovePatternsCopy();
        List<MovePattern> strikePatternsCopy = movementRuleSet.getStrikePatternsCopy();

        // The new ability is to expand the movement and strike with another curving move.
        MovePattern newMovePattern = new CurvingMovePattern(2, 3);
        movePatternsCopy.add(newMovePattern);
        strikePatternsCopy.add(newMovePattern);
        movementRuleSet.setMovePatterns(movePatternsCopy);
        movementRuleSet.setStrikePatterns(strikePatternsCopy);

        return new LevelUpEffect(Integer.MAX_VALUE, movementRuleSet);
    }

    public static LevelUpEffect bishopLevel1Ability() {
        MovementRuleSet bishopMoveSet = MovementFactory.createBishopMoveRules();
        List<MovePattern> movePatterns = bishopMoveSet.getMovePatternsCopy();
        movePatterns.add(HorizontalMovePattern.oneSquareHorizontalMovement());
        movePatterns.add(VerticalMovePattern.oneSquareVerticalMovement());
        bishopMoveSet.setMovePatterns(movePatterns);

        return new LevelUpEffect(BISHOP_LEVEL_2_THRESHOLD, bishopMoveSet);
    }

    public static LevelUpEffect bishopLevel2Ability() {
        return new LevelUpEffect(Integer.MAX_VALUE, bishopLevel1Ability().getNewMovementRuleSet());
        //TODO: Remove blocking from this move set, once blocking is implemented
    }

    public static LevelUpEffect queenLevelUpAbility() {
        return new LevelUpEffect(QUEEN_LEVEL_UP_THRESHOLD, MovementFactory.createQueenMoveRules());
        //TODO: Add custom move rule that spawns a pawn.
    }

}
