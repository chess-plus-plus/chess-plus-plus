package com.chessplusplus.game.system;

import com.chessplusplus.game.Piece;
import com.chessplusplus.game.PieceType;
import com.chessplusplus.game.component.movement.MovementRuleSet;

import java.util.HashMap;

/**
 * The Level Engine is used to level up pieces. When a piece levels up it uses the
 * level engine to upgrade itself (dependency injection).
 *
 * It can be customised during run-time if necessary, and is designed to be modifiable.
 */
public class LevelEngine {

    private HashMap<PieceType, HashMap<Integer, LevelUpEffect>> upgradeScheme;

    public LevelEngine(HashMap<PieceType, HashMap<Integer, LevelUpEffect>> upgradeScheme) {
        this.upgradeScheme = upgradeScheme;
    }

    /**
     * Updates the movement and strike rules of the piece according to the upgrade scheme.
     * @param piece Piece to level up.
     */
    public void levelUp(Piece piece) {

        LevelUpEffect effect = upgradeScheme.get(piece.getPieceType()).get(piece.getLevel());
        piece.setMovement(effect.newMovementRuleSet);
        piece.setNextLevelXpThreshold(effect.nextXpThreshold);
        if (effect.resetXpOnLevelUp) {
            piece.setXp(0);
            piece.setLevel(0);
        }

    }

    public HashMap<PieceType, HashMap<Integer, LevelUpEffect>> getUpgradeScheme() {
        return upgradeScheme;
    }

    public void setUpgradeScheme(HashMap<PieceType, HashMap<Integer, LevelUpEffect>> upgradeScheme) {
        this.upgradeScheme = upgradeScheme;
    }

    public static class LevelUpEffect {

        int nextXpThreshold;
        MovementRuleSet newMovementRuleSet;
        boolean resetXpOnLevelUp;

        public LevelUpEffect(int nextXpThreshold, MovementRuleSet newMovementRuleSet,
                             boolean resetXpOnLevelUp) {
            this.nextXpThreshold = nextXpThreshold;
            this.newMovementRuleSet = newMovementRuleSet;
            this.resetXpOnLevelUp = resetXpOnLevelUp;
        }

        public LevelUpEffect(int nextXpThreshold, MovementRuleSet newMovementRuleSet) {
            this(nextXpThreshold, newMovementRuleSet, false);
        }

        public int getNextXpThreshold() {
            return nextXpThreshold;
        }

        public MovementRuleSet getNewMovementRuleSet() {
            return newMovementRuleSet;
        }
    }

}
