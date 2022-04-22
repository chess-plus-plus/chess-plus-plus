package com.chessplusplus.game;

import java.util.ArrayList;
import java.util.List;

import static com.chessplusplus.game.PieceFactory.createBishop;
import static com.chessplusplus.game.PieceFactory.createKing;
import static com.chessplusplus.game.PieceFactory.createKnight;
import static com.chessplusplus.game.PieceFactory.createPawn;
import static com.chessplusplus.game.PieceFactory.createQueen;
import static com.chessplusplus.game.PieceFactory.createRook;
import static com.chessplusplus.game.component.Position.pos;

/**
 * Creates chess boards with pieces. Can also be used to create pieces.
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


}
