package com.chessplusplus.model.board;

import com.chessplusplus.model.piece.Piece;

import java.util.ArrayList;
import java.util.List;

import static com.chessplusplus.model.piece.PieceFactory.createBishop;
import static com.chessplusplus.model.piece.PieceFactory.createKing;
import static com.chessplusplus.model.piece.PieceFactory.createKnight;
import static com.chessplusplus.model.piece.PieceFactory.createPawn;
import static com.chessplusplus.model.piece.PieceFactory.createQueen;
import static com.chessplusplus.model.piece.PieceFactory.createRook;
import static com.chessplusplus.model.board.Position.pos;

/**
 * Creates chess boards with pieces. Can also be used to create pieces.
 */
public class BoardFactory {

    /**
     * Creates a normal 8x8 chess board with a normal set-up of pieces.
     *
     * @return Fresh board.
     */
    public static Board standardBoardAndPieces(String player1Id, String player2Id,
                                               boolean defaultPromotion) {
        List<Piece> pieces = new ArrayList<>();

        pieces.addAll(createStandardPowerPiecesInRow(player1Id, 0));
        pieces.addAll(createRowOfPawns(player1Id, 1, 8, 1, defaultPromotion ));
        pieces.addAll(createRowOfPawns(player2Id, 6, 8, -1, defaultPromotion));
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
     * @param defaultPromotion Whether to default promotions to Queen pieces.
     * @return List of new pawn pieces.
     */
    private static List<Piece> createRowOfPawns(String playerId, int row, int rowWidth,
                                                int moveDir, boolean defaultPromotion) {
        List<Piece> pieces = new ArrayList<>();

        for (int i = 0; i < rowWidth; i++) {
            pieces.add(createPawn(playerId, pos(i, row), moveDir, rowWidth-1, defaultPromotion));
        }

        return pieces;
    }


}
