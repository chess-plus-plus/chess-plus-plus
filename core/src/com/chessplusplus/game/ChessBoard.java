package com.chessplusplus.game;

import com.chessplusplus.game.component.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChessBoard implements Board {

    private HashMap<Position, Piece> board = new HashMap<>();
    private final int boardWidth;
    private final int boardHeight;

    public ChessBoard(List<Piece> pieces, int boardWidth, int boardHeight) {
        for (Piece piece : pieces) {
            board.put(piece.getPosition(), piece);
        }
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
    }

    public ChessBoard(List<Piece> pieces) {
        this(pieces, 8, 8);
    }

    @Override
    public int getHeight() {
        return boardHeight;
    }

    @Override
    public int getWidth() {
        return boardWidth;
    }

    @Override
    public Piece getPiece(Position square) {
        return board.get(square);
    }

    @Override
    public boolean squareIsEmpty(Position position) {
        return board.get(position) == null;
    }

    @Override
    public boolean squareIsUnderAttack(Position position, String playerId) {
        //TODO: This one is a bit tricky.
        return false;
    }

    @Override
    public List<Piece> getAllPieces() {
        return new ArrayList<>(board.values());
    }

    @Override
    public void updateBoard() {
        List<Piece> pieces = getAllPieces();
        board = new HashMap<>();

        for (Piece piece : pieces) {
            if (piece != null)
                board.put(piece.getPosition(), piece);
        }
    }

    @Override
    public void removePiece(Piece piece) {
        board.put(piece.getPosition(), null);
    }


}
