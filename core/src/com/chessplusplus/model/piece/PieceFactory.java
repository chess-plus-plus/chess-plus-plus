package com.chessplusplus.model.piece;

import com.chessplusplus.model.piece.movement.MovementRuleSetFactory;
import com.chessplusplus.model.config.RPGConfig;
import com.chessplusplus.model.board.Position;

public class PieceFactory {

    /**
     * Creates a "Power piece" according to the given piece type.
     * A power piece is anything except for a pawn.
     *
     * @param playerId  Player id.
     * @param position  Starting position of piece.
     * @param pieceType Type of the piece.
     * @return New piece object.
     */
    public static Piece createPowerPiece(String playerId, Position position, PieceType pieceType) {
        switch (pieceType) {
            case BISHOP:
                return createBishop(playerId, position);
            case KNIGHT:
                return createKnight(playerId, position);
            case ROOK:
                return createRook(playerId, position);
            case QUEEN:
                return createQueen(playerId, position);
            case KING:
                return createKing(playerId, position);
            default:
                return null;
        }
    }

    /**
     * Creates a pawn piece.
     *
     * @param playerId Player id.
     * @param position Starting position of piece.
     * @param moveDir  Which way along the y axis the pawn will move, 1 for up, -1 for down.
     * @param maxRow   Row that pawn can not move into
     * @return New pawn piece object.
     */
    public static Piece createPawn(String playerId, Position position, int moveDir, int maxRow) {
        return new Piece(playerId, PieceType.PAWN, position,
                MovementRuleSetFactory.createPawn(moveDir, maxRow), RPGConfig.PAWN_LEVEL_1_THRESHOLD);
    }

    /**
     * Creates a new bishop piece.
     *
     * @param playerId Player id.
     * @param position Starting position of piece.
     * @return New bishop piece object.
     */
    public static Piece createBishop(String playerId, Position position) {
        return new Piece(playerId, PieceType.BISHOP, position, MovementRuleSetFactory.createBishopMoveRules(),
                RPGConfig.BISHOP_LEVEL_1_THRESHOLD);
    }

    /**
     * Creates a new knight piece.
     *
     * @param playerId Player id.
     * @param position Starting position of piece.
     * @return New knight piece object.
     */
    public static Piece createKnight(String playerId, Position position) {
        return new Piece(playerId, PieceType.KNIGHT, position, MovementRuleSetFactory.createKnightMoveRules(),
                RPGConfig.KNIGHT_LEVEL_1_THRESHOLD);
    }

    /**
     * Creates a new rook piece.
     *
     * @param playerId Player id.
     * @param position Starting position of piece.
     * @return New rook piece object.
     */
    public static Piece createRook(String playerId, Position position) {
        return new Piece(playerId, PieceType.ROOK, position, MovementRuleSetFactory.createRookMoveRules(),
                RPGConfig.ROOK_LEVEL_1_THRESHOLD);
    }

    /**
     * Creates a new queen piece.
     *
     * @param playerId Player id.
     * @param position Starting position of piece.
     * @return New queen piece object.
     */
    public static Piece createQueen(String playerId, Position position) {
        return new Piece(playerId, PieceType.QUEEN, position, MovementRuleSetFactory.createQueenMoveRules(),
                RPGConfig.QUEEN_LEVEL_UP_THRESHOLD);
    }

    /**
     * Creates a new king piece.
     *
     * @param playerId Player id.
     * @param position Starting position of piece.
     * @return New king piece object.
     */
    public static Piece createKing(String playerId, Position position) {
        return new Piece(playerId, PieceType.KING, position, MovementRuleSetFactory.createKingMoveRules());
    }

}
