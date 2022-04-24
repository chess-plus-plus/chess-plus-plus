package com.chessplusplus.game.system;

import com.chessplusplus.game.Board;
import com.chessplusplus.game.MovementFactory;
import com.chessplusplus.game.Piece;
import com.chessplusplus.game.PieceType;
import com.chessplusplus.game.component.Position;
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
    public void levelUp(Piece piece, Board board) {
        LevelUpEffect effect = upgradeScheme.get(piece.getPieceType()).get(piece.getLevel());
        piece.setMovement(effect.newMovementRuleSet);
        piece.setNextLevelXpThreshold(effect.nextXpThreshold);

        if (effect.IsQueenEffect) {
            piece.setXp(0);
            piece.setLevel(0);

            // TODO: Refactor out creating a new Piece to somewhere else
            Position firstPos = piece.getActions().get(0).startPos;
            int moveDir;
            String texturePath; // TODO: THIS IS VERY BAD AND HACK-Y
            if (firstPos.getY() == 1) {
                moveDir = -1;
                texturePath = "texturepacks/genesis/pieces/black/";
            } else {
                moveDir = 1;
                texturePath = "texturepacks/genesis/pieces/white/";
            }

            Position lastPos = piece.getActions().get(piece.getActions().size() - 2).startPos;
            Piece newPawn = new Piece(piece.getPlayerId(), PieceType.PAWN, lastPos,
                    MovementFactory.createPawn(moveDir, board.getHeight() - 1));
            board.addPiece(newPawn, lastPos);
            board.addPiece(piece, piece.getPosition());
            newPawn.setTexture(texturePath);
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
