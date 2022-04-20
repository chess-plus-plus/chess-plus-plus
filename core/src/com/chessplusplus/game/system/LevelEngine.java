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

    }

}
