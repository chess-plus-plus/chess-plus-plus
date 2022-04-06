package com.chessplusplus.game;

import com.chessplusplus.game.component.Position;
import com.chessplusplus.game.entity.MovementFactory;

import java.util.ArrayList;
import java.util.List;

import static com.chessplusplus.game.component.Position.pos;

/**
 * Creates chess boards with pieces. Can also be used to create pieces.
 * TODO: Split out piece creation into different factory class?
 */
public class BoardFactory {

    /**
     * Creates a normal 8x8 chess board with a normal set-up of pieces.
     *
     * @return Fresh board.
     */
    public static Board standardBoardAndPieces(String player1Id, String player2Id) {
        List<Piece> pieces = new ArrayList<>();

        pieces.addAll(createStandardPowerPiecesInRow(player1Id, 0));
        pieces.addAll(createRowOfPawns(player1Id, 1, 8, 1));
        pieces.addAll(createRowOfPawns(player2Id, 6, 8, -1));
        pieces.addAll(createStandardPowerPiecesInRow(player2Id, 7));

        return new ChessBoard(pieces, 8, 8);
    }

    /**
     * Creates all of the non-pawn pieces in a standard chess formation.
     *
     * @param playerId PlayerId.
     * @param row      Row, aka the y coordinate.
     * @return List of new non-pawn pieces.
     */
    private static List<Piece> createStandardPowerPiecesInRow(String playerId, int row) {
        List<Piece> pieces = new ArrayList<>();

        pieces.add(createRook(playerId, pos(0, row)));
        pieces.add(createKnight(playerId, pos(1, row)));
        pieces.add(createBishop(playerId, pos(2, row)));
        pieces.add(createQueen(playerId, pos(3, row)));
        pieces.add(createKing(playerId, pos(4, row)));
        pieces.add(createBishop(playerId, pos(5, row)));
        pieces.add(createKnight(playerId, pos(6, row)));
        pieces.add(createRook(playerId, pos(7, row)));

        return pieces;
    }

    /**
     * Creates a row filled with pawns.
     *
     * @param playerId Player id.
     * @param row      Row, aka the y coordinate.
     * @param rowWidth Width of the row.
     * @param moveDir  Which way along the y axis the pawn will move, 1 for up, -1 for down.
     * @return List of new pawn pieces.
     */
    private static List<Piece> createRowOfPawns(String playerId, int row, int rowWidth, int moveDir) {
        List<Piece> pieces = new ArrayList<>();

        for (int i = 0; i < rowWidth; i++) {
            pieces.add(createPawn(playerId, pos(0, row), moveDir));
        }

        return pieces;
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
        return new Piece(playerId, PieceType.PAWN, position, MovementFactory.createPawn(moveDir));
    }

    /**
     * Creates a new bishop piece.
     *
     * @param playerId Player id.
     * @param position Starting position of piece.
     * @return New bishop piece object.
     */
    public static Piece createBishop(String playerId, Position position) {
        return new Piece(playerId, PieceType.BISHOP, position, MovementFactory.createBishopMoveRules());
    }

    /**
     * Creates a new knight piece.
     *
     * @param playerId Player id.
     * @param position Starting position of piece.
     * @return New knight piece object.
     */
    public static Piece createKnight(String playerId, Position position) {
        return new Piece(playerId, PieceType.KNIGHT, position, MovementFactory.createKnightMoveRules());
    }

    /**
     * Creates a new rook piece.
     *
     * @param playerId Player id.
     * @param position Starting position of piece.
     * @return New rook piece object.
     */
    public static Piece createRook(String playerId, Position position) {
        return new Piece(playerId, PieceType.ROOK, position, MovementFactory.createRookMoveRules());
    }

    /**
     * Creates a new queen piece.
     *
     * @param playerId Player id.
     * @param position Starting position of piece.
     * @return New queen piece object.
     */
    public static Piece createQueen(String playerId, Position position) {
        return new Piece(playerId, PieceType.QUEEN, position, MovementFactory.createQueenMoveRules());
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
