package com.chessplusplus.game;

import com.chessplusplus.game.component.Position;
import com.chessplusplus.game.entity.MovementFactory;

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
     * @return New pawn piece object.
     */
    public static Piece createPawn(String playerId, Position position, int moveDir) {
        return new Piece(playerId, PieceType.PAWN, position, MovementFactory.createPawn(moveDir),
                RPGConfig.PAWN_LEVEL_1_THRESHOLD);
    }

    /**
     * Creates a new bishop piece.
     *
     * @param playerId Player id.
     * @param position Starting position of piece.
     * @return New bishop piece object.
     */
    public static Piece createBishop(String playerId, Position position) {
        return new Piece(playerId, PieceType.BISHOP, position, MovementFactory.createBishopMoveRules(),
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
        return new Piece(playerId, PieceType.KNIGHT, position, MovementFactory.createKnightMoveRules(),
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
        return new Piece(playerId, PieceType.ROOK, position, MovementFactory.createRookMoveRules(),
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
        return new Piece(playerId, PieceType.QUEEN, position, MovementFactory.createQueenMoveRules(),
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
        return new Piece(playerId, PieceType.KING, position, MovementFactory.createKingMoveRules());
    }

}
