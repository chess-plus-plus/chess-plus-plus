package com.chessplusplus.model.level;

import com.chessplusplus.model.board.Board;
import com.chessplusplus.model.board.Position;
import com.chessplusplus.model.piece.movement.MovementRuleSet;
import com.chessplusplus.model.piece.Piece;
import com.chessplusplus.model.piece.PieceType;
import com.chessplusplus.model.piece.movement.MovementRuleSetFactory;

import java.util.HashMap;

/**
 * The Level Engine is used to level up pieces. When a piece levels up it uses the
 * level engine to upgrade itself (dependency injection).
 *
 * It can be customised during run-time if necessary, and is designed to be modifiable.
 */
public class LevelSystem {

    private HashMap<PieceType, HashMap<Integer, LevelUpEffect>> upgradeScheme;
    private final boolean defaultPromotion;

    public LevelSystem(HashMap<PieceType, HashMap<Integer, LevelUpEffect>> upgradeScheme,
                       boolean defaultPromotion) {
        this.upgradeScheme = upgradeScheme;
        this.defaultPromotion = defaultPromotion;
    }

    /**
     * Updates the movement and strike rules of the piece according to the upgrade scheme.
     * @param piece Piece to level up.
     */
    public void levelUp(Piece piece, Board board) {
        LevelUpEffect effect = upgradeScheme.get(piece.getPieceType()).get(piece.getLevel());
        piece.setMovement(effect.newMovementRuleSet);
        piece.setNextLevelXpThreshold(effect.nextXpThreshold);

        if (effect.IsQueenEffect) {
            piece.setXp(0);
            piece.setLevel(0);

            Position firstPos = piece.getActions().get(0).startPos;
            int moveDir;
            if (firstPos.getY() == 1) {
                moveDir = -1;
            } else {
                moveDir = 1;
            }

            Position lastPos = piece.getActions().get(piece.getActions().size() - 2).startPos;
            Piece newPawn = new Piece(piece.getPlayerId(), PieceType.PAWN, lastPos,
                    MovementRuleSetFactory.createPawn(moveDir, board.getHeight() - 1,
                            defaultPromotion));
            board.addPiece(newPawn, lastPos);
            board.addPiece(piece, piece.getPosition());
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
        boolean IsQueenEffect;

        public LevelUpEffect(int nextXpThreshold, MovementRuleSet newMovementRuleSet,
                             boolean IsQueenEffect) {
            this.nextXpThreshold = nextXpThreshold;
            this.newMovementRuleSet = newMovementRuleSet;
            this.IsQueenEffect = IsQueenEffect;
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
